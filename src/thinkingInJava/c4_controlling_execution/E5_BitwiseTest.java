package thinkingInJava.c4_controlling_execution;

public class E5_BitwiseTest {
    private static void binaryPrint (int q) {
        if (q == 0) {
            System.out.print("0");
        } else {
            int n = Integer.numberOfLeadingZeros(q);
            q <<= n;
            for(int p = 0; p < Integer.SIZE - n; p++) {
                System.out.print((Integer.numberOfLeadingZeros(q) == 0) ? 1 : 0);
                q <<= 1;
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int low = 0xAA;
        int msb = 0xDD; // most significant bit

        System.out.print("low - ");
        binaryPrint(low);
        System.out.print("msb - ");
        binaryPrint(msb);
        System.out.print("low&msb - ");
        binaryPrint(low&msb);
        System.out.print("low|msb - ");
        binaryPrint(low|msb);
        System.out.print("~low - ");
        binaryPrint(~low);
    }
}
