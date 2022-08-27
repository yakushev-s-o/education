package thinkingInJava.control;

import java.util.Random;
import static thinkingInJava.util.Print.print;

public class E2_Comparison {
    public static void main(String[] args) {
        Random rnd = new Random();
        int a, b = rnd.nextInt(25);
        for (int i = 1; i < 25; i++) {
            a = rnd.nextInt(25);
            if(a < b) {
                print(i + ": " + a + " < " + b + " (a < b)");
            } else if(a > b) {
                print(i + ": " + a + " > " + b + " (a > b)");
            } else {
                print(i + ": " + a + " = " + b + " (a = b)");
            }
            b = a;
        }
    }
}
