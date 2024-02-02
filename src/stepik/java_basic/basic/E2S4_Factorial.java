package stepik.java_basic.basic;

import java.math.BigInteger;

public class E2S4_Factorial {
    /**
     * Calculates factorial of given <code>value</code>.
     *
     * @param value positive number
     * @return factorial of <code>value</code>
     */
    public static BigInteger factorial(int value) {
        BigInteger bigInteger = BigInteger.ONE;

        for (int i = 1; i <= value; i++) {
            bigInteger = bigInteger.multiply(BigInteger.valueOf(i));
        }

        return bigInteger;
    }
}
