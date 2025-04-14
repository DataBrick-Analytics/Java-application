package com.databrick.service;

import com.databrick.entity.Address;
import com.databrick.entity.Property;
import com.databrick.entity.Value;
import com.databrick.utils.LoggingUtility;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

public class PropertyService {

    private final LoggingUtility log = new LoggingUtility();

    public void processData(Sheet sheet) {
        for (Row row : sheet) {
            List<String> cellValues = new ArrayList<>(); // Lista onde receberemos os valores das células
            row.forEach(cell ->
                    cellValues.add(cell.getStringCellValue()) // adiciona o valor da célula na lista
            );

            for (String value : cellValues) {
                if (value == null) {
                    log.registerLog("Celula vazia", "warn");
                    return;
                }
            }

            Value propertyValue = new Value(cellValues.get(1), cellValues.get(2), cellValues.get(3));
            Address propertyAddress = new Address(
                    cellValues.get(9), cellValues.get(10), cellValues.get(11),
                    cellValues.get(12), cellValues.get(13), cellValues.get(14),
                    cellValues.get(15), cellValues.get(16), cellValues.get(17),
                    cellValues.get(18), cellValues.get(19));
            Property property = new Property(propertyValue, cellValues.get(4), cellValues.get(5), cellValues.get(6), cellValues.get(7), cellValues.get(8), propertyAddress, cellValues.get(20), cellValues.get(21));

            // TODO inserção dos dados no banco
        }
    }
}
