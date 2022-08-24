package offtop;

import static thinkingInJava.util.Print.*;

public class Exponentiation {
    public static double Power(double base, int exponent) {
        double d = 1;
        boolean flag = true;
        if(exponent != 0){
            if(exponent < 0){
                exponent = Math.abs(exponent);
                flag = false;
            }
            while(exponent > 0){
                if(exponent % 2 == 1){
                    d *= base;
                }
                base *= base;
                exponent >>= 1;
            }
        }
        if(flag) {
            return d;
        }else {
            return 1/d;
        }
    }

    public static int powRecursion(int value, int powValue) {
        if (powValue == 1) {
            return value;
        } else {
            return value * powRecursion(value, powValue - 1);
        }
    }

    public static double OldApproximatePower(double b, double e) {
        long i = Double.doubleToLongBits(b);
        i = (long)(4606853616395542500L + e * (i - 4606853616395542500L));
        return Double.longBitsToDouble(i);
    }

    public static double BinaryPower(double b, long e) {
        double v = 1d;
        while(e > 0) {
            if((e & 1) != 0) {
                v *= b;
            }
            b *= b;
            e >>= 1;
        }
        return v;
    }

    public static void main(String[] args) {
        int num = 5;
        int pow = 2;

        long nTime = System.nanoTime(); // Start timer
        print("Math.pow -> " + Math.pow(num, pow));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime)/1_000_000.0); // Stop timer

        long nTime1 = System.nanoTime(); // Start timer
        print("Arithmetic -> " + num * num);
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime1)/1_000_000.0); // Stop timer

        long nTime2 = System.nanoTime(); // Start timer
        print("Power() -> " + Power(num, pow));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime2)/1_000_000.0); // Stop timer

        long nTime3 = System.nanoTime(); // Start timer
        print("powRecursion() -> " + powRecursion(num, pow));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime3)/1_000_000.0); // Stop timer

        long nTime4 = System.nanoTime(); // Start timer
        print("OldApproximatePower() -> " + OldApproximatePower(num, pow));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime4)/1_000_000.0); // Stop timer

        long nTime5 = System.nanoTime(); // Start timer
        print("BinaryPower() -> " + BinaryPower(num, pow));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime5)/1_000_000.0); // Stop timer

    }
}

/*
Math.pow -> 25.0
Time -> 2,088 ms
Arithmetic -> 25
Time -> 0,029 ms
Power() -> 25.0
Time -> 0,032 ms
powRecursion() -> 25
Time -> 0,026 ms
OldApproximatePower() -> 25.1681408000004
Time -> 0,039 ms
BinaryPower() -> 25.0
Time -> 0,030 ms
 */