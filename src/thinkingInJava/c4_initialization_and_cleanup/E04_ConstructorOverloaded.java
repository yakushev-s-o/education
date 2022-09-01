package thinkingInJava.c4_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

public class E04_ConstructorOverloaded {

    E04_ConstructorOverloaded(String s) {
        printf("String (s = \"%s\")", s);
    }

    E04_ConstructorOverloaded() {
        print("Default");
    }

    public static void main(String[] args) {
        new E04_ConstructorOverloaded();
        new E04_ConstructorOverloaded("Overloaded");
    }
}
