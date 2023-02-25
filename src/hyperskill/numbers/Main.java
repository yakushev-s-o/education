package hyperskill.numbers;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(Messages.GREETING);
        System.out.println(Messages.INSTRUCTIONS);
        Request request;

        do {
            System.out.println(Messages.PROMPT);
            String[] inputProperty = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY"};
            String[] userInput = scanner.nextLine().toUpperCase().split(" ");
            long[] value = Util.numberOnly(userInput);
            String[] property = Util.propertyOnly(userInput);
            request = Util.checkRequest(userInput, inputProperty, property);

            switch (request) {
                case EMPTY -> System.out.println(Messages.INSTRUCTIONS);
                case INVALID_FIRST_NUMBER -> System.out.println(Messages.FIRST_ERROR);
                case INVALID_SECOND_NUMBER -> System.out.println(Messages.SECOND_ERROR);
                case INVALID_PROPERTY -> System.out.printf(Messages.PROPERTY_ERROR.toString(), Arrays.toString(Util.propertyError(property, inputProperty)));
                case INVALID_ALL_PROPERTY -> System.out.println(Messages.INCORRECT_PROPERTIES);
                case MUTUALLY_EXCLUSIVE -> System.out.printf(Messages.MUTUALLY_EXCLUSIVE_ERROR.toString(), Arrays.toString(property));
                case FIRST_NUMBER -> Util.print(value[0]);
                case SECOND_NUMBER -> Util.print(value[0], value[1]);
                case PROPERTY -> Util.print(value[0], value[1], property);
                case ZERO -> System.out.println(Messages.GOODBYE);
            }
        } while (request != Request.ZERO);
    }
}
