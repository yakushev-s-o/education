package thinkingInJava.operators;

import static thinkingInJava.util.Print.*;

public class StringOperators {
    public static void main(String[] args) {
        int x = 0, y = 1, z = 2;
        String s = "x, y, z";

        print(s + x + y + z);
        print(x + " " + s);
        s += " (sum) = ";
        print(s + (x + y + z));
        print(Integer.toString(x) + y + z);
        print("" + x + y + z);
    }
}
