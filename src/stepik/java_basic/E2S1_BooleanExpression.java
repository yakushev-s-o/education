package stepik.java_basic;

public class E2S1_BooleanExpression {
    /**
     * Checks if <code>a, b, c, d</code> has two true arguments.
     *
     * @param a,b,c,d any boolean
     * @return <code>true</code> when any two arguments <code>a,b,c,d</code> are true
     */
    public static boolean booleanExpression(boolean a, boolean b, boolean c, boolean d) {
        return ((a != b) & (c != d)) | ((a != c) & (b != d));
    }
}
