package com.databrick.service;

import com.databrick.entity.Property;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PropertyService {

    private final LoggingUtility log = new LoggingUtility(PropertyService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionPropertyData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO,"Iniciando o processamento de dados de propriedades");
        IOUtils.setByteArrayMaxOverride(250_000_000);

        try {
            for (InputStream bucketObject : bucketObjects) {
                Workbook workbook = new XSSFWorkbook(bucketObject);
                Sheet sheet = workbook.getSheetAt(0);
                log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                DataFormatter formatter = new DataFormatter();

                Integer success = 0;
                Integer failed = 0;

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

                    if (cellValues.contains(null)) {
                        log.registerLog(Level.WARN, "Célula vazia encontrada na linha " + (i + 1));
                        failed++;
                        continue;
                    }

                    Property property = new Property(cellValues.get(35), cellValues.get(17));

                    Boolean wasSaved = jdbcService.saveProperty(property);
                    if (wasSaved) success++; else failed++;
                }

                log.registerLog(Level.INFO, "Dados de segurança salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}
