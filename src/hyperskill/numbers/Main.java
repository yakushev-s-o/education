package hyperskill.numbers;

import java.util.Scanner;

import static hyperskill.numbers.Util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(Messages.GREETING);
        System.out.println(Messages.INSTRUCTIONS);
        Request request;

        do {
            System.out.println(Messages.PROMPT);
            String[] inputProperty = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING"};
            String[] userInput = scanner.nextLine().toUpperCase().split(" ");
//            long[] value = numberOnly(userInput);
            String[] property = propertyOnly(userInput);
            request = checkRequest(userInput, inputProperty, property);

            switch (request) {
                case EMPTY -> System.out.println(Messages.INSTRUCTIONS);
                case INVALID_FIRST_NUMBER -> System.out.println(Messages.FIRST_ERROR);
                case INVALID_SECOND_NUMBER -> System.out.println(Messages.SECOND_ERROR);
                case INVALID_PROPERTY -> printfError(Messages.PROPERTY_ERROR, propertyError(property, inputProperty));
                case INVALID_ALL_PROPERTY -> System.out.println(Messages.INCORRECT_PROPERTIES);
                case MUTUALLY_EXCLUSIVE -> printfError(Messages.MUTUALLY_EXCLUSIVE_ERROR, property);
                case FIRST_NUMBER -> print(Long.parseLong(userInput[0]));
                case SECOND_NUMBER -> print(Long.parseLong(userInput[0]), Long.parseLong(userInput[1]));
                case PROPERTY -> print(Long.parseLong(userInput[0]), Long.parseLong(userInput[1]), property);
                case ZERO -> System.out.println(Messages.GOODBYE);
            }
        } while (request != Request.ZERO);
    }
}
