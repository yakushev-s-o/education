package hyperskill.numbers;

public class Util {

    public static Request checkRequest(String userInput) {
        if (userInput.length() == 0) {
            return Request.EMPTY;
        } else if (userInput.equals("0")) {
            return Request.ZERO;
        } else {
            String[] arr = userInput.split(" ");

            if (isValidNumber(arr[0]) && Number.isNatural(Long.parseLong(arr[0]))) {
                if (arr.length == 1) {
                    return Request.FIRST_NUMBER;
                } else if (arr.length == 2) {
                    if (isValidNumber(arr[1]) && Number.isNatural(Long.parseLong(arr[1]))) {
                        return Request.SECOND_NUMBER;
                    } else {
                        return Request.INVALID_SECOND_NUMBER;
                    }
                } else {
                    if (checkProperty(arr[2])) {
                        return Request.THIRD_NUMBER;
                    } else {
                        return Request.INVALID_THIRD_NUMBER;
                    }
                }
            } else {
                return Request.INVALID_FIRST_NUMBER;
            }
        }
    }

    public static boolean checkProperty(String userInput) {
        String[] property = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY"};

        for (String s : property) {
            if (userInput.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isValidNumber(String userInput) {
        try {
            Long.parseLong(userInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void instructions() {
        System.out.println("""
                Supported requests:
                - enter a natural number to know its properties;
                - enter two natural numbers to obtain the properties of the list:
                  * the first parameter represents a starting number;
                  * the second parameter shows how many consecutive numbers are to be printed;
                - two natural numbers and a property to search for;
                - separate the parameters with one space;
                - enter 0 to exit.""");
    }
}
