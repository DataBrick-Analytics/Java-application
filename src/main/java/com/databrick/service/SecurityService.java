package com.databrick.service;

import com.databrick.entity.Security;
import com.databrick.utils.LoggingUtility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class SecurityService {

    private final LoggingUtility log = new LoggingUtility();

    public void processDataSecurity(Sheet sheet) {
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            List<String> cellValues = new ArrayList<>();

            row.forEach(cell ->
                    cellValues.add(cell.getStringCellValue()) // adiciona o valor da célula na lista
            );

            Boolean lineHasEmptyCell = false;
            for (String value : cellValues) {
                if (value == null) {
                    log.registerLog("Célula vazia encontrada na linha " + (i + 1), "warn");
                    lineHasEmptyCell = true;
                    break;
                }
            }

            if (lineHasEmptyCell) {
                continue;
            }

            Security security = new Security(
                    cellValues.get(0), cellValues.get(1), cellValues.get(2), cellValues.get(3),
                    cellValues.get(4), cellValues.get(5), cellValues.get(6), cellValues.get(7),
                    cellValues.get(8), cellValues.get(9), cellValues.get(10), cellValues.get(11)
            );

            // TODO inserção dos dados no banco
        }
    }
}
