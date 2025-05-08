package com.databrick.utils;

import com.databrick.service.JDBCService;
import org.apache.logging.log4j.Level;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoggingUtility {

    public interface LogSaver {
        void saveLog(List<String> values);
    }

    private final String processType;
    private final LogSaver logSaver;

    public LoggingUtility(String processType) {
        this(processType, null);
    }

    public LoggingUtility(String processType, LogSaver logSaver) {
        this.processType = processType;
        this.logSaver = logSaver;
    }

    public void registerLog(Level level, String message) {
        List<String> values = new ArrayList<>();

        if (level != null && message != null) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String user = "System";

            values.add(dateTime);
            values.add(processType);
            values.add(level.toString());
            values.add(message);
            values.add(user);

            if (logSaver != null) {
                logSaver.saveLog(values);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("[").append(dateTime).append("] ");
            sb.append("[").append(processType).append("] ");
            sb.append("[").append(level.toString()).append("] ");
            sb.append("Usuário: ").append(user).append(" - ").append(message);
            System.out.println(sb.toString());
        }
    }
}
