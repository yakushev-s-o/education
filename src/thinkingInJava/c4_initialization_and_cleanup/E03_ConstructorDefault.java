package thinkingInJava.c4_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

public class E03_ConstructorDefault {

    E03_ConstructorDefault() {
        print("Default");
    }

    public static void main(String[] args) {
        new E03_ConstructorDefault();
    }
}
