package thinkingInJava.operators;

import static thinkingInJava.util.Print.print;

public class E13_BinaryChar {
    public static void main(String[] args) {
        char a = 'a';

        for (int i = 0; i < 26; i++) {
            print(a + " - " + Integer.toBinaryString(a++));
        }
    }
}
