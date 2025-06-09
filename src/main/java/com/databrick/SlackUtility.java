package com.databrick;

import com.databrick.config.ConnectionBD;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class SlackUtility {
    private static final String webhookUrl = "https://hooks.slack.com/services/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";
    static int ultimoIdLido = 0;

    public static void sendSlackMessage(String webhookUrl, String message) {

        try {
            URL url = new URL(webhookUrl);
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

            String sql = "SELECT * FROM notificacao WHERE id_notificacacoes > ? ORDER BY id_notificacacoes DESC LIMIT 1";
            List<Map<String, Object>> dados = jdbcTemplate.queryForList(sql, ultimoIdLido);

            for (int i = 0; i < dados.size(); i++) {
                Map<String, Object> linha = dados.get(i);
                int id = (int) linha.get("id_notificacacoes");
                sendSlackMessage(webhookUrl, "Novas propriedades encontradas, verifique!");
                ultimoIdLido = id;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
