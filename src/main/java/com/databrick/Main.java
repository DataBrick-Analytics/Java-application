package com.databrick;

import com.databrick.config.AppConfig;
import com.databrick.service.InfoRegionService;
import com.databrick.service.PropertyService;
import com.databrick.service.S3Service;
import com.databrick.service.SecurityService;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final LoggingUtility log = new LoggingUtility(Main.class.getName());
    private static final PropertyService propertyService = new PropertyService();
    private static final SecurityService securityService = new SecurityService();
    private static  final InfoRegionService infoRegionService =  new InfoRegionService();
  //  private static final S3Service bucketService = new S3Service(AppConfig.get("bucket.name"));
    public static void main(String[] args) {
        log.registerLog(Level.INFO, "Aplicação iniciada");

        try {
            log.registerLog(Level.INFO, "Iniciando leitura dos valores das células");

            List<InputStream> securityFiles;
            List<InputStream> propertyFiles;
            List<InputStream> infoRegionFiles;

            if ("dev".equalsIgnoreCase(AppConfig.get("mode"))) {
                log.registerLog(Level.INFO, "Modo dev ativado. Lendo arquivos locais.");

                securityFiles = loadLocalFiles(AppConfig.get("local.security.file"));
                propertyFiles = loadLocalFiles(AppConfig.get("local.property.file"));
                infoRegionFiles = loadLocalFiles(AppConfig.get("local.region.file"));

                securityService.extractionSecurityData(securityFiles);
                propertyService.extractionPropertyData(propertyFiles);
                infoRegionService.extractInfoRegionData(infoRegionFiles);

            } else {
                log.registerLog(Level.INFO, "Modo produção. Lendo arquivos do bucket.");

                //securityFiles   = bucketService.bucketObjectList(AppConfig.get("bucket.security.file"));
                //propertyFiles   = bucketService.bucketObjectList(AppConfig.get("bucket.property.file"));
                //infoRegionFiles =bucketService.bucketObjectList(AppConfig.get("bucket.region.file"));
            }


            log.registerLog(Level.INFO, "Leitura dos valores finalizada");

        } catch (Exception e) {
            log.registerLog(Level.FATAL, "Error ao executar a aplicação: " + e.getMessage());
        }
    }

    private static List<InputStream> loadLocalFiles(String paths) {
        List<InputStream> inputStreams = new ArrayList<>();
        for (String path : paths.split(",")) {
            try {
                inputStreams.add(new FileInputStream(path.trim()));
            } catch (IOException e) {
                log.registerLog(Level.ERROR, "Erro ao ler arquivo local: " + path + " - " + e.getMessage());
            }
        }
        return inputStreams;
    }
}