import java.io.File;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

class Finalhomework {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int validFileCount = 0; // Счетчик правильных файлов

        while (true) {
            System.out.print("Введите путь к файлу: ");
            String path = scanner.nextLine();                //// Запрашиваем путь в консоли
            File file = new File(path);
            boolean fileExists = file.exists();   /// Переменная fileExists будет равна true, если файл существует, и false, если не существует.
            boolean isDirectory = file.isDirectory(); // является ли указанный путь путём именно к файлу, а не к папке.

            if (!fileExists || isDirectory) {
                System.out.println("Файл не существует. Попробуйте снова.");
                continue;
            }

            validFileCount++;
            System.out.println("Путь указан верно. Это файл номер " + validFileCount);

            class LongLineException extends RuntimeException {          /// /класс исключение для строки больше 1024 символов
                public LongLineException(String message) {
                    super(message);
                }
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {        /// производим чтение построчно
                String line;
                int lineCount = 0;
                int maxLength = 0;
                int minLength = Integer.MAX_VALUE;

                while ((line = reader.readLine()) != null) {
                    lineCount++;
                    int length = line.length();

                    if (length > 1024) {
                        throw new LongLineException("Строка длиннее 1024 символов: " + length);
                    }
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length < minLength) {
                        minLength = length;
                    }
                }

                System.out.println("Общее количество строк в файле: " + lineCount);
                System.out.println("Длина самой длинной строки в файле: " + maxLength);
                System.out.println("Длина самой короткой строки в файле: " + (minLength == Integer.MAX_VALUE ? 0 : minLength));
            } catch (IOException e) {
                System.out.println("Ошибка, нечитаемый формат: " + e.getMessage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}




