package devcolibri;

public class Recursion {

    public static void main(String[] args) {

        long time = System.currentTimeMillis(); // Code execution time #1

        System.out.println("Recursion Fibonacci numbers:");
        for (int i = 0; i < 10; i++) {
            System.out.print(recFibonacci(i) + " ");

        }

        System.out.println("\n" + (double) (System.currentTimeMillis() - time) + " ms"); // Code execution time #2

    }

    static int recFibonacci(int num) {

        if (num == 0) {
            return 0;
        } else if (num ==1) {
            return 1;
        } else {
            return (recFibonacci(num -1) + recFibonacci(num - 2));
        }

    }

}
