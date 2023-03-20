package hyperskill.bullscows;

import java.util.Scanner;

public class Bullscows {
    private final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz";
    private String secret;
    private int bulls;
    private int cows;


    public void run() {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Input the length of the secret code:");

            String inputLength = sc.next();
            try {
                int length = Integer.parseInt(inputLength);

                if (length > 36 || length == 0) {
                    System.out.printf("Error: can't generate a secret number " +
                            "with a length of %d because there aren't enough unique digits.\n", length);
                } else {
                    System.out.println("Input the number of possible symbols in the code:");

                    String inputRange = sc.next();
                    try {
                        int range = Integer.parseInt(inputRange);

                        if (range > 36) {
                            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                        } else {
                            if (length > range) {
                                System.out.printf("Error: it's not possible to generate a code " +
                                        "with a length of %d with %d unique symbols.", length, range);
                            } else {
                                secret = randomSecretCode(length, range);

                                System.out.printf("The secret is prepared: %s (%s).\n", "*".repeat(length), getRangeChar(range));

                                System.out.println("Okay, let's start a game!");

                                int turn = 1;
                                while (bulls != secret.length()) {
                                    bulls = 0;
                                    cows = 0;
                                    System.out.printf("Turn %d:\n", turn++);

                                    String input = sc.next();

                                    if (input.length() == length) {
                                        grader(input);
                                        printGrader();
                                    }
                                }

                                System.out.println("Congratulations! You guessed the secret code.");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.printf("Error: \"%s\" isn't a valid number.", inputRange);
                    }
                }
            } catch (NumberFormatException e) {
                System.out.printf("Error: \"%s\" isn't a valid number.", inputLength);
            }
        }
    }

    public void grader(String s) {
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < secret.length(); j++) {
                if (s.charAt(i) == secret.charAt(i)) {
                    bulls++;
                    break;
                } else if (s.charAt(i) == secret.charAt(j)) {
                    cows++;
                }
            }
        }
    }

    public void printGrader() {
        String bull = bulls == 1 ? "bull" : "bulls";
        String cow = cows == 1 ? "cow" : "cows";

        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d %s and %d %s\n", bulls, bull, cows, cow);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d %s\n", bulls, bull);
        } else if (cows > 0) {
            System.out.printf("Grade: %d %s\n", cows, cow);
        } else {
            System.out.println("Grade: None");
        }
    }

    public String randomSecretCode(int length, int range) {
        StringBuilder secretCode = new StringBuilder();

        while (secretCode.length() < length) {
            int random = (int) (Math.random() * range);

            if (!secretCode.toString().contains(String.valueOf(ALPHABET.charAt(random)))) {
                secretCode.append(ALPHABET.charAt(random));
            }
        }
        return secretCode.toString();
    }

    private String getRangeChar(int range) {
        if (range <= 10) {
            return "0-" + ALPHABET.charAt(range - 1);
        } else {
            return "0-9, a-" + ALPHABET.charAt(range - 1);
        }
    }
}
