package com.databrick;

import com.databrick.config.AppConfig;
import com.databrick.config.ConnectionBD;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SlackUtility {
    static int ultimoIdLido = 0;

    public static void sendSlackMessage(String message) {

        try {
            URL url = URI.create(AppConfig.get("slack.webhookUrl")).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonPayload = "{\"text\": \"" + message + "\"}";

            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                System.out.println(" Mensagem enviada para o Slack!");
            } else {
                System.out.println(" Erro ao enviar mensagem. Código: " + responseCode);
            }

            conn.disconnect();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {
        fetchDataAndSendToSlack();
    }

    public static void fetchDataAndSendToSlack() {
        try {
            ConnectionBD connectionBD = new ConnectionBD();
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connectionBD.getConexao());

            sendSlackMessage("Novas propriedades encontradas, verifique!");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
