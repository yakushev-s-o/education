package thinkingInJava.c4_initialization_and_cleanup;

public class E08_ThisClass {

    void first() {
        this.second();
        second();
    }

    void second() {
        System.out.println("second()");
    }

    public static void main(String[] args) {
        new E08_ThisClass().first();
    }
}
