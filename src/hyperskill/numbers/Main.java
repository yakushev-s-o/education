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
            String inputStr = scanner.nextLine();

            if (inputStr.length() == 0) {
                instructions();

            } else if (inputStr.equals("0")) {
                System.out.println("Goodbye!");
                break;
            } else {
                String[] arr = inputStr.split(" ");

                if (isnNaturalNumbers(arr[0])) {
                    long firstNum = Long.parseLong(arr[0]);

                    if (arr.length == 1) {
                        printFirst(firstNum);
                    } else {
                        if (isnNaturalNumbers(arr[1])) {
                            long secondNum = Long.parseLong(arr[1]);

                            for (long i = 0; i < secondNum; i++) {
                                printSecond(firstNum++);
                            }
                        } else {
                            System.out.println("The second parameter should be a natural number.");
                        }
                    }
                } else {
                    System.out.println("The first parameter should be a natural number or zero.");
                }
            }
        }
    }

    public static void printSecond(long num) {
        String odd = isOdd(num) ? "odd" : "even";
        String buzz = isBuzzNumber(num) ? ", buzz" : "";
        String duck = isDuck(num) ? ", duck" : "";
        String palindromic = isPalindromic(num) ? ", palindromic" : "";
        String gapful = isGapful(num) ? ", gapful" : "";

        System.out.printf(Locale.US, "%d is %s%s%s%s%s\n", num, odd, buzz, duck, palindromic, gapful);
    }

    private static void printFirst(long num) {
        System.out.printf(Locale.US, """
                        Properties of %,d
                                even: %b
                                 odd: %b
                                buzz: %b
                                duck: %b
                         palindromic: %b
                              gapful: %b
                        """,
                num, !isOdd(num), isOdd(num),
                isBuzzNumber(num), isDuck(num),
                isPalindromic(num), isGapful(num));
    }

    private static boolean isGapful(long num) {
        long n = num;
        int count = 0;
        while (n > 9) {
            n /= 10;
            count++;
        }
        long temp = n * 10 + num % 10;
        return count > 2 && num % temp == 0;
    }

    private static boolean isPalindromic(long num) {
        long numSrc = num;
        long reversed = 0;

        while (numSrc != 0) {
            reversed = reversed * 10 + (numSrc % 10);
            numSrc /= 10;
        }

        return num == reversed;
    }

    private static void instructions() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - separate the parameters with one space;
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

    private static boolean isnNaturalNumbers(String str) {
        try {
            return Long.parseLong(str) > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
