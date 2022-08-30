package thinkingInJava.control;

import static thinkingInJava.util.Print.*;

public class E10_Vampire {
    static void vampTest(int min, int max) {
        int count = 0;
        for (int i = min; i <= max; i++) {
            count++;
            int a, b, c, d;
            a = i / 1000;
            b = (i / 100) % 10;
            c = (i / 10) % 10;
            d = i % 10;
            String res = count + ": " + i + " =  " + a + "" + b + " * " + c + "" + d;

            if (i == (a*10 + b) * (c*10 + d)) { print(res); }
            if (i == (a*10 + b) * (d*10 + c)) { print(res); }
            if (i == (a*10 + c) * (b*10 + d)) { print(res); }
            if (i == (a*10 + c) * (d*10 + b)) { print(res); }
            if (i == (a*10 + d) * (c*10 + b)) { print(res); }
            if (i == (a*10 + d) * (b*10 + c)) { print(res); }

            if (i == (b*10 + a) * (c*10 + d)) { print(res); }
            if (i == (b*10 + a) * (d*10 + c)) { print(res); }
            if (i == (b*10 + c) * (d*10 + a)) { print(res); }
            if (i == (b*10 + d) * (c*10 + a)) { print(res); }

            if (i == (c*10 + a) * (d*10 + b)) { print(res); }
            if (i == (c*10 + b) * (d*10 + a)) { print(res); }
        }
    }

    public static void main(String[] args) {
        vampTest(1000, 9999);
    }
}

//        261: 1260 =  12 * 60
//        396: 1395 =  13 * 95
//        436: 1435 =  14 * 35
//        531: 1530 =  15 * 30
//        828: 1827 =  18 * 27
//        1188: 2187 =  21 * 87
//        5881: 6880 =  68 * 80
//        5881: 6880 =  68 * 80
