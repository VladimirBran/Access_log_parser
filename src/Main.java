import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите первое число и нажмите <Enter>: ");
        int firstNumber = new Scanner(System.in).nextInt();
        System.out.println("Введите второе числои нажмите <Enter>: ");
        int secondNumber = new Scanner(System.in).nextInt();
        int sum = firstNumber + secondNumber; //Сумма чисел
        System.out.println("Сумма: " + sum);
        int difference = firstNumber - secondNumber; //Вычитание чисел
        System.out.println("Разность: " + difference);
        int product = firstNumber * secondNumber; //Умножение чисел
        System.out.println("Произведение: " + product);
        double quotient = (double) firstNumber / secondNumber; //Деление чисел
        System.out.println("Частное: " + quotient);

    }
}
