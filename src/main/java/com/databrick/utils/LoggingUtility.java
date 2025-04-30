package com.databrick.utils;

import com.databrick.service.JDBCService;
import org.apache.logging.log4j.Level;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoggingUtility {

    private String processType;
    private final JDBCService jdbcService = new JDBCService();

    public LoggingUtility(String processType) {
        this.processType = processType;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public void registerLog(Level level, String message) {
        List<String> values = new ArrayList<>();

        if (level != null && message != null) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
            String user = "System";

            values.add(dateTime);
            values.add(getProcessType());
            values.add(level.toString());
            values.add(message);
            values.add(user);

            jdbcService.saveLog(values);

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(dateTime).append("] ");
            sb.append("[").append(getProcessType()).append("] ");
            sb.append("[").append(level.toString()).append("] ");
            sb.append("Usuário: ").append(user).append(" - ").append(message);
            System.out.println(sb.toString());
        }
    }
}
