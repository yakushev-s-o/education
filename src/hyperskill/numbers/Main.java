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
            String userInput = scanner.nextLine();
            request = Util.checkRequest(userInput);

            switch (request) {
                case EMPTY -> System.out.println(Messages.INSTRUCTIONS);
                case INVALID_FIRST_NUMBER -> System.out.println(Messages.FIRST_ERROR);
                case INVALID_SECOND_NUMBER -> System.out.println(Messages.SECOND_ERROR);
                case INVALID_THIRD_NUMBER -> {
                    String property = userInput.split(" ")[2].toUpperCase();
                    System.out.printf(Messages.THIRD_ERROR.toString(), property);
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
                    String property = userInput.split(" ")[2].toLowerCase();
                    Util.print(value, many, property);
                }
                case ZERO -> System.out.println(Messages.GOODBYE);
            }
        } while (request != Request.ZERO);
    }
}
