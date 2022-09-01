package thinkingInJava.c4_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

class TestClass {
    void message() {
        print("without constructor");
    }
}

public class E07_WithoutConstructor {
    public static void main(String[] args) {
        new TestClass().message();
    }
}
