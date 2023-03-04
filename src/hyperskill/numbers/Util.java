package hyperskill.numbers;

import java.util.*;

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
    public static Request checkRequest(String[] userInput, String[] property) {
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
                    if (checkAllProperties(userInput)) {
                        if (!checkMutuallyExclusive(property)) {
                            return Request.PROPERTY;
                        } else {
                            return Request.MUTUALLY_EXCLUSIVE;
                        }
                    } else {
                        if (Util.propertyError(property).length == 1) {
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
    public static boolean checkAllProperties(String[] userInput) {
        for (int i = 2; i < userInput.length; i++) {
            boolean found = false;
            for (Property property : Property.values()) {
                if (userInput[i].contains(property.name())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
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
    public static String[] propertyError(String[] property) {
        List<String> resultList = new ArrayList<>();

        for (String s : property) {
            boolean found = false;
            for (Property p : Property.values()) {
                if (s.contains(p.name())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                resultList.add(s);
            }
        }

        return resultList.toArray(new String[0]);
    }

    // Checking if properties in user input are mutually exclusive.
    public static boolean checkMutuallyExclusive(String[] strings) {
        Map<String, Integer> map = new HashMap<>();

        for (String s : strings) {
            for (Property p : Property.values()) {
                if (s.contains(p.name())) {
                    if (s.startsWith("-")) {
                        p.changeValue();
                        map.put(s, p.code);
                        p.changeValue();
                    } else {
                        map.put(s, p.code);
                    }
                    break;
                }
            }
        }

        /*
        If the keys are not equal, the values are equal and both keys do not start with a "-" character,
        or if one key without a "-" character is equal to the other, or "ODD" is equal to "EVEN".
         */
        for (Map.Entry<String, Integer> entry1 : map.entrySet()) {
            for (Map.Entry<String, Integer> entry2 : map.entrySet()) {
                if (!entry1.getKey().equals(entry2.getKey()) &&
                        entry1.getValue().equals(entry2.getValue()) &&
                        !entry1.getKey().startsWith("-") &&
                        !entry2.getKey().startsWith("-") ||
                        entry1.getKey().substring(1).equals(entry2.getKey()) ||
                        entry2.getKey().substring(1).equals(entry1.getKey()) ||
                        entry1.getKey().equals("ODD") && entry2.getKey().equals("EVEN") ||
                        entry1.getKey().equals("-ODD") && entry2.getKey().equals("-EVEN")) {
                    return true;
                }
            }
        }

        return false;
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
