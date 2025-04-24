package CourseWork1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    LogEntry logEntry;
    private long totalTraffic, totalTrafficPeriod, totalTrafficHour;
    private LocalDateTime minTime, maxTime;
    private HashSet<String> pageAll =new HashSet<>();
    private HashMap<String, Integer> os=new HashMap<>();
    private HashMap<String, Double> osStatictic=new HashMap<>();

    public void addEntry(ArrayList<LogEntry> logEntryArr) {
        long sumAmount = 0;
        int count=0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        long hours = Duration.between(this.minTime, this.maxTime).toHours();
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
        }
        this.totalTraffic = sumAmount;
        this.totalTrafficHour = totalTraffic / hours;
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

    public HashMap<String, Double> getOsStatictic() {
        int sumOs = os.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : os.entrySet()) {
            osStatictic.put(entry.getKey(), (double)entry.getValue()/sumOs);
        }
        return osStatictic;
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

    @Override
    public String toString() {
        return "Statistics{ " +
                "logEntry = " + logEntry +
                ", totalTraffic = " + getTotalTraffic() +
                ", minTime = " + getMinTime() +
                ", maxTime = " + getMaxTime() +
                '}';
    }
}
