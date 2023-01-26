package hyperskill.chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input string:");
        char[] arr = sc.nextLine().toCharArray();
        StringBuilder binary = new StringBuilder();

        System.out.println("The result:");
        for (char c : arr) {
            binary.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(c))));
        }

        int i = 0;
        char ch;

        while (i < binary.length()) {
            ch = binary.charAt(i);

            System.out.print(binary.charAt(i) == '1' ? "0 " : "00 ");

            while (binary.charAt(i) == ch) {
                System.out.print("0");
                i++;
                if (i == binary.length()) break;
            }

            if (i < binary.length()) {
                System.out.print(" ");
            }
        }
    }
}