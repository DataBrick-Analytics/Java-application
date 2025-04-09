package com.databrick;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Main {

    private static final Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        Path pathFile = Path.of("database.xlsx");
        try (InputStream file = Files.newInputStream(pathFile);) {
            Workbook workbook = new XSSFWorkbook(file); // Instancia o arquivo como um objeto Workbook para podermos manipular as suas informações
            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha do arquivo


            for (Row row : sheet) {
                List<String> cellValues = new ArrayList<>(); // Lista onde receberemos os valores das células
                row.forEach(cell ->
                        cellValues.add(cell.getStringCellValue())
                );
            }



        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

    }
}