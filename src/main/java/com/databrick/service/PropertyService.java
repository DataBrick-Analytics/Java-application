package com.databrick.service;

import com.databrick.entity.Address;
import com.databrick.entity.Property;
import com.databrick.entity.Value;
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

public class PropertyService {

    private final LoggingUtility log = new LoggingUtility(PropertyService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionPropertyData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO,"Iniciando o processamento de dados de propriedades");
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

                    Value propertyValue = new Value(cellValues.get(0), cellValues.get(1), cellValues.get(2), cellValues.get(3), cellValues.get(4));
                    Address propertyAddress = new Address(
                            cellValues.get(10), cellValues.get(11), cellValues.get(12),
                            cellValues.get(13), cellValues.get(14), cellValues.get(15),
                            cellValues.get(16), cellValues.get(17), cellValues.get(18),
                            cellValues.get(19), cellValues.get(20));
                    Property property = new Property(
                            propertyValue, cellValues.get(5), cellValues.get(6),
                            cellValues.get(7), cellValues.get(8), cellValues.get(9),
                            propertyAddress, cellValues.get(21), cellValues.get(22));

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
