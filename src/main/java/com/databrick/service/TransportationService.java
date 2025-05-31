package com.databrick.service;

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

public class TransportationService {
    private final LoggingUtility log = new LoggingUtility(PropertyService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionTransportationData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO,"Iniciando o processamento de dados de transportes");
        try {
            for (InputStream bucketObject : bucketObjects) {
                Workbook workbook = new XSSFWorkbook(bucketObject);
                Sheet sheet = workbook.getSheetAt(0);
                log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                DataFormatter formatter = new DataFormatter();

                Integer success = 0;
                Integer failed = 0;

                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
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

                    Transportation transportation = new Transportation(cellValues.get(1), cellValues.get(2), cellValues.get(3), cellValues.get(4));

                    Boolean wasSaved = jdbcService.saveTransportation(transportation);
                    if (wasSaved) success++; else failed++;
                }
                log.registerLog(Level.INFO, "Dados de transporte salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}

