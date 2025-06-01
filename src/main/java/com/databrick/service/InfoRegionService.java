package com.databrick.service;


import com.databrick.entity.*;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InfoRegionService {
    private final LoggingUtility log = new LoggingUtility(InfoRegion.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractInfoRegionData(List<InputStream> bucketObjects) throws IOException {
        try {
            log.registerLog(Level.INFO, "Iniciando o processamento de dados de qualidade de vida região");


            for (InputStream bucketObject : bucketObjects) {
                Workbook workbook = new XSSFWorkbook(bucketObject);
                Sheet sheet = workbook.getSheetAt(0);
                log.registerLog(Level.INFO, "Planilha acessada com sucesso");

                DataFormatter formatter = new DataFormatter();

                Integer success=0;
                Integer failed=0;


                for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);

                    int lastCellNum = row.getLastCellNum(); // pega o número total de colunas da linha
                    List<String> cellValues = new ArrayList<>(Collections.nCopies(lastCellNum, "0"));

                    for (int j = 0; j < lastCellNum; j++) {
                        Cell cell = row.getCell(j, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
                        String value = cell != null ? formatter.formatCellValue(cell) : "0";
                        value = value.isEmpty() || value.equalsIgnoreCase("nan") ? "0" : value;
                        cellValues.set(j, value);
                    }

                    if (cellValues.contains(null)) {
                        log.registerLog(Level.WARN, "Célula vazia encontrada na linha " + (i + 1));
                        failed++;
                        continue;
                    }

                    InfoRegionValue infoRegionValue = new InfoRegionValue(cellValues.get(2), cellValues.get(5), cellValues.get(77), cellValues.get(78),cellValues.get(79),cellValues.get(80), cellValues.get(81),cellValues.get(211),cellValues.get(235),cellValues.get(233) );
                    InfoRegion infoRegion = new InfoRegion(cellValues.get(0), cellValues.get(1), cellValues.get(3), cellValues.get(7), cellValues.get(232), cellValues.get(233), cellValues.get(234),infoRegionValue);

                    Boolean wasSaved = jdbcService.saveInfoRegion(infoRegion);
                    if (wasSaved) success++; else failed++;
                }
                log.registerLog(Level.INFO, "Dados de Informações de região salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }



    }

}
