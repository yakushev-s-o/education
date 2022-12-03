package offtop;

import java.math.BigInteger;

public class FibonacciBigInteger {
    public static BigInteger getFibonacciNumber(int n) {
        if (n <= 0) {
            return BigInteger.ZERO;
        }
        BigInteger prev = BigInteger.ZERO;
        BigInteger curr = BigInteger.ONE;
        for (int i = 1; i < n; ++i) {
            BigInteger next = prev.add(curr);
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static void printFibonacciBigInteger(int max) {
        for (int i = 0; i <= max; ++i) {
            System.out.printf("fib (%d) = %d\n", i, getFibonacciNumber(i));
        }
    }

    public static void main(String[] args) {
        printFibonacciBigInteger(15);
    }
}
