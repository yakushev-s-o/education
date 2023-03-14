package hyperskill.bullscows;

public class Bullscows {
    private final String secret;
    private int bulls;
    private int cows;

    public Bullscows() {
        secret = "9305";
        bulls = 0;
        cows = 0;
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
        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is %s.", bulls, cows, secret);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d bull(s). The secret code is %s.", bulls, secret);
        } else if (cows > 0) {
            System.out.printf("Grade: %d cow(s). The secret code is %s.", cows, secret);
        } else {
            System.out.printf("Grade: None. The secret code is %s.", secret);
        }
    }

    public void randomSecretCode(int size) {
        if (size < 10) {
            String secretCode;

            boolean check;
            do {
                long pseudoRandomNumber = System.nanoTime();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(pseudoRandomNumber).reverse();

                while (stringBuilder.charAt(0) == '0') {
                    stringBuilder.deleteCharAt(0);
                }

                secretCode = stringBuilder.substring(0, size);

                check = false;
                boolean[] digits = new boolean[10];
                for (int i = 0; i < secretCode.length(); i++) {
                    int digit = Character.getNumericValue(secretCode.charAt(i));
                    if (digits[digit]) {
                        check = true;
                    }
                    digits[digit] = true;
                }

            } while (check);

            System.out.printf("The random secret number is %s.", secretCode);
        } else {
            System.out.printf("Error: can't generate a secret number with a length of %d because there aren't enough unique digits.", size);
        }
    }
}
