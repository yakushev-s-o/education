package thinkingInJava.с2_operators;

import static thinkingInJava.util.Print.print;

public class E11_SignedRightShift {
    public static void main(String[] args) {
        int a = 0x10000000;
        print(Integer.toBinaryString(a));
        for (int i = 0; i < 28; i++) {
            a >>= 1;
            print(Integer.toBinaryString(a));
        }
    }
}
