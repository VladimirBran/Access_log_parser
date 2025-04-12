package New1.Ex3;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

class Statistics {
    private long totalTraffic;
    private long totalTrafficPeriod;
    private long totalTrafficHour;
    private LocalDateTime minTime;
    private LocalDateTime maxTime;

    public Statistics() {
    }

    public void addEntries(ArrayList<LogEntry> logEntryArr) {
        if (logEntryArr.isEmpty()) {
            throw new IllegalArgumentException("Log entry list cannot be empty.");
        }

        long sumAmount = 0;
        this.minTime = logEntryArr.get(0).getData();
        this.maxTime = logEntryArr.get(logEntryArr.size() - 1).getData();
        long hours = Duration.between(this.minTime, this.maxTime).toHours();

        for (LogEntry entry : logEntryArr) {
            sumAmount += entry.getAmount();
        }

        this.totalTraffic = sumAmount;
        this.totalTrafficHour = hours > 0 ? totalTraffic / hours : totalTraffic;
    }

    public void getTrafficRate(ArrayList<LogEntry> logEntryArr, LocalDateTime minTime, LocalDateTime maxTime) {
        ArrayList<LogEntry> filteredEntries = new ArrayList<>();
        int sum = 0;

        for (LogEntry entry : logEntryArr) {
            if ((entry.getData().isEqual(minTime) || entry.getData().isAfter(minTime)) &&
                    (entry.getData().isEqual(maxTime) || entry.getData().isBefore(maxTime))) {
                filteredEntries.add(entry);
            }
        }

        for (LogEntry entry : filteredEntries) {
            System.out.println(entry);
            sum += entry.getAmount();
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
