package CourseWork1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


class Main {
    public static void main(String[] args) throws IOException {
        int count = 0;
        ArrayList<LogEntry> logEntryArr = new ArrayList<>();
        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = new Scanner(System.in).nextLine();
            File file = new File(path);
            if (file.exists() && !file.isDirectory()) {
                count++;
                System.out.println("Путь указан верно. Это файл номер " + count);

                try {
                    FileReader fileReader = new FileReader(path);
                    BufferedReader reader = new BufferedReader(fileReader);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        int length = line.length();
                        if (length >= 1024) throw new LongLineException("В файле длина строки более 1024 символов");
                        line = line.replaceAll(" -", "");
                        line = line.replaceAll("\\[", "");
                        line = line.replaceAll("[+]", "");
                        line = line.replaceAll("0300] ", "");
                        LogEntry logEntry = new LogEntry(line);
                        logEntryArr.add(logEntry);
                    }
                    Statistics statistics = new Statistics();
                    statistics.addEntry(logEntryArr);

                    System.out.println("Объем трафика за час = " + statistics.getTotalTrafficHour());
//                    System.out.println("За период: ");
//                    statistics.getTrafficRate(logEntryArr, LocalDate.of(2022, Month.SEPTEMBER, 26).atTime(5, 55, 55), LocalDate.of(2022, Month.SEPTEMBER, 26).atTime(5, 55, 55));
//                    System.out.println("Общий объем за период: " + " c " + statistics.getMinTime() + " до " + statistics.getMaxTime() + " = " + statistics.getTotalTrafficPeriod());
//                    System.out.println(statistics.getPageAll().toString());
                    System.out.println("Частота встречаемости каждой ОС: "+statistics.getOs());
                    System.out.println("Доля ОС: "+statistics.getOsStatictic());
                } catch (LongLineException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
