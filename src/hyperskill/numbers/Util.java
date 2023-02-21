package hyperskill.numbers;

public class Util {

    // Displays a specific property in a string multiple times with a value change
    public static void print(long value, int many, String property) {
        String print;
        while (many > 0) {
            print = new Number(value++).printPropertiesRow();
            if (print.contains(property)) {
                System.out.println(print);
                many--;
            }
        }
    }

    // Displays properties in a string multiple times with value change
    public static void print(long value, int many) {
        for (long i = 0; i < many; i++) {
            System.out.println(new Number(value++).printPropertiesRow());
        }
    }

    // Displays properties in a row
    public static void print(long value) {
        System.out.println(new Number(value).printPropertiesColumn());
    }

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
}
