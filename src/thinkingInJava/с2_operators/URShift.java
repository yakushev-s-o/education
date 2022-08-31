package thinkingInJava.с2_operators;

public class URShift {
    public static void main(String[] args) {
        int i = 0b101;
        int b = -5;
        System.out.println(
                Integer.toBinaryString(i) + "\n" +
                Integer.toBinaryString(b) + "\n" +
                Integer.toBinaryString(i << 1) + "\n" +
                Integer.toBinaryString(i >> 1) + "\n" +
                Integer.toBinaryString(i >>> 1) + "\n" +
                Integer.toBinaryString(b << 1) + "\n" +
                Integer.toBinaryString(b >> 1) + "\n" +
                Integer.toBinaryString(b >>> 1));
    }
}
