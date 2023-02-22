package hyperskill.numbers;

public class Util {

    public static boolean isValidNumber(String userInput) {
        try {
            Long.parseLong(userInput);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
                } else if (arr.length == 3) {
                    if (checkProperty(arr[2])) {
                        return Request.THIRD_NUMBER;
                    } else {
                        return Request.INVALID_THIRD_NUMBER;
                    }
                } else {
                    if (checkProperty(arr[2]) && checkProperty(arr[3])) {
                        if (!checkMutuallyExclusive(userInput)) {
                            return Request.FOURTH_NUMBER;
                        } else {
                            return Request.MUTUALLY_EXCLUSIVE;
                        }
                    } else {
                        return Request.INVALID_FOURTH_NUMBER;
                    }
                }
            } else {
                return Request.INVALID_FIRST_NUMBER;
            }
        }
    }

    public static boolean checkProperty(String userInput) {
        String[] property = {"EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY"};

        for (String s : property) {
            if (userInput.equalsIgnoreCase(s)) {
                return true;
            }
        }

        return false;
    }

    // Displays properties in a row.
    public static void print(long value) {
        System.out.println(new Number(value).printPropertiesColumn());
    }

    // Displays properties in a string multiple times with value change.
    public static void print(long value, int many) {
        for (long i = 0; i < many; i++) {
            System.out.println(new Number(value++).printPropertiesRow());
        }
    }

    // Displays a specific property in a string multiple times with a value change.
    public static void print(long value, int many, String property) {
        String print;
        while (many > 0) {
            print = new Number(value++).printPropertiesRow();
            if (print.contains(property.toLowerCase())) {
                System.out.println(print);
                many--;
            }
        }
    }

    // Displays multiple specific properties on a row multiple times with value changes.
    public static void print(long value, int many, String property1, String property2) {
        String print;
        while (many > 0) {
            print = new Number(value++).printPropertiesRow();
            if (print.contains(property1.toLowerCase()) && print.contains(property2.toLowerCase())) {
                System.out.println(print);
                many--;
            }
        }
    }

    public static boolean checkMutuallyExclusive(String userInput) {
        return userInput.contains("EVEN") && userInput.contains("ODD") ||
                userInput.contains("DUCK") && userInput.contains("SPY") ||
                userInput.contains("SQUARE") && userInput.contains("SUNNY");
    }
}
