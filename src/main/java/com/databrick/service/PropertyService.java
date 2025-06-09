package com.databrick.service;

import com.databrick.entity.Property;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PropertyService {
    private static final int BATCH_SIZE = 1000;
    private final LoggingUtility log = new LoggingUtility(PropertyService.class.getName());
    private final JDBCService jdbcService = new JDBCService();

    public void extractionPropertyData(List<InputStream> bucketObjects) {
        log.registerLog(Level.INFO, "Iniciando o processamento de dados de propriedades");

        try {
            for (InputStream bucketObject : bucketObjects) {
                processExcelFile(bucketObject);
            }
        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao tentar processar os dados. Message: " + e.getMessage());
        }
    }

    private void processExcelFile(InputStream inputStream) throws IOException {
        List<Property> batchList = new ArrayList<>();
        Integer success = 0;
        Integer failed = 0;

        try {
            OPCPackage pkg = OPCPackage.open(inputStream);
            XSSFReader reader = new XSSFReader(pkg);
            SharedStringsTable sst = (SharedStringsTable) reader.getSharedStringsTable();
            XMLReader parser = fetchSheetParser(sst, batchList, success, failed);

            InputStream sheet = reader.getSheet("rId1");
            InputSource sheetSource = new InputSource(sheet);

            try {
                parser.parse(sheetSource);
            } finally {
                sheet.close();
                pkg.close();
            }

            if (!batchList.isEmpty()) {
                if (jdbcService.saveProperty(batchList)) {
                    success += batchList.size();
                } else {
                    failed += batchList.size();
                }
            }

            log.registerLog(Level.INFO, "Dados de propriedade salvos no banco. Sucesso: " + success + " dado(s). Falha: " + failed + " dado(s)");

        } catch (Exception e) {
            log.registerLog(Level.ERROR, "Erro ao processar planilha: " + e.getMessage());
        }
    }

    private XMLReader fetchSheetParser(SharedStringsTable sst, List<Property> batchList, Integer success, Integer failed)
            throws SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XMLReader parser = factory.newSAXParser().getXMLReader();
        ContentHandler handler = new SheetHandler(sst, batchList, success, failed);
        parser.setContentHandler(handler);
        return parser;
    }

    private class SheetHandler extends DefaultHandler {
        private final SharedStringsTable sst;
        private final List<Property> batchList;
        private Integer success;
        private Integer failed;
        private String lastContents;
        private boolean nextIsString;
        private int currentRow = 0;
        private int currentCol = 0;
        private String districtCode = null;
        private String iptuUse = null;

        private SheetHandler(SharedStringsTable sst, List<Property> batchList, Integer success, Integer failed) {
            this.sst = sst;
            this.batchList = batchList;
            this.success = success;
            this.failed = failed;
        }

        @Override
        public void startElement(String uri, String localName, String name, Attributes attributes) {
            if ("c".equals(name)) {
                String cellType = attributes.getValue("t");
                nextIsString = cellType != null && cellType.equals("s");
            }
            lastContents = "";
        }

        @Override
        public void endElement(String uri, String localName, String name) {
            if (nextIsString) {
                try {
                    int idx = Integer.parseInt(lastContents);
                    lastContents = sst.getItemAt(idx).getString();
                } catch (NumberFormatException e) {
                    // Em caso de erro, mantém o lastContents original
                }
                nextIsString = false;
            }

            if ("v".equals(name)) {
                if (currentRow > 0) { // Ignora cabeçalho
                    if (currentCol == 35) { // Coluna do código do distrito
                        districtCode = lastContents;
                    } else if (currentCol == 17) { // Coluna do uso do IPTU
                        iptuUse = lastContents;
                    }
                }
                currentCol++;
            } else if ("row".equals(name)) {
                if (currentRow > 0 && districtCode != null && iptuUse != null) {
                    try {
                        Property property = new Property(districtCode, iptuUse);
                        batchList.add(property);

                        if (batchList.size() >= BATCH_SIZE) {
                            if (jdbcService.saveProperty(batchList)) {
                                success += batchList.size();
                            } else {
                                failed += batchList.size();
                            }
                            batchList.clear();
                        }
                    } catch (Exception e) {
                        log.registerLog(Level.ERROR, "Erro ao processar linha " + currentRow + ": " + e.getMessage());
                        failed++;
                    }
                }
                currentRow++;
                currentCol = 0;
                districtCode = null;
                iptuUse = null;
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            lastContents += new String(ch, start, length);
        }
    }
}