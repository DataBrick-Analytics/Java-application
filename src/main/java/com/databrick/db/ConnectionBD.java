package com.databrick.db;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

public class ConnectionBD {
    private DataSource conexao;

    public ConnectionBD() {
        DriverManagerDataSource driver = new DriverManagerDataSource();
        driver.setUsername("");
        driver.setPassword("");
        driver.setUrl("jdbc:mysql://localhost:3306/seu_banco");
        driver.setDriverClassName("com.mysql.cj.jdbc.Driver");

        this.conexao = driver;
    }

    public DataSource getConexao() {
        return this.conexao;
    }
}
