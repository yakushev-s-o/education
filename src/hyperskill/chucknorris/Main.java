package hyperskill.chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input string:");
        char[] arr = sc.nextLine().toCharArray();

        System.out.println("The result:");
        for (char c : arr) {
            String binary = Integer.toBinaryString(c);

            int count = 0;
            for (int i = 1; i < binary.length(); i++) {
                if (binary.charAt(i) == binary.charAt(i - 1)) {
                    count++;
                    if (i != binary.length() - 1) {
                        continue;
                    }
                }
                System.out.print(binary.charAt(i - 1) == '1' ? "0 " : "00 ");
                for (int j = 0; j <= count; j++) {
                    System.out.print("0");
                }
                if (i != binary.length() - 1) {
                    System.out.print(" ");
                } else {
                    System.out.print("");
                }
                count = 0;
            }
        }
    }
}
