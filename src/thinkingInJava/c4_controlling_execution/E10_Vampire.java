package thinkingInJava.c4_controlling_execution;

import static thinkingInJava.util.Print.*;

public class E10_Vampire {
    final static int START = 1000, END = 9999;
    public static int count = 0;

    public static void main(String[] args) {
        for (int i = START; i <= END; i++) {
            int a, b, c, d;
            a = i / 1000;
            b = (i / 100) % 10;
            c = (i / 10) % 10;
            d = i % 10;

            if (b + c == 0 || b + d == 0 || c + d == 0) continue;

            searchAndPrint(i, a, b, c, d);
            searchAndPrint(i, a, b, d, c);
            searchAndPrint(i, a, c, b, d);
            searchAndPrint(i, a, c, d, b);
            searchAndPrint(i, a, d, b, c);
            searchAndPrint(i, a, d, c, b);
            searchAndPrint(i, b, a, c, d);
            searchAndPrint(i, b, a, d, c);
            searchAndPrint(i, b, c, d, a);
            searchAndPrint(i, b, d, c, a);
            searchAndPrint(i, c, a, d, b);
            searchAndPrint(i, c, b, d, a);
        }
    }

    static void searchAndPrint(int i, int a, int b, int c, int d) {
        count++;
        if ((a * 10 + b) * (c * 10 + d)  == i) {
            print(i + " = " + (a * 10 + b) + " * " + (c * 10 + d) + "\t" + "(" + count + ")");
        }
    }
}

//        1260 = 21 * 60	(2875)
//        1395 = 15 * 93	(4482)
//        1435 = 41 * 35	(4951)
//        1530 = 51 * 30	(6079)
//        1827 = 87 * 21	(9610)
//        2187 = 27 * 81	(13674)
//        6880 = 86 * 80	(68563)
//        6880 = 80 * 86	(68566)
