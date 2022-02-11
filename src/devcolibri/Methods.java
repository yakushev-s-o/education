package devcolibri;

public class Methods {
    public static void main(String[] args) {

        numFibonacci();

    }

    static void numFibonacci() {

        int f0 = 0;
        int f1 = 1;
        int res;
        System.out.println(f0 + "\n" + f1);
        for (int i = 2; i < 10; ++i) {
            res = f0 + f1;
            System.out.println(res);
            f0 = f1;
            f1 = res;
        }

    }
}
