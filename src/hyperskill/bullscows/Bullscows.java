package hyperskill.bullscows;

import java.util.Scanner;

public class Bullscows {
    private String secret;
    private int bulls;
    private int cows;
    private final String ALPHABET = "1234567890abcdefghijklmnopqrstuvwxyz";

    public Bullscows() {
        bulls = 0;
        cows = 0;
    }

    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Input the length of the secret code:");

        int length;
        while (true) {
            length = sc.nextInt();

            if (length <= 36) {
                break;
            } else {
                System.out.printf("Error: can't generate a secret number " +
                        "with a length of %d because there aren't enough unique digits.\n", length);
            }
        }

        System.out.println("Input the number of possible symbols in the code:");
        int range = sc.nextInt();

        secret = randomSecretCode(length, range);

        String rangeChar;
        if (range < 10) {
            rangeChar = "0-" + ALPHABET.charAt(range - 1);
        } else {
            rangeChar = "0-9, a-" + ALPHABET.charAt(range - 1);
        }
        System.out.printf("The secret is prepared: %s (%s).\n", "*".repeat(length), rangeChar);

        System.out.println("Okay, let's start a game!");

        int turn = 1;
        while (bulls != secret.length()) {
            bulls = 0;
            cows = 0;
            System.out.printf("Turn %d:\n", turn++);
            grader(sc.next());
            printGrader();
        }

        System.out.println("Congratulations! You guessed the secret code.");
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
}
