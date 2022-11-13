package thinkingInJava.c4_controlling_execution;

import static thinkingInJava.util.Print.*;

public class E8_SwitchTest {
    static void testBreak() {
        for (int i = 0; i < 5; i++) {
            switch(i) {
                case 0: printnb("0 ");
                    break;
                case 1: printnb("1 ");
                    break;
                case 2: printnb("2 ");
                    break;
                case 3: printnb("3 ");
                    break;
                case 4: printnb("4 ");
                    break;
                default: printnb("END");
            }
        }
    }

    static void testWithoutBreak(){
        for (int i = 0; i < 5; i++) {
            switch(i) {
                case 0: printnb("0 ");
                case 1: printnb("1 ");
                case 2: printnb("2 ");
                case 3: printnb("3 ");
                case 4: printnb("4 ");
                default: printnb("END ");
            }
        }
    }

    public static void main(String[] args) {
        testBreak();
        print();
        testWithoutBreak();
    }
}
