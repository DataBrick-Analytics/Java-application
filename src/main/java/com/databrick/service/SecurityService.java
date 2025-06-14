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
    private static final int BATCH_SIZE = 1000;
    private final LoggingUtility log = new LoggingUtility(SecurityService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionSecurityData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO, "Iniciando o processamento de dados de segurança");
        try {
            for (InputStream bucketObject : bucketObjects) {
                try (Workbook workbook = new XSSFWorkbook(bucketObject)) {
                    Sheet sheet = workbook.getSheetAt(0);
                    log.registerLog(Level.INFO, "A planilha foi acessada com sucesso");

                    DataFormatter formatter = new DataFormatter();
                    Integer success = 0;
                    Integer failed = 0;
                    List<Security> batchList = new ArrayList<>();

                    for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                        Row row = sheet.getRow(i);

                        List<String> cellValues = new ArrayList<>();
                        row.forEach(cell -> {
                            String value = formatter.formatCellValue(cell);
                            cellValues.add(value.isEmpty() || value.equalsIgnoreCase("nan") ? "0" : value);
                        });

                        try {
                            Security security = new Security(
                                    cellValues.get(0), cellValues.get(1), cellValues.get(2), cellValues.get(3),
                                    cellValues.get(4), cellValues.get(5), cellValues.get(6), cellValues.get(7),
                                    cellValues.get(8), cellValues.get(9), cellValues.get(10), cellValues.get(21)
                            );
                            batchList.add(security);

                            if (batchList.size() >= BATCH_SIZE) {
                                if (jdbcService.saveSecurity(batchList)) {
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
                        if (jdbcService.saveSecurity(batchList)) {
                            success += batchList.size();
                        } else {
                            failed += batchList.size();
                        }
                    }

                    log.registerLog(Level.INFO, "Dados de segurança salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
                }
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}
