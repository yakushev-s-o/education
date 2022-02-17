package devcolibri.syntax;

public class Methods {
    public static void main(String[] args) {

        System.out.println(returnText(":"));
        numFibonacci();

    }

    static String returnText(String s) {

        return "Fibonacci numbers" + s;

    }

    static void numFibonacci() {

        /*
        F = {0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...}
        F0 = 0, F1 = 1, Fn = Fn - 1 + Fn - 2;
        n ≥ 0, n ∈ Z
         */

        int f0 = 0;
        int f1 = 1;
        int fn; // 10
        System.out.print(f0 + " " + f1 + " ");
        for (int i = 2; i < 10; i++) {
            fn = f0 + f1;
            System.out.print(fn + " ");
            f0 = f1;
            f1 = fn;
        }

    }
}
