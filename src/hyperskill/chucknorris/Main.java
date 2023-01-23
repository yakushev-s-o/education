package hyperskill.chucknorris;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input string:");
        char[] arr = sc.nextLine().toCharArray();

        System.out.println("The result:");
        for (char c : arr) {
            System.out.printf("%c = %07d\n", c, Integer.parseInt(Integer.toBinaryString(c)));
        }
    }
}
