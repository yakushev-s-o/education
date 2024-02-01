package stepik.java_basic;

public class E2S2_LeapYearCount {
    /**
     * Calculating the number of leap years from the beginning
     * of our era (the first year) to a given year inclusive.
     *
     * @param year any number
     * @return <code>year</code>
     */
    public static int leapYearCount(int year) {
        return (year / 4) - (year / 100) + (year / 400);
    }
}
