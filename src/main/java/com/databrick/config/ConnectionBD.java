package com.databrick.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class ConnectionBD {

    private DataSource conexao;
    private Properties properties;

    public ConnectionBD() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setUsername(properties.getProperty("database.username"));
        driver.setPassword(properties.getProperty("database.password"));
        driver.setUrl(properties.getProperty("database.url"));

        this.conexao = driver;
    }

    public DataSource getConexao() {
        return this.conexao;
    }
}
