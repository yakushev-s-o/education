package hyperskill.numbers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    // Checks if the input string is a valid number.
    public static boolean isValidNumber(String userInput) {
        try {
            Long.parseLong(userInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Validates the request type based on user input and an array of valid properties.
    public static Request checkRequest(String[] userInput, String[] inputProperty, String[] property) {
        if (userInput.length == 0) {
            return Request.EMPTY;
        } else if (userInput[0].equals("0")) {
            return Request.ZERO;
        } else {
            if (isValidNumber(userInput[0]) && Number.isNatural(Long.parseLong(userInput[0]))) {
                if (userInput.length == 1) {
                    return Request.FIRST_NUMBER;
                } else if (userInput.length == 2) {
                    if (isValidNumber(userInput[1]) && Number.isNatural(Long.parseLong(userInput[1]))) {
                        return Request.SECOND_NUMBER;
                    } else {
                        return Request.INVALID_SECOND_NUMBER;
                    }
                } else {
                    if (checkAllProperties(userInput, inputProperty)) {
                        if (!checkMutuallyExclusive(userInput)) {
                            return Request.PROPERTY;
                        } else {
                            return Request.MUTUALLY_EXCLUSIVE;
                        }
                    } else {
                        if (Util.propertyError(property, inputProperty).length == 1) {
                            return Request.INVALID_PROPERTY;
                        } else {
                            return Request.INVALID_ALL_PROPERTY;
                        }
                    }
                }
            } else {
                return Request.INVALID_FIRST_NUMBER;
            }
        }
    }

    // Checks if the user input contains all valid properties.
    public static boolean checkAllProperties(String[] userInput, String[] inputProperty) {
        for (int i = 2; i < userInput.length; i++) {
            if (!Arrays.asList(inputProperty).contains(userInput[i])) {
                return false;
            }
        }

        return true;
    }

    // Returns an array of strings containing only properties from user input.
    public static String[] propertyOnly(String[] userInput) {
        if (userInput.length > 2) {
            return Arrays.copyOfRange(userInput, 2, userInput.length);
        } else {
            return new String[0];
        }
    }

    // Returns an array of strings, contains invalid properties from user input.
    public static String[] propertyError(String[] property, String[] inputProperty) {
        List<String> resultList = new ArrayList<>();

        for (String s : property) {
            if (!Arrays.asList(inputProperty).contains(s)) {
                resultList.add(s);
            }
        }

        return resultList.toArray(new String[0]);
    }

    // Checking if properties in user input are mutually exclusive.
    public static boolean checkMutuallyExclusive(String[] userInputs) {
        boolean containsEven = false;
        boolean containsOdd = false;
        boolean containsDuck = false;
        boolean containsSpy = false;
        boolean containsSquare = false;
        boolean containsSunny = false;
        boolean containsHappy = false;
        boolean containsSad = false;

        for (String input : userInputs) {
            switch (input) {
                case "EVEN" -> containsEven = true;
                case "ODD" -> containsOdd = true;
                case "DUCK" -> containsDuck = true;
                case "SPY" -> containsSpy = true;
                case "SQUARE" -> containsSquare = true;
                case "SUNNY" -> containsSunny = true;
                case "HAPPY" -> containsHappy = true;
                case "SAD" -> containsSad = true;
            }
        }

        return (containsEven && containsOdd) || (containsDuck && containsSpy) ||
                (containsSquare && containsSunny) || (containsHappy && containsSad);
    }


    // Displays properties in a row.
    public static void print(long value) {
        System.out.println(new Number(value).printPropertiesColumn());
    }

    // Displays properties in a string multiple times with value change.
    public static void print(long value, long many) {
        for (long i = 0; i < many; i++) {
            System.out.println(new Number(value++).printPropertiesRow());
        }
    }

    // Displays multiple defined properties in a row multiple times with value change ignoring negative properties.
    public static void print(long value, long many, String[] property) {
        while (many > 0) {
            String print = new Number(value++).printPropertiesRow();

            boolean found = true;
            for (String s : property) {
                if (s.startsWith("-")) {
                    if (print.toLowerCase().contains(s.substring(1).toLowerCase())) {
                        found = false;
                        break;
                    }
                } else {
                    if (!print.toLowerCase().contains(s.toLowerCase())) {
                        found = false;
                        break;
                    }
                }
            }

            if (found) {
                System.out.println(print);
                many--;
            }
        }
    }


    // Displays an error message using the formatted output from the Messages object and the Property array of lines.
    public static void printfError(Messages message, String[] property) {
        System.out.printf(message.toString(), Arrays.toString(property));
    }
}
