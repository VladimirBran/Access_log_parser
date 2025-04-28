package CourseWork2;


public class UserAgent {
    private String userAgentString;
    private String os;
    private String browser;

    public UserAgent(String userAgentString) {
        this.userAgentString = userAgentString;
        this.browser = detectBrowser(userAgentString);
        this.os = detectOs(userAgentString);
    }

    private String detectBrowser(String userAgent) {
        if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Opera")) {
            return "Opera";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Edge")) {
            return "Edge";
        } else {
            return "Other";
        }
    }

    private String detectOs(String userAgent) {
        if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Macintosh")) {
            return "Macintosh";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else {
            return "Other";
        }
    }

    public String getOs() {
        return os;
    }

    public String getBrowser() {
        return browser;
    }

    @Override
    public String toString() {
        return "User Agent{ " +
                "os = '" + os + '\'' +
                ", browser = '" + browser + '\'' +
                '}';
    }
}

