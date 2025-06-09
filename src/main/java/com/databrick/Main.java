package com.databrick;

import com.databrick.config.AppConfig;
import com.databrick.service.DistrictService;
import com.databrick.service.EducationSystemService;
import com.databrick.service.HealthCareService;
import com.databrick.service.InfoRegionService;
import com.databrick.service.ParksService;
import com.databrick.service.PricingService;
import com.databrick.service.PropertyService;
import com.databrick.service.S3Service;
import com.databrick.service.SecurityService;
import com.databrick.service.TransportationService;
import com.databrick.utils.LoggingUtility;
import org.apache.logging.log4j.Level;
import org.apache.poi.util.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final LoggingUtility log = new LoggingUtility(Main.class.getName());
    private static final PropertyService propertyService = new PropertyService();
    private static final SecurityService securityService = new SecurityService();
    private static final InfoRegionService infoRegionService =  new InfoRegionService();
    private static final DistrictService districtService =  new DistrictService();
    private static final EducationSystemService educationSystemService =  new EducationSystemService();
    private static final HealthCareService healthCareService =  new HealthCareService();
    private static final ParksService parksService =  new ParksService();
    private static final PricingService pricingService =  new PricingService();
    private static final TransportationService transportationService =  new TransportationService();
    private static final S3Service bucketService = new S3Service(AppConfig.get("bucket.name"));
    public static void main(String[] args) {
        log.registerLog(Level.INFO, "Aplicação iniciada");

        IOUtils.setByteArrayMaxOverride(250_000_000);

        String webhookUrl = System.getenv("SLACK_WEBHOOK_URL");
        String mensagem = "Olá! Esta mensagem veio do meu JAR em Java!";
        SlackUtility.sendSlackMessage(webhookUrl, mensagem);

        try {
            log.registerLog(Level.INFO, "Iniciando leitura dos valores das células");

            List<InputStream> securityFiles;
            List<InputStream> propertyFiles;
            List<InputStream> infoRegionFiles;
            List<InputStream> districtFiles;
            List<InputStream> educationFiles;
            List<InputStream> healthCareFiles;
            List<InputStream> parksFiles;
            List<InputStream> pricingFiles;
            List<InputStream> transportationFiles;

            if ("dev".equalsIgnoreCase(AppConfig.get("mode"))) {
                log.registerLog(Level.INFO, "Modo dev ativado. Lendo arquivos locais.");

                districtFiles = loadLocalFiles(AppConfig.get("local.districts.file"));
                securityFiles = loadLocalFiles(AppConfig.get("local.security.file"));
                propertyFiles = loadLocalFiles(AppConfig.get("local.property.file"));
                infoRegionFiles = loadLocalFiles(AppConfig.get("local.region.file"));
                educationFiles = loadLocalFiles(AppConfig.get("local.education.file"));
                healthCareFiles = loadLocalFiles(AppConfig.get("local.healthcare.file"));
                parksFiles = loadLocalFiles(AppConfig.get("local.parks.file"));
                pricingFiles = loadLocalFiles(AppConfig.get("local.pricing.file"));
                transportationFiles = loadLocalFiles(AppConfig.get("local.transportation.file"));

            } else {
                log.registerLog(Level.INFO, "Modo produção. Lendo arquivos do bucket.");

                securityFiles   = bucketService.bucketObjectList(AppConfig.get("bucket.security.file"));
                districtFiles   = bucketService.bucketObjectList(AppConfig.get("bucket.districts.file"));
                educationFiles = bucketService.bucketObjectList(AppConfig.get("bucket.education.file"));
                parksFiles = bucketService.bucketObjectList(AppConfig.get("bucket.parks.file"));
                pricingFiles = bucketService.bucketObjectList(AppConfig.get("bucket.pricing.file"));
                propertyFiles = bucketService.bucketObjectList(AppConfig.get("bucket.property.file"));
                infoRegionFiles = bucketService.bucketObjectList(AppConfig.get("bucket.region.file"));
                healthCareFiles = bucketService.bucketObjectList(AppConfig.get("bucket.healthcare.file"));
                transportationFiles = bucketService.bucketObjectList(AppConfig.get("bucket.transportation.file"));
            }

            districtService.extractionDistrictData(districtFiles);
            parksService.extractionParksData(parksFiles);
            infoRegionService.extractInfoRegionData(infoRegionFiles);
            securityService.extractionSecurityData(securityFiles);
            propertyService.extractionPropertyData(propertyFiles);
            educationSystemService.extractionEducationSystemData(educationFiles);
            healthCareService.extractionHealthCareData(healthCareFiles);
            pricingService.extractionPricingData(pricingFiles);
            transportationService.extractionTransportationData(transportationFiles);

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