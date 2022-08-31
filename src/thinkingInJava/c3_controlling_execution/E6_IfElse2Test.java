package thinkingInJava.c3_controlling_execution;

import static thinkingInJava.util.Print.*;

public class E6_IfElse2Test {
    static int test(int testval, int begin, int end) {
        for(int i = begin; i <=end; i++) {
            if(testval == i) {
                printnb("true ");
                return 1;
            }
        }
        printnb("false ");
        return 0;
    }

    public static void main(String[] args) {
        print(test(15, 10, 20));
        print(test(10, 10, 20));
        print(test(20, 10, 20));
        print(test(9, 10, 20));
        print(test(21, 10, 20));
    }
}

//    static boolean test(int testval, int begin, int end) {
//        if(testval >= begin && testval <= end)
//            return true;
//        else
//            return false;
//    }