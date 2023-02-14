package hyperskill.numbers;

import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!");
        instructions();

        while (true) {
            System.out.println("Enter a request:");
            long num = scanner.nextLong();

            if (isnNaturalNumbers(num)) {
                System.out.printf(Locale.US, """
                                Properties of %,d
                                        even: %b
                                         odd: %b
                                        buzz: %b
                                        duck: %b
                                 palindromic: %b
                                """,
                        num, !isOdd(num), isOdd(num),
                        isBuzzNumber(num), isDuck(num),
                        palindromic(num));
            } else if (num == 0) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("The first parameter should be a natural number or zero.");
            }
        }
    }

    private static boolean palindromic(long num) {
        long numSrc = num;
        long reversed = 0;

        while(numSrc != 0) {
            reversed = reversed * 10 + (numSrc % 10);
            numSrc /= 10;
        }

        return num == reversed;
    }

    private static void instructions() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter 0 to exit.""");
    }

    private static boolean isDuck(long num) {
        long numTrim = num;
        for (long i = 10; i <= num; i *= 10) {
            if (numTrim % 10 == 0) {
                return true;
            }
            numTrim /= 10;
        }
        return false;
    }

    private static boolean isBuzzNumber(long num) {
        return num % 7 == 0 || num % 10 == 7;
    }

    private static boolean isOdd(long num) {
        return (num & 1) == 1;
    }

    private static boolean isnNaturalNumbers(long num) {
        return num > 0;
    }
}
