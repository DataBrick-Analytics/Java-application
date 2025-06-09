package com.databrick.service;

import com.databrick.entity.District;
import com.databrick.entity.Transportation;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class DistrictService {
    private static final int BATCH_SIZE = 1000;
    private final LoggingUtility log = new LoggingUtility(DistrictService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionDistrictData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO, "Iniciando o processamento de dados de distritos");
        try {
            for (InputStream bucketObject : bucketObjects) {
                try (Workbook workbook = new XSSFWorkbook(bucketObject)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                    DataFormatter formatter = new DataFormatter();
                    Integer success = 0;
                    Integer failed = 0;
                    List<District> batchList = new ArrayList<>();

                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        List<String> cellValues = new ArrayList<>();
                        row.forEach(cell -> {
                            String value = formatter.formatCellValue(cell);
                            cellValues.add(value.isEmpty() || value.equalsIgnoreCase("nan") ? null : value);
                        });

                        if (cellValues.contains(null)) {
                            log.registerLog(Level.WARN, "Célula vazia encontrada na linha " + (i + 1));
                            failed++;
                            continue;
                        }

                        try {
                            District district = new District(cellValues.get(0), cellValues.get(1), cellValues.get(2), cellValues.get(4));
                            batchList.add(district);

                            // Processa o lote quando atingir o tamanho definido
                            if (batchList.size() >= BATCH_SIZE) {
                                if (jdbcService.saveDistrict(batchList)) {
                                    success += batchList.size();
                                } else {
                                    failed += batchList.size();
                                }
                                batchList.clear();
                            }
                        } catch (Exception e) {
                            log.registerLog(Level.ERROR, "Erro ao processar linha " + (i + 1) + ": " + e.getMessage());
                            failed++;
                        }
                    }

                    // Processa o último lote
                    if (!batchList.isEmpty()) {
                        if (jdbcService.saveDistrict(batchList)) {
                            success += batchList.size();
                        } else {
                            failed += batchList.size();
                        }
                    }

                    log.registerLog(Level.INFO, "Dados de distritos salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
                }
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}