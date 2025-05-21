package streamApi.ex1;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, Exception {
        System.out.print("Введите путь к файлу и нажмите <Enter>: ");
        String path = new Scanner(System.in).nextLine();
        File file = new File(path);
        boolean fileExists = file.exists();
        boolean isDirectory = file.isDirectory();
        if (isDirectory) {
            System.out.println("Указан неполный путь к файлу!");
        } else {
            System.out.println("Путь указан верно.");
            getInfo(path);
        }
    }

    public static void getInfo(String path) throws IOException, LongLineException {
        int countLines = 0;
        FileReader fileReader = new FileReader(path);
        BufferedReader reader;
        Statistics stat = new Statistics();
        int totalLines = totalCountLines(path);

        if (totalLines == 0) {
            System.out.println("Файл пуст.");
            return;
        } else {System.out.println("Строк в файле: " + totalLines);}

        try {
            reader = new BufferedReader(fileReader);
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.length() > 1024) {
                    throw new LongLineException("Строка " + countLines + " превышает 1024 символов!");
                }
                try {
                    LogEntry logEntry = new LogEntry(line);
                    stat.addEntry(logEntry);

                } catch (Exception ex) {
                    System.out.println(" строка " +countLines+"  "+ line+" "+ex.getMessage());
                }
            }
            reader.close();
        } catch (Exception ex) {
            System.out.println("Ошибка чтения файла: " + ex.getMessage());
        }

//        System.out.println("Среднее количество посещений в час: "+ (int)stat.avgVisitorsPerHour());
//        System.out.println("Средняя посещаемость пользователем: "+ (int)stat.getAvgAttendance());
//        System.out.println("Количество ошибочных запросов в час: "+ (int)stat.avgErrorRequestPerHour());
        System.out.println("Максимальная посещаемость пользователем: "+ stat.getMaxVisitsPerIp());
        System.out.println("Пиковая посещаемость "+ stat.getSecondWithPeakVisitors()+" с количеством: "+stat.getMaxVisitsPerSecond());
        stat.printStatisticsUrl();
        stat.printRefersDomains();
    }

    private static int totalCountLines(String filePath) {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lines++;
            }
        } catch (IOException ignored) {
        }
        return lines;
    }
}