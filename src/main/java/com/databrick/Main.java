package com.databrick;

import com.databrick.db.ConnectionBD;
import com.databrick.entity.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import com.databrick.entity.Property;
import com.databrick.entity.Value;
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

        ConnectionBD conexao = new ConnectionBD();
        JdbcTemplate template = new JdbcTemplate(conexao.getConexao());

        Path pathFile = Path.of("database.xlsx");
        try (InputStream file = Files.newInputStream(pathFile);) {
            Workbook workbook = new XSSFWorkbook(file); // Instancia o arquivo como um objeto Workbook para podermos manipular as suas informações
            Sheet sheet = workbook.getSheetAt(0); // Pega a primeira planilha do arquivo


            for (Row row : sheet) {
                List<String> cellValues = new ArrayList<>(); // Lista onde receberemos os valores das células
                row.forEach(cell ->
                        cellValues.add(cell.getStringCellValue()) // adiciona o valor da célula na lista
                );

                for (String value : cellValues) {
                    if (value == null) {
                        // TODO adicionar log de celula vazia
                        return;
                    }
                }

                // TODO Criação dos logs

                Value propertyValue = new Value(cellValues.get(1), cellValues.get(2), cellValues.get(3));
                Address propertyAddress = new Address(
                        cellValues.get(9), cellValues.get(10), cellValues.get(11),
                        cellValues.get(12), cellValues.get(13), cellValues.get(14),
                        cellValues.get(15), cellValues.get(16), cellValues.get(17),
                        cellValues.get(18), cellValues.get(19));
                Property property = new Property(propertyValue, cellValues.get(4), cellValues.get(5), cellValues.get(6), cellValues.get(7), cellValues.get(8), propertyAddress, cellValues.get(20), cellValues.get(21));

                // TODO inserção dos dados no banco
            }

        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

    }
}