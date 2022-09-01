package thinkingInJava.c4_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

class Dog {
    Dog() {
        print("Gaw");
    }

    Dog(int i) {
        printf("Woof %d\n", i);
    }

    Dog(String s) {
        printf("Woof %s\n", s);
    }

    Dog(boolean b) {
        printf("Woof %b\n", b);
    }
}

public class E05_ConstructorDog {
    public static void main(String[] args) {
        new Dog();
        new Dog(2);
        new Dog("rrr");
        new Dog(true);
    }
}
