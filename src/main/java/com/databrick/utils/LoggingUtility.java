package com.databrick.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.builder.impl.DefaultConfigurationBuilder;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoggingUtility {

    private Logger log;

    public LoggingUtility() {
        String filePath = "logs/";
        LocalDate presentDay = LocalDate.now();

        File logFile = new File(filePath + "log_" + presentDay.format(DateTimeFormatter.ofPattern("dd_MM_yyyy")));

        try {
            logFile.getParentFile().mkdirs();

            if (logFile.createNewFile()) {
                log.info("Arquivo criado com sucesso! Caminho: {}", logFile.getPath());
            } else {
                log.warn("O arquivo já existe! Caminho: {}", logFile.getPath());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private void configureLogger() {
        ConfigurationBuilder<BuiltConfiguration> builder = new DefaultConfigurationBuilder<>();
        builder.setConfigurationName("ConfigLog");

        String presentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String fileName = "logs/log " + presentDate + ".txt";

        File file = new File(fileName);
        file.getParentFile().mkdirs();

        String pattern = "%d{yyyy-MM-dd HH:mm:ss} | %-5level | %c{1} | %msg%n";

        AppenderComponentBuilder appender = builder.newAppender("Arquivo", "File")
                .addAttribute("fileName", fileName)
                .add(builder.newLayout("PatternLayout").addAttribute("pattern", pattern));
        builder.add(appender);

        builder.add(builder.newRootLogger(Level.DEBUG).add(builder.newAppenderRef("Arquivo")));

        Configurator.initialize(builder.build());

        this.log = LogManager.getLogger("AppLogger");
    }

    public void registerLog(String message, String level) {
        if (log == null) {
            configureLogger();
        }

        switch (level.toUpperCase()) {
            case "WARN":  log.warn(message);  break;
            case "ERROR": log.error(message); break;
            case "DEBUG": log.debug(message); break;
            case "FATAL": log.fatal(message); break;
            default:      log.info(message);
        }
    }
}
