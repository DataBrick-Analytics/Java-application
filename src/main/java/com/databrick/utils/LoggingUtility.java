package com.databrick.utils;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

public class LoggingUtility {

    private Logger log;

    public void registerLog(Level level, String message) {
        String sqlScript = "INSERT INTO tb_logs (data_hora, tipo_processo, status, mensagem, usuario) VALUES (?, ?, ?, ?, ?)";

        // TODO validação e integração dos campos

//        try () {
//            // TODO conectar com o banco
//        } catch (Exception e) {
//            // TODO logar no console o erro de conexão
//        }
    }
}
