package hyperskill.chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str;

        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            str = sc.nextLine();

            if ("encode".equals(str)) {
                System.out.println("Input string:");
                str = sc.nextLine();
                System.out.println("Encoded string:");
                encode(str);
            } else if ("decode".equals(str)) {
                System.out.println("Input encoded string:");
                str = sc.nextLine();
                if (isValid(str)) {
                    System.out.println("Decoded string:");
                    decode(str);
                }
            } else if ("exit".equals(str)) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("There is no '<input>' operation");
            }
        }
    }

    private static void encode(String str) {
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
            strToChar.append((char) (Integer.parseInt(strDecrypted.substring((7 * i), (i + 1) * 7), 2)));
        }
        System.out.println(strToChar);
    }

    private static void decode(String str) {
        StringBuilder binary = new StringBuilder();

        for (char c : str.toCharArray()) {
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

    public static boolean isValid(String string) {
        String[] str = string.split(" ");

        for (String s : str) {
            if (!"0".equals(s)) {
                System.out.println("Encoded string is not valid.");
                return false;
            }
        }

        if (str.length % 2 != 0) {
            return false;
        }

        for (int i = 0; i < str.length; i += 2) {
            if (str[i].length() < 3) {
                System.out.println("Encoded string is not valid.");
                return false;
            }
        }

        return true;
    }
}