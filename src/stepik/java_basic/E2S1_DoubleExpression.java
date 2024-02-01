package stepik.java_basic;

public class E2S1_DoubleExpression {
    /**
     * Checks if a + b = c.
     *
     * @param a,b,c any number
     * @return <code>true</code> when a + b = c
     */
    public static boolean doubleExpression(double a, double b, double c) {
        return Math.abs((a + b) - c) < 1E-4;
    }
}
