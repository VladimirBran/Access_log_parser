import java.io.File;
import java.util.Scanner;

public class Finalhomework {
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
        }
    }
}

