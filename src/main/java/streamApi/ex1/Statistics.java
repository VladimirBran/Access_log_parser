package streamApi.ex1;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    long countRequests;
    long totalTraffic;
    long countUserAgentNotBot;
    long countErrorRequest;
    LocalDateTime minTime;
    LocalDateTime maxTime;
    HashSet<String> websitePagesURL;
    HashSet<String> refersDomains;
    HashSet<String> websiteNoPagesURL;
    HashMap<String,Integer> visitorsOS;
    HashMap<String,Integer> visitorsBrowser;
    HashMap<String,Integer> visitorsIpNotBot;

    HashMap<Integer,Integer> visitsPerSeconds;

    public Statistics(){
        this.websitePagesURL = new HashSet<>();
        this.refersDomains = new HashSet<>();
        this.websiteNoPagesURL = new HashSet<>();
        this.visitorsOS = new HashMap<>();
        this.visitorsBrowser = new HashMap<>();
        this.visitorsIpNotBot = new HashMap<>();
        this.visitsPerSeconds = new HashMap<>();
    }

    public void addEntry(LogEntry logEntry){
        totalTraffic += logEntry.getBytesTransferred();
        countRequests += 1;
        LocalDateTime currentTime = logEntry.getDateTime();
        if (minTime == null || minTime.isAfter(currentTime)){
            minTime = currentTime;
        }
        if (maxTime == null || maxTime.isBefore(currentTime)){
            maxTime = currentTime;
        }

        int statusCode = logEntry.getResponseCode();
        String url = logEntry.getRequestPath();
        if (statusCode == 200) {
            websitePagesURL.add(url);
        } else if (statusCode == 404) {
            websiteNoPagesURL.add(url);
        }
        String domain = logEntry.getRefererDomain();
        if (!domain.equals("-")) {
            refersDomains.add(domain);
        }

        if(!logEntry.isBot()){
            countUserAgentNotBot += 1;
            addItemInHashMap(visitorsIpNotBot,logEntry.getIpAddress());
            int seconds = (int) logEntry.getDateTime().toInstant(ZoneOffset.ofHours(3)).getEpochSecond();
            addItemInHashMap(visitsPerSeconds, seconds);
        }
        countErrorRequest += logEntry.getResponseCode() >= 400 ? 1 : 0;
        addItemInHashMap(visitorsOS,logEntry.getUserAgent().osType);
        addItemInHashMap(visitorsBrowser,logEntry.getUserAgent().getName());
    }

    private <K,V extends Number> void addItemInHashMap(HashMap<K,Integer> data, K increment){
        data.put(increment, data.getOrDefault(increment,0)+1);
    }
    private void addItemInHashMap3(HashMap<String,Integer> data, String item){
        data.put(item, data.getOrDefault(item,0)+1);
    }

    public HashMap<String, Double> getStatisticsVisitorOs(){
        return calculateStatistic(visitorsOS);
    }
    public HashMap<String, Double> getStatisticsVisitorBrowser(){
        return calculateStatistic(visitorsBrowser);
    }
    public String getSecondWithPeakVisitors(){
        int timestampSecond = visitsPerSeconds.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        return Instant.ofEpochSecond(timestampSecond).atZone(ZoneId.of("Europe/Moscow")).format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
    }
    public int getMaxVisitsPerSecond(){
        return visitsPerSeconds.entrySet().stream().max(Map.Entry.comparingByValue()).get().getValue();
    }
    public String getMaxVisitsPerIp(){
        String ip = visitorsIpNotBot.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();
        return visitorsIpNotBot.get(ip)+" пользователь с IP "+ ip;
    }

    private static HashMap<String, Double> calculateStatistic(HashMap<String,Integer> data){
        HashMap<String,Double> statistics = new HashMap<>();
        double totalVisitors = data.values().stream().mapToLong(Integer::intValue).sum();
        if (totalVisitors == 0) {
            statistics.put("unknown",1.0);
            return statistics;
        }
        for (var entry : data.entrySet()){
            double percentage = getPercentage(entry.getValue() ,totalVisitors);
            statistics.put(entry.getKey(), percentage);
        }
        return statistics;
    }

    private LocalDateTime getMinTime(){
        return minTime;
    }
    private LocalDateTime getMaxTime(){
        return maxTime;
    }
    public double getTotalTraffic(){
        return totalTraffic;
    }
    public long getCountRequests(){
        return countRequests;
    }
    public double getAvgAttendance(){
        int totalCountVisitNotBot = visitorsIpNotBot.values().stream().mapToInt(Integer::intValue).sum();
        return (double) (totalCountVisitNotBot/visitorsIpNotBot.size());
    }
    public double getTrafficRateBytePerHour(){
        return getTotalTraffic()/getDuration();
    }
    public double avgErrorRequestPerHour(){
        return (double) countErrorRequest/getDuration();
    }

    public double avgVisitorsPerHour(){
        return (double) countUserAgentNotBot/getDuration();
    }

    private Long getDuration(){
        if (getMinTime() == null){return 0L;}
        Duration duration = Duration.between(getMinTime(),getMaxTime());
        long durationInHours = duration.toHours();
        if (durationInHours==0){durationInHours=1;}
        return durationInHours;
    }
    public HashSet<String> getWebsitePagesURL() {
        return websitePagesURL;
    }
    public HashSet<String> getWebsiteNoPagesURL() {
        return websiteNoPagesURL;
    }
    public HashSet<String> getRefersDomains() {
        return refersDomains;
    }

    private static double getPercentage(long score, double total){
        return (double) (score * 100/ total);
    }

    public void printStatisticsUrl() {
        System.out.println("Всего уникальных URL-адресов: " + getWebsitePagesURL().size());
        System.out.println("Всего запрошенных несуществующих URL-адресов: " + getWebsiteNoPagesURL().size());
        System.out.println("Всего доменов всех рефералов: " + getRefersDomains().size());
    }

    public void printRefersDomains(){
        System.out.println("Список доменов рефералов: ");
        getRefersDomains().forEach(System.out::println);
    }
}