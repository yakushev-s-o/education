package thinkingInJava.c4_controlling_execution;

import java.util.Random;
import static thinkingInJava.util.Print.print;

public class E3_InfinityComparison {
    public static void main(String[] args) {
        Random rnd = new Random();
        int a, b = rnd.nextInt(25), i = 1;
        for (;;) {
            a = rnd.nextInt(25);
            if (a < b) {
                print(i + ": " + a + " < " + b + " (a < b)");
            } else if (a > b) {
                print(i + ": " + a + " > " + b + " (a > b)");
            } else {
                print(i + ": " + a + " = " + b + " (a = b)");
            }
            b = a;
            i++;
        }
    }
}
