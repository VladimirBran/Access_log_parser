

import Exercise1.LogEntry;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

class Statistics {
    private long totalTraffic;
    private long totalTrafficPeriod;
    private long totalTrafficHour;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

//    public Statistics() {
//    }

    public void addEntries(ArrayList<LogEntry> logEntryArr) {
        if (logEntryArr.isEmpty()) {
            throw new IllegalArgumentException("Log entry list cannot be empty.");
        }

        long sumAmount = 0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        long hours = Duration.between(this.minTime, this.maxTime).toHours();

        for (int i = 0; i < logEntryArr.size() - 1; i++) {
            sumAmount += logEntryArr.get(i).getAmount();
        }

        this.totalTraffic = sumAmount;
        this.totalTrafficHour = hours > 0 ? totalTraffic / hours : totalTraffic;
    }

    public void getTrafficRate(ArrayList<CourseWork2.LogEntry> logEntryArr, LocalDateTime minTime, LocalDateTime maxTime) {
        ArrayList<CourseWork2.LogEntry> logEntryArr2 = new ArrayList<>();
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
        return "Statistics{" +
                "totalTraffic=" + totalTraffic +
                ", totalTrafficPeriod=" + totalTrafficPeriod +
                ", totalTrafficHour=" + totalTrafficHour +
                ", minTime=" + minTime +
                ", maxTime=" + maxTime +
                '}';
    }
}