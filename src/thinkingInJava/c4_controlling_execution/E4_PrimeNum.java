package thinkingInJava.c4_controlling_execution;

public class E4_PrimeNum {
    public static void main(String[] args) {
        for (int i = 1; i < 100; i++) {
            int num = 0;
            for (int j = 1; j < i; j++) {
                if((i % j) == 0) {
                    num++;
                }
            }
            if(num < 2) {
                System.out.print(i + " ");
            }
        }
    }
}
