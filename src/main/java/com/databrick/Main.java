package com.databrick;

import com.databrick.service.PropertyService;
import com.databrick.utils.LoggingUtility;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final LoggingUtility log = new LoggingUtility();
    private static PropertyService propertyService;

    public static void main(String[] args) {

        log.registerLog("Aplicação iniciada", "info");

        Path pathFile = Path.of("database.xlsx");
        try (InputStream file = Files.newInputStream(pathFile);) {
            Workbook workbook = new XSSFWorkbook(file); // Instancia o arquivo como um objeto Workbook para podermos manipular as suas informações
            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha do arquivo

            log.registerLog("Iniciando leitura dos valores das células", "info");
            propertyService.processData(sheet);
            log.registerLog("Leitura dos valores finalizada", "info");

        } catch (Exception e) {
            log.registerLog("Error ao executar a aplicação: " + e.getMessage(), "FATAL");
        }

    }
}