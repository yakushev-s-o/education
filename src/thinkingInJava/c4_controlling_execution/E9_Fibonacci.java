package thinkingInJava.c4_controlling_execution;

import static thinkingInJava.util.Print.*;

public class E9_Fibonacci {
    static void fib(int n){
        int f0 = 0;
        int f1 = 1;
        int fn;

        if(n <= 0){
            print(0);
        } else if(n < 2) {
            print(1);
        } else {
            printnb(1);
            for (int i = 1; i < n; i++) {
                fn = f0 + f1;
                printnb(" " + fn);
                f0 = f1;
                f1 = fn;
            }
            print();
        }
    }

    public static void main(String[] args) {
        fib(0);
        fib(1);
        fib(2);
        fib(5);
    }
}
