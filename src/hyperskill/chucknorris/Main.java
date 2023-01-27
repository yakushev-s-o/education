package hyperskill.chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

//        System.out.println("Input string:");
        System.out.println("Input encoded string:");
        String str = sc.nextLine();

        System.out.println("The result:");
//        encrypted(arr);
        decrypted(str);
    }

    private static void decrypted(String str) {
        StringBuilder strDecrypted = new StringBuilder();
        String[] chars = str.split(" ");
        String first;
        int second;

        for (int i = 0; i < chars.length; i++) {
            first = chars[i++].equals("0") ? "1" : "0";
            second = chars[i].length();

            while (second > 0) {
                strDecrypted.append(first);
                second--;
            }
        }

        StringBuilder strToChar = new StringBuilder();
        for (int i = 0; i < strDecrypted.length() / 7; i++) {
            strToChar.append((char) (Integer.parseInt(strDecrypted.substring((7 * i), (i + 1) * 7),2)));
        }
        System.out.println(strToChar);
    }

//    private static void encrypted(String arr) {
//        StringBuilder binary = new StringBuilder();
//
//        for (char c : arr.toCharArray()) {
//            binary.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(c))));
//        }
//
//        int i = 0;
//        char ch;
//
//        while (i < binary.length()) {
//            ch = binary.charAt(i);
//
//            System.out.print(binary.charAt(i) == '1' ? "0 " : "00 ");
//
//            while (binary.charAt(i) == ch) {
//                System.out.print("0");
//                i++;
//                if (i == binary.length()) break;
//            }
//
//            if (i < binary.length()) {
//                System.out.print(" ");
//            }
//        }
//    }
}