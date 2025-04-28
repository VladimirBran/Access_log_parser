package CourseWork2;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ip;
    private final String method;
    private final String urlRequest;
    private final String referer;
    private final LocalDateTime date;
    private final int response;
    private final int amount;
    final UserAgent userAgent;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss", Locale.ENGLISH);

    public LogEntry(String logEntryString) {
        String[] parts = logEntryString.split(" ");
        String[] userAgentParts = logEntryString.split("\"");
        if (parts.length < 8) {
            throw new IllegalArgumentException("Invalid log entry format: " + logEntryString);
        }

        this.ip = parts[0];
        this.date = LocalDateTime.parse(parts[1], formatter);
        this.method = parts[2].replace("\"", "");
        this.urlRequest = parts[3];
        this.response = Integer.parseInt(parts[5]);
        this.amount = Integer.parseInt(parts[6]);
        this.referer = parts.length > 7 ? parts[7].replace("\"", "") : "N/A";
        this.userAgent = new UserAgent(userAgentParts[5]);
    }

    public String getIp() {
        return ip;
    }

    public String getMethod() {
        return method;
    }

    public String getUrlRequest() {
        return urlRequest;
    }

    public String getReferer() {
        return referer;
    }

    public LocalDateTime getData() {
        return date;
    }

    public int getResponse() {
        return response;
    }

    public int getAmount() {
        return amount;
    }


    @Override
    public String toString() {
        return "LogEntry{ " +
                "ip ='" + ip + '\'' +
                ", date =" + date +
                ", method ='" + method + '\'' +
                ", urlRequest ='" + urlRequest + '\'' +
                ", response =" + response +
                ", amount =" + amount +
                ", referer ='" + referer + '\'' +
                ", userAgent =" + userAgent +
                '}';
    }
}
