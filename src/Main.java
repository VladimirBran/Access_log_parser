import java.util.Scanner;

import static java.awt.SystemColor.text;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введит етекст и нажмите <Enter>: ");
        String text = new Scanner(System.in).nextLine();
        System.out.println("Длинна текста: " + text.length());
    }
}
