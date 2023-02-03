package hyperskill.chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String str = sc.nextLine();

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
                } else {
                    System.out.println("Encoded string is not valid.");
                }
            } else if ("exit".equals(str)) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("There is no '" + str + "' operation");
            }
        }
    }

    private static void decode(String str) {
        StringBuilder strToChar = new StringBuilder();
        StringBuilder binary = strToBinary(str);

        // Convert the 7-bit binary substrings to a characters
        for (int i = 0; i < binary.length() / 7; i++) {
            strToChar.append((char) (Integer.parseInt(binary.substring((7 * i), (i + 1) * 7), 2)));
        }

        System.out.println(strToChar);
    }

    private static void encode(String str) {
        StringBuilder binary = new StringBuilder();

        // Convert characters to 7-bit binary representation
        for (char c : str.toCharArray()) {
            binary.append(String.format("%07d", Integer.parseInt(Integer.toBinaryString(c))));
        }

        int i = 0;
        char ch;

        // Encode binary string to 0 and 00
        while (i < binary.length()) {
            ch = binary.charAt(i);
            System.out.print(binary.charAt(i) == '1' ? "0 " : "00 ");

            while (binary.charAt(i) == ch) {
                System.out.print("0");
                i++;
                if (i == binary.length()) {
                    break;
                }
            }

            if (i < binary.length()) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static StringBuilder strToBinary(String str) {
        StringBuilder binary = new StringBuilder();
        String[] chars = str.split(" ");
        String first;
        int second;

        // Converting a character string to a binary representation
        for (int i = 0; i < chars.length; i++) {
            first = chars[i++].equals("0") ? "1" : "0";
            second = chars[i].length();

            while (second > 0) {
                binary.append(first);
                second--;
            }
        }

        return binary;
    }

    private static boolean isValid(String str) {
        String[] s = str.split(" ");

        // The number of blocks is odd
        if (s.length % 2 != 0) {
            return false;
        }

        // The length of the decoded binary string is not a multiple of 7
        if (strToBinary(str).length() % 7 != 0) {
            return false;
        }

        // The encoded message includes characters other than 0 or spaces
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != ' ') {
                return false;
            }
        }

        // The first block of each sequence is not 0 or 00
        for (int i = 0; i < s.length; i += 2) {
            if (s[i].length() > 2) {
                return false;
            }
        }

        return true;
    }
}