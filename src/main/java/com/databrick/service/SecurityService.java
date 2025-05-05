package com.databrick.service;

import com.databrick.entity.Security;
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

public class SecurityService {

    private final LoggingUtility log = new LoggingUtility(SecurityService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionSecurityData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO,"Iniciando o processamento de dados de segurança");
        try {
            for (InputStream bucketObject : bucketObjects) {
                Workbook workbook = new XSSFWorkbook(bucketObject);
                Sheet sheet = workbook.getSheetAt(0);
                log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                DataFormatter formatter = new DataFormatter();

                for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
                    Row row = sheet.getRow(i);

                    List<String> cellValues = new ArrayList<>();
                    row.forEach(cell -> {
                        String value = formatter.formatCellValue(cell);
                        cellValues.add(value.isEmpty() ? null : value);
                    });

                    if (cellValues.contains(null)) {
                        log.registerLog(Level.WARN, "Célula vazia encontrada na linha " + (i + 1));
                        continue;
                    }

                    Security security = new Security(
                            cellValues.get(0), cellValues.get(1), cellValues.get(2), cellValues.get(3),
                            cellValues.get(4), cellValues.get(5), cellValues.get(6), cellValues.get(7),
                            cellValues.get(8), cellValues.get(9)
                    );

                    jdbcService.saveSecurity(security);
                }
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}
