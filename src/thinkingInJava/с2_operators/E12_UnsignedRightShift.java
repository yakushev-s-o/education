package thinkingInJava.с2_operators;

import static thinkingInJava.util.Print.print;

public class E12_UnsignedRightShift {
    public static void main(String[] args) {
        int a = 0xffffffff;
        print(a);
        print(Integer.toBinaryString(a));
        a <<= 1;
        print(Integer.toBinaryString(a));

        for (int i = 0; i < 31; i++) {
            a >>>= 1;
            print(Integer.toBinaryString(a));
        }
    }
}
