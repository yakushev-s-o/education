package hyperskill.bullscows;

import java.util.Scanner;

public class Main {
    final static int SECRET_CODE = 9305;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        grader(sc.nextInt());
    }

    public static void grader(int n) {
        int bulls = 0;
        int cows = 0;

        int[] input = splitToArray(n);
        int[] secret = splitToArray(SECRET_CODE);

        for (int i = 0; i < input.length; i++) {
            for (int k : secret) {
                if (input[i] == secret[i]) {
                    bulls++;
                    break;
                } else if (input[i] == k) {
                    cows++;
                }
            }
        }

        if (bulls > 0 && cows > 0) {
            System.out.printf("Grade: %d bull(s) and %d cow(s). The secret code is %d.", bulls, cows, SECRET_CODE);
        } else if (bulls > 0) {
            System.out.printf("Grade: %d bull(s). The secret code is %d.", bulls, SECRET_CODE);
        } else if (cows > 0) {
            System.out.printf("Grade: %d cow(s). The secret code is %d.", cows, SECRET_CODE);
        } else {
            System.out.printf("Grade: None. The secret code is %d.", SECRET_CODE);
        }
    }

    public static int[] splitToArray(int num) {
        int count = String.valueOf(num).length();
        int[] res = new int[count];
        for (int i = count - 1; i >= 0; i--) {
            res[i] = num % 10;
            num /= 10;
        }
        return res;
    }
}
