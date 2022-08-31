package thinkingInJava.с2_operators;

public class E10_BitwiseOperators {

    private static void printBinary(String msg, int num) {
        System.out.println(msg + " - " + Integer.toBinaryString(num));
    }

    public static void main(String[] args) {
        int low = 0xAA;
        int msb = 0xDD; // most significant bit

        printBinary("low", low);
        printBinary("msb", msb);
        printBinary("low&msb", low&msb);
        printBinary("low|msb", low|msb);
        printBinary("low^msb", low^msb);
        printBinary("~low", ~low);
    }
}
