package com.databrick.service;

import com.databrick.entity.HealthCare;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HealthCareService {
    private static final int BATCH_SIZE = 1000;
    private final LoggingUtility log = new LoggingUtility(HealthCareService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionHealthCareData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO, "Iniciando o processamento de dados do sistema de saúde");
        try {
            for (InputStream bucketObject : bucketObjects) {
                try (Workbook workbook = new XSSFWorkbook(bucketObject)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                    DataFormatter formatter = new DataFormatter();
                    Integer success = 0;
                    Integer failed = 0;
                    List<HealthCare> batchList = new ArrayList<>();

                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        int lastCellNum = row.getLastCellNum();
                        List<String> cellValues = new ArrayList<>(Collections.nCopies(lastCellNum, "0"));

                        for (int j = 0; j < lastCellNum; j++) {
                            Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                            String value = cell != null ? formatter.formatCellValue(cell) : "0";
                            value = value.isEmpty() || value.equalsIgnoreCase("nan") ? "0" : value;
                            cellValues.set(j, value);
                        }

                        try {
                            HealthCare healthCare = new HealthCare(cellValues.get(24), cellValues.get(6), cellValues.get(11), cellValues.get(20), cellValues.get(13));
                            batchList.add(healthCare);

                            if (batchList.size() >= BATCH_SIZE) {
                                if (jdbcService.saveHealthCare(batchList)) {
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

                    if (!batchList.isEmpty()) {
                        if (jdbcService.saveHealthCare(batchList)) {
                            success += batchList.size();
                        } else {
                            failed += batchList.size();
                        }
                    }

                    log.registerLog(Level.INFO, "Dados do sistema de saúde salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
                }
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}
