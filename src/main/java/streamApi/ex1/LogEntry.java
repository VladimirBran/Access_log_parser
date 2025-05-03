import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntry {
    private final String ipAddress;
    private final LocalDateTime dateTime;
    private final HttpMethod httpMethod;
    private final String requestPath;
    private final int responseCode;
    private final int bytesTransferred;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) throws Exception {

        String logEntryPattern = "^([\\d.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";
        int NUM_FIELDS = 9;
        Pattern LOG_PATTERN = Pattern.compile(logEntryPattern);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
            throw new Exception("Problem with Expression Parsing");
        }
        this.ipAddress = matcher.group(1);
        dateTime = LocalDateTime.parse(matcher.group(4), formatter);
        String requestLine = matcher.group(5);
        String[] requestParts = requestLine.split(" ");
        this.httpMethod = HttpMethod.valueOf(requestParts[0].toUpperCase());
        this.requestPath = requestParts[1];
        this.responseCode = Integer.parseInt(matcher.group(6));
        this.bytesTransferred = Integer.parseInt(matcher.group(7));
        this.referer = matcher.group(8);
        if (!matcher.group(9).equals("-")) {
            String userAgentString = matcher.group(9);
            this.userAgent = getUserAgent(userAgentString);
        } else {
            this.userAgent = new UserAgent("none", "none", "none");
        }
    }

    public String getIpAddress() {return ipAddress;}

    public LocalDateTime getDateTime() {return dateTime;}

    public HttpMethod getHttpMethod() {return httpMethod;}

    public String getRequestPath() {return requestPath;}

    public int getResponseCode() {return responseCode;}

    public int getBytesTransferred() {return bytesTransferred;}

    public String getReferer() {return referer;}

    public String getRefererDomain() {
        String domain = this.referer;
        if (domain.length()<4){
            return "-";
        } else {
            if (domain.contains("%3A")){ domain = domain.replace("%3A",":");}
            if (domain.contains("%2F")){ domain = domain.replace("%2F","/");}
            domain = domain.split("://")[1];
            domain = domain.split("/")[0];
            if (domain.startsWith("www.")) {
                domain = domain.substring(4);
            }
            return domain;
        }

    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    public static class UserAgent {
        String botName;
        String osType;
        String browserName;

        private UserAgent(String browserName, String osType) {
            this.browserName = browserName;
            this.osType = osType;
            this.botName = "none";
        }

        private UserAgent(String browserName, String osType, String botName) {
            this.browserName = browserName;
            this.osType = osType;
            this.botName = botName;
        }

        public String getName() {
            return browserName;
        }

        public String getType() {
            return osType;
        }

        @Override
        public String toString() {
            return String.format("UserAgent name: %s, type: %s, bot: %s", browserName, osType, botName);
        }
    }

    public UserAgent getUserAgent(String userAgentString) {
        String botName;
        String browserName;
        String osType;

        browserName = extractBrowserName(userAgentString);
        osType = extractOsType(userAgentString);
        if (userAgentString.toLowerCase().contains("compatible")) {
            browserName = "compatible";
            botName = extractBotName(userAgentString);
        } else {
            botName = "none";
        }
        return new UserAgent(browserName, osType, botName);
    }

    private static String extractBotName(String userAgentString) {
        String botName = "none";
        String[] substrings = userAgentString.split(";");
        for (int i = 0; i<substrings.length-1; i++){
            if (substrings[i].contains("compatible") && i<substrings.length-1){
                String fragment = substrings[i+1].strip();//.replace(" ", "");
                if (fragment.contains("/")) {
                    botName = fragment.substring(0, fragment.indexOf("/"));
                } else {botName = fragment;}
            }
        }
        return botName;
    }

    private static String extractBrowserName(String userAgentString) {
        String[] browsers = {"Chrome", "Firefox", "Safari", "Edge", "Opera", "Internet Explorer"};
        for (String browser : browsers) {
            if (userAgentString.contains(browser)) {
                return browser;
            }
        }
        return "Unknown Browser";
    }

    private static String extractOsType(String userAgentString) {
        if (userAgentString.contains("compatible")) {
            return "Windows";
        } else if (userAgentString.contains("Windows")) {
            return "Windows";
        } else if (userAgentString.contains("Linux")) {
            return "Linux";
        } else if (userAgentString.contains("Android")) {
            return "Android";
        } else if (userAgentString.contains("Mac OS")) {
            return "Mac OS";
        } else {
            return "Unknown OS";
        }
    }

    public Boolean isBot(){
        if (!getUserAgent().botName.equals("none")){
            return  true;
        }
        return false;
    }

    enum HttpMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
    @Override
    public String toString() {
        return "LogParser{" +
                "ipAddress='" + ipAddress + '\'' +
                ", date='" + dateTime + '\'' +
                ", method='" + httpMethod + '\'' +
                ", url='" + requestPath + '\'' +
                ", statusCode=" + responseCode +
                ", bytesSent=" + bytesTransferred +
                ", refer=" + referer +
                ", userAgent='" + userAgent + '\'' +
                '}';
    }

}
