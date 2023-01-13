package hyperskill.chucknorris;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input string:");
        char[] arr = sc.nextLine().toCharArray();

        for (char c : arr) {
            System.out.print(c + " ");
        }
    }
}
