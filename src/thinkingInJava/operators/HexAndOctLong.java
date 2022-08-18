package thinkingInJava.operators;

public class HexAndOctLong {
    public static void main(String[] args) {
        long hex = 0x2f;
        long oct = 057;

        System.out.println("bin " + Long.toBinaryString(hex) +
                " -> hex " + Long.toHexString(hex) +
                " -> dec " + hex);
        System.out.println("bin " + Long.toBinaryString(oct) +
                " -> oct " + Long.toOctalString(oct) +
                " -> dec " + oct);
    }
}
