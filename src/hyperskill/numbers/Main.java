package hyperskill.numbers;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(Messages.GREETING);
        System.out.println(Messages.INSTRUCTIONS);
        Request request;

        do {
            System.out.println(Messages.PROMPT);
            String userInput = scanner.nextLine().toUpperCase();
            request = Util.checkRequest(userInput);

            switch (request) {
                case EMPTY -> System.out.println(Messages.INSTRUCTIONS);
                case INVALID_FIRST_NUMBER -> System.out.println(Messages.FIRST_ERROR);
                case INVALID_SECOND_NUMBER -> System.out.println(Messages.SECOND_ERROR);
                case INVALID_THIRD_NUMBER -> {
                    String property = userInput.split(" ")[2];
                    System.out.printf(Messages.THIRD_ERROR.toString(), property);
                }
                case INVALID_FOURTH_NUMBER -> {
                    String property1 = userInput.split(" ")[2];
                    String property2 = userInput.split(" ")[3];
                    System.out.printf(Messages.FOURTH_ERROR.toString(), property1, property2);
                }
                case MUTUALLY_EXCLUSIVE -> {
                    String property1 = userInput.split(" ")[2];
                    String property2 = userInput.split(" ")[3];
                    System.out.printf(Messages.MUTUALLY_EXCLUSIVE.toString(), property1, property2);
                }
                case FIRST_NUMBER -> {
                    long value = Long.parseLong(userInput);
                    Util.print(value);
                }
                case SECOND_NUMBER -> {
                    long value = Long.parseLong(userInput.split(" ")[0]);
                    int many = Integer.parseInt(userInput.split(" ")[1]);
                    Util.print(value, many);
                }
                case THIRD_NUMBER -> {
                    long value = Long.parseLong(userInput.split(" ")[0]);
                    int many = Integer.parseInt(userInput.split(" ")[1]);
                    String property = userInput.split(" ")[2];
                    Util.print(value, many, property);
                }
                case FOURTH_NUMBER -> {
                    long value = Long.parseLong(userInput.split(" ")[0]);
                    int many = Integer.parseInt(userInput.split(" ")[1]);
                    String property1 = userInput.split(" ")[2];
                    String property2 = userInput.split(" ")[3];
                    Util.print(value, many, property1, property2);
                }
                case ZERO -> System.out.println(Messages.GOODBYE);
            }
        } while (request != Request.ZERO);
    }
}
