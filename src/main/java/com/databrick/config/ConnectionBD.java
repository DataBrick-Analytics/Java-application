package com.databrick.config;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class ConnectionBD {

    private DataSource conexao;

    public ConnectionBD() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setDriverClassName(AppConfig.get("database.driver.class.name"));
        driver.setUsername(AppConfig.get("database.username"));
        driver.setPassword(AppConfig.get("database.password"));
        driver.setUrl(AppConfig.get("database.url"));

        this.conexao = driver;
    }

    public DataSource getConexao() {
        return this.conexao;
    }
}
