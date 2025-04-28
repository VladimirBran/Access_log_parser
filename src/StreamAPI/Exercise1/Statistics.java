package Exercise1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Statistics {
    LogEntry logEntry;
    long bot;
    private long totalTraffic, totalTrafficPeriod, totalTrafficHour;
    private LocalDateTime minTime, maxTime;
    private final HashSet<String> pageAll;
    private final HashMap<String, Integer> os=new HashMap<>();
    private final HashMap<String, Double> osStatistic=new HashMap<>();
    private final HashMap<String, Integer> browser = new HashMap<>();
    private final HashMap<String, Double> browserStatistic = new HashMap<>();
    private final HashSet<String> pageError = new HashSet<>();
    private long hours;

    public Statistics() {
        pageAll = new HashSet<>();
    }

    public void addEntry(ArrayList<LogEntry> logEntryArr) {
        long sumAmount = 0;
        int count=0;
        long sumBot=0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        this.hours = Duration.between(this.minTime, this.maxTime).toHours();
        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            sumAmount += logEntryArr.get(i).getAmount();
            if (logEntryArr.get(i).getResponse()==200){
                pageAll.add(logEntryArr.get(i).getUrlRequest());
            }
            if(os.containsKey(logEntryArr.get(i).userAgent.getOs())){
                os.put(logEntryArr.get(i).userAgent.getOs(),os.get(logEntryArr.get(i).userAgent.getOs())+1);}
            else {
                os.put(logEntryArr.get(i).userAgent.getOs(),1);
            }
            if (logEntryArr.get(i).getResponse() == 500) {
                pageError.add(logEntryArr.get(i).getUrlRequest());
            }
            if (browser.containsKey(logEntryArr.get(i).userAgent.getBrowser())) {
                browser.put(logEntryArr.get(i).userAgent.getBrowser(), browser.get(logEntryArr.get(i).userAgent.getBrowser()) + 1);
            } else {
                browser.put(logEntryArr.get(i).userAgent.getBrowser(), 1);
            }
            if (logEntryArr.get(i).userAgent.getBot()){
                sumBot=sumBot+1;
            }
        }
        this.totalTraffic = sumAmount;
        this.totalTrafficHour = totalTraffic / hours;
        this.bot=sumBot;
    }


    public void getTrafficRate(ArrayList<LogEntry> logEntryArr, LocalDateTime minTime, LocalDateTime maxTime) {
        ArrayList<LogEntry> logEntryArr2 = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            if ((logEntryArr.get(i).getData().isEqual(minTime) || logEntryArr.get(i).getData().isAfter(minTime))
                    && ((logEntryArr.get(i).getData().isEqual(maxTime) || logEntryArr.get(i).getData().isBefore(maxTime)))) {
                logEntryArr2.add(logEntryArr.get(i));
            }
        }
        for (int i = 0; i < logEntryArr2.size() - 1; i++) {
            System.out.println(logEntryArr2.get(i));
            sum += logEntryArr2.get(i).getAmount();
        }
        this.totalTrafficPeriod = sum;
    }

    public HashSet<String> getPageAll() {
        return pageAll;
    }

    public HashMap<String, Integer> getOs() {
        return os;
    }

    public HashMap<String, Double> getOsStatistic() {
        int sumOs = os.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : os.entrySet()) {
            osStatistic.put(entry.getKey(), (double)entry.getValue()/sumOs);
        }
        return osStatistic;
    }
    public HashSet<String> getPageError() {
        return pageError;
    }
    public HashMap<String, Integer> getBrowser() {
        return browser;
    }
    public HashMap<String, Double> getBrowserStatistic() {
        double sumBrowser = browser.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : browser.entrySet()) {
            browserStatistic.put(entry.getKey(), entry.getValue() / sumBrowser);
        }
        return browserStatistic;
    }
    public long averageVisitsPerHour() {
        int numVst = 0;
        for (Map.Entry<String, Integer> entry : browser.entrySet()) {
            if (!browser.containsKey("null")) {
                numVst += entry.getValue();
            }
        }
        return numVst / hours;
    }

    public long averageNumErrorRequests() {
        return pageError.size() / hours;
    }

    public double averageVisitsPerUser(ArrayList<LogEntry> logEntryArr) {
        List<LogEntry> list = new ArrayList<>();
        for (LogEntry entry : logEntryArr) {
            if (!Objects.isNull(entry.userAgent.getBrowser())) {
                if (Objects.equals(entry.userAgent.getBot(), false)) {
                    list.add(entry);
                }
            }
        }
        HashMap<String, String> mapList= (HashMap<String, String>) list.stream().collect(Collectors.toMap(logEntry1 ->
                logEntry1.getIp(), logEntry1 -> logEntry1.userAgent.getBrowser(), (oldValue,newValue) -> newValue));

        return (double)  mapList.size()/list.size();
    }

    public long getTotalTraffic() {
        return totalTraffic;
    }

    public long getTotalTrafficPeriod() {
        return totalTrafficPeriod;
    }

    public long getTotalTrafficHour() {
        return totalTrafficHour;
    }

    public LocalDateTime getMinTime() {
        return minTime;
    }

    public LocalDateTime getMaxTime() {
        return maxTime;
    }

    public long getBot() {  return bot; }

    @Override
    public String toString() {
        return "Statistics{ " +
                "logEntry = " + logEntry +
                ", totalTraffic = " + getTotalTraffic() +
                ", minTime = " + getMinTime() +
                ", maxTime = " + getMaxTime() +
                ", TotalTrafficHour"+ getTotalTrafficHour()+
                ", st.getOs" +getOs()+
                ", getOsStatictic"+ getOsStatistic()+
                ", getBrowser"+ getBrowser()+
                ", getBrowserStat"+getBrowserStatistic()+
                ", averageVisitsPerHour"+ averageVisitsPerHour()+
                ", averageNumErrorRequests"+ averageNumErrorRequests()+
//                ", averageVisitsPerUser"+averageVisitsPerUser()+
                '}';
    }
}
