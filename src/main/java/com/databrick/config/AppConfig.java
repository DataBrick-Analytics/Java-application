package com.databrick.config;

import java.io.InputStream;
import java.util.Properties;

public class AppConfig {
    private static final Properties properties = new Properties();

    static {
        try (InputStream input = AppConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                properties.load(input);
            } else {
                throw new RuntimeException("Arquivo application.properties não encontrado");
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar application.properties", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
