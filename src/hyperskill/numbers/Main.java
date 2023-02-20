package hyperskill.numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Amazing Numbers!");
        Util.instructions();
        Request request;

        do {
            System.out.println("Enter a request:");
            String userInput = scanner.nextLine();
            request = Util.checkRequest(userInput);

            switch (request) {
                case EMPTY -> Util.instructions();
                case INVALID_FIRST_NUMBER -> System.out.println("The first parameter should be a natural number or zero.");
                case INVALID_SECOND_NUMBER -> System.out.println("The second parameter should be a natural number.");
                case INVALID_THIRD_NUMBER ->
                    System.out.printf("""
                            The property [%s] is wrong.
                            Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY]
                            """, userInput.split(" ")[2]);
                case FIRST_NUMBER -> {
                    long value = Long.parseLong(userInput);
                    new Number(value).printPropertiesColumn();
                }
                case SECOND_NUMBER -> {
                    long value = Long.parseLong(userInput.split(" ")[0]);
                    int many = Integer.parseInt(userInput.split(" ")[1]);
                    for (long i = 0; i < many; i++) {
                        new Number(value++).printPropertiesRow();
                    }
                }
                case THIRD_NUMBER -> {
                    long many = Long.parseLong(userInput.split(" ")[1]);
                    String str = userInput.split(" ")[2];
                    if (Util.checkProperty(str)) {
                        System.out.println(str + " " + many);
                    }
                }
                case ZERO -> System.out.println("Goodbye!");
            }
        } while (request != Request.ZERO);
    }
}
