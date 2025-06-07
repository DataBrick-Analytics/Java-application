package com.databrick.service;

import com.databrick.entity.EducationSystem;
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

public class EducationSystemService {
    private final LoggingUtility log = new LoggingUtility(EducationSystemService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionEducationSystemData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO,"Iniciando o processamento de dados do sistema de educação");
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

                    EducationSystem educationSystem = new EducationSystem(cellValues.get(3), cellValues.get(10), cellValues.get(16), cellValues.get(17));

                    Boolean wasSaved = jdbcService.saveEducationSystem(educationSystem);
                    if (wasSaved) success++; else failed++;
                }
                log.registerLog(Level.INFO, "Dados do sistema de educação salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }
}

