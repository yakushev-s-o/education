package thinkingInJava.c4_controlling_execution;

public class E1_OneHundred {
    public static void whileCycle(){
        int i = 0;
        while (i <= 100) {
            System.out.print(i + " ");
            i++;
        }
        System.out.println();
    }

    public static void doWhileCycle(){
        int i = 0;
        do {
            System.out.print(i + " ");
            i++;
        } while (i <= 100);
        System.out.println();
    }

    public static void forCycle(){
        for (int i = 0; i <= 100; i++) {
            System.out.print(i + " ");
        }
    }

    public static void main(String[] args) {
        whileCycle();
        doWhileCycle();
        forCycle();
    }
}
