package offtop;

public class Fibonacci {
    public static long getFibonacciNumber(int n) {
        if (n <= 0) {
            return 0;
        }
        long prev = 0;
        long curr = 1;
        for (int i = 1; i < n; ++i) {
            long next = prev + curr;
            prev = curr;
            curr = next;
        }
        return curr;
    }

    public static void printFibonacci(int max) {
        for (int i = 0; i <= max; ++i) {
            System.out.printf("fib (%d) = %d\n", i, getFibonacciNumber(i));
        }
    }

    public static void main(String[] args) {
        printFibonacci(15);
    }
}
