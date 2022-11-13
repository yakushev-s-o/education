package thinkingInJava.c5_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

class Dog2 {
    Dog2() {
        print("Gaw");
    }

    Dog2(int i) {
        printf("Woof %d\n", i);
    }

    Dog2(String s) {
        printf("Woof %s\n", s);
    }

    Dog2(boolean b) {
        printf("Woof %b\n", b);
    }

    Dog2(String s, int i) {
        printf("Woof %s %d\n", s, i);
    }

    Dog2(int i, String s) {
        printf("Woof %d %s\n", i, s);
    }
}

public class E06_ConstructorDog2 {
    public static void main(String[] args) {
        new Dog2();
        new Dog2(2);
        new Dog2("rrr");
        new Dog2(true);
        new Dog2("Rrr", 5);
        new Dog2(10, "RRR");
    }
}
