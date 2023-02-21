package hyperskill.numbers;

import java.util.Locale;

public class Number {
    private final long value;
    private final boolean odd;
    private final boolean buzz;
    private final boolean duck;
    private final boolean palindromic;
    private final boolean gapful;
    private final boolean spy;

    public Number(long value) {
        this.value = value;
        this.odd = isOdd();
        this.buzz = isBuzzNumber();
        this.duck = isDuck();
        this.palindromic = isPalindromic();
        this.gapful = isGapful();
        this.spy = isSpy();
    }

    public static boolean isNatural(long value) {
        return value > 0;
    }

    private boolean isOdd() {
        return (value & 1) == 1;
    }

    // It is a numbers that are either divisible by 7 or end with 7.
    private boolean isBuzzNumber() {
        return value % 7 == 0 || value % 10 == 7;
    }

    // It is a number is a positive number that contains zeros.
    private boolean isDuck() {
        long numTrim = value;

        for (long i = numTrim; i >= 10; i /= 10) {
            if (numTrim % 10 == 0) {
                return true;
            }
            numTrim /= 10;
        }

        return false;
    }

    // It is a number is symmetrical, it stays the same regardless of whether we read it from left or right.
    private boolean isPalindromic() {
        long numSrc = value;
        long reversed = 0;

        while (numSrc != 0) {
            reversed = reversed * 10 + (numSrc % 10);
            numSrc /= 10;
        }

        return value == reversed;
    }

    // It is a number that contains at least 3 digits and is divisible by the concatenation of its first and last digit without a remainder.
    private boolean isGapful() {
        long n = value;
        int count = 1;

        while (n > 9) {
            n /= 10;
            count++;
        }

        return count > 2 && value % (n * 10 + value % 10) == 0;
    }

    // This is a number where the sum of all digits is equal to the product of all digits.
    private boolean isSpy() {
        long num = value;
        long sum = 0;
        long product = 1;

        while(num != 0) {
            long digit = num % 10;
            sum += digit;
            product *= digit;
            num /= 10;
        }

        return sum == product;
    }

    public String printPropertiesRow() {
        String isOdd = odd ? "odd" : "even";
        String isBuzz = buzz ? ", buzz" : "";
        String isDuck = duck ? ", duck" : "";
        String isPalindromic = palindromic ? ", palindromic" : "";
        String isGapful = gapful ? ", gapful" : "";
        String isSpy = spy ? ", spy" : "";

        return String.format(Locale.US, "%,d is %s%s%s%s%s%s", value, isOdd, isBuzz, isDuck, isPalindromic, isGapful, isSpy);
    }

    public String printPropertiesColumn() {
       return String.format(Locale.US, """
                        Properties of %,d
                                even: %b
                                 odd: %b
                                buzz: %b
                                duck: %b
                         palindromic: %b
                              gapful: %b
                                 spy: %b
                        """,
                value, !isOdd(), isOdd(),
                isBuzzNumber(), isDuck(),
                isPalindromic(), isGapful(), isSpy());
    }
}
