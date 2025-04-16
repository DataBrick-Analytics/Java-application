package com.databrick;

import com.databrick.service.PropertyService;
import com.databrick.service.SecurityService;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import com.databrick.config.ConnectionBD;
import org.springframework.jdbc.core.JdbcTemplate;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    private static final LoggingUtility log = new LoggingUtility();
    private static PropertyService propertyService;
    private static SecurityService securityService;

    public static void main(String[] args) {
        log.registerLog(Level.INFO, "Aplicação iniciada");
        
        ConnectionBD conexao = new ConnectionBD();
        JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

        Path pathFile = Path.of("database.xlsx");
        try (InputStream file = Files.newInputStream(pathFile);) {
            Workbook workbook = new XSSFWorkbook(file); // Instancia o arquivo como um objeto Workbook para podermos manipular as suas informações
            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha do arquivo

            log.registerLog(Level.INFO, "Iniciando leitura dos valores das células");
            if (pathFile.endsWith("dadosPropriedades.xlsx")) {
                propertyService.processDataProperty(sheet);
            } else {
                securityService.processDataSecurity(sheet);
            }
            log.registerLog(Level.INFO, "Leitura dos valores finalizada");

        } catch (Exception e) {
            log.registerLog(Level.FATAL, "Error ao executar a aplicação: " + e.getMessage());
        }

    }
}