package com.databrick.service;


import com.databrick.entity.InfoRegion;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class InfoRegionService {
    private final LoggingUtility log = new LoggingUtility(InfoRegion.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractInfoRegionData(List<InputStream> bucketObjects) throws IOException {
        log.registerLog(Level.INFO, "Iniciando o processamento de dados de qualidade de vida região");


        for (InputStream bucketObject : bucketObjects) {
            Workbook workbook = new XSSFWorkbook(bucketObject);
            Sheet sheet = workbook.getSheetAt(1);
            log.registerLog(Level.INFO, "Planilha acessada com sucesso");

            DataFormatter formatter = new DataFormatter();

            Integer success=0;
            Integer failed=0;


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

                InfoRegion infoRegion = new InfoRegion(
                        cellValues.get(1),cellValues.get(2),
                        cellValues.get(3),cellValues.get(4),
                        cellValues.get(7),cellValues.get(8),
                        cellValues.get(78),cellValues.get(80),
                        cellValues.get(81),cellValues.get(82),
                        cellValues.get(83),cellValues.get(84),
                        cellValues.get(212),cellValues.get(232),
                        cellValues.get(233),cellValues.get(234),
                        cellValues.get(235)
                );

                Boolean wasSaved = jdbcService.saveProperty(infoRegion);
                if (wasSaved) success++; else failed++;
            }
        }
    }

}
