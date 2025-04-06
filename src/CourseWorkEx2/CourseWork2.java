package CourseWorkEx2;

import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

class CourseWork2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int validFileCount = 0;

        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();           //// Запрашиваем путь в консоли
            File file = new File(path);

            if (isValidFile(file)) {
                validFileCount++;
                System.out.println("Путь указан верно. Это файл номер " + validFileCount);
                processFile(file);
            } else {
                System.out.println("Файл не существует. Попробуйте снова.");
            }
        }
    }

    private static boolean isValidFile(File file) {
        return file.exists() && !file.isDirectory();
    }

    private static void processFile(File file) {
        int lineCount = 0;
        int yandexBotCount = 0;
        int googleBotCount = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                checkLineLength(line);
                String[] parts = line.split(" ");
                for (String part : parts) {
                    if (part.contains("YandexBot")) {
                        yandexBotCount++;
                    }
                    if (part.contains("Googlebot")) {
                        googleBotCount++;
                    }
                }
                lineCount++;
            }
            printResults(lineCount, yandexBotCount, googleBotCount);
        } catch (IOException e) {
            System.err.println("Ошибка, нечитаемый формат: " + e.getMessage());
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void checkLineLength(String line) throws Exception {
        if (line.length() >= 1024) {
            throw new LongLineException("Строка длиннее 1024 символов.");
        }
    }

    private static void printResults(int lineCount, int yandexBotCount, int googleBotCount) {
        System.out.println("Количество строк в файле: " + lineCount);
        System.out.printf("YandexBot: %d, Доля запросов от YandexBot (%%): %.2f%n", yandexBotCount, ((double) yandexBotCount / lineCount) * 100);
        System.out.printf("Googlebot: %d, Доля запросов от Googlebot (%%): %.2f%n", googleBotCount, ((double) googleBotCount / lineCount) * 100);
        System.out.println();
    }
}