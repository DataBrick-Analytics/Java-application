package com.databrick;

import com.databrick.service.PropertyService;
import com.databrick.service.S3Service;
import com.databrick.service.SecurityService;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {

    private static final LoggingUtility log = new LoggingUtility(Main.class.getName());
    private static final PropertyService propertyService = new PropertyService();
    private static final SecurityService securityService = new SecurityService();
    private static Properties properties;
    private static S3Service bucketService = new S3Service(properties.getProperty("bucket.name"));

    public static void main(String[] args) {
        log.registerLog(Level.INFO, "Aplicação iniciada");

        try {
            log.registerLog(Level.INFO, "Iniciando leitura dos valores das células");

            List<InputStream> propertyFiles = bucketService.bucketObjectList(properties.getProperty("bucket.property.file"));
            propertyService.processDataProperty(propertyFiles);

            List<InputStream> securityFiles = bucketService.bucketObjectList(properties.getProperty("bucket.security.file"));
            securityService.processDataSecurity(securityFiles);

            log.registerLog(Level.INFO, "Leitura dos valores finalizada");

        } catch (Exception e) {
            log.registerLog(Level.FATAL, "Error ao executar a aplicação: " + e.getMessage());
        }

    }
}