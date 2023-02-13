package hyperskill.numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a natural number:");
        int num = scanner.nextInt();

        if (isnNaturalNumbers(num)) {
            System.out.println(isOdd(num) ? "This number is Odd." : "This number is Even.");
            System.out.println(isBuzzNumber(num) ? "It is a Buzz number." : "It is not a Buzz number.");
        } else {
            System.out.println("This number is not natural!");
        }

        printExplanationBuzz(num);
    }

    private static boolean isnNaturalNumbers(int num) {
        return num > 0;
    }

    private static boolean isOdd(int num) {
        return (num & 1) == 1;
    }

    private static boolean isBuzzNumber(int num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    private static void printExplanationBuzz(int num) {
        System.out.println("Explanation:");

        if (num % 7 == 0 && num % 10 == 7) {
            System.out.println(num + " is divisible by 7 and ends with 7");
        } else if (num % 7 == 0) {
            System.out.println(num + " is divisible by 7");
        } else if (num % 10 == 7) {
            System.out.println(num + " ends with 7");
        } else {
            System.out.println(num + " is neither divisible by 7 nor does it end with 7");
        }
    }
}
