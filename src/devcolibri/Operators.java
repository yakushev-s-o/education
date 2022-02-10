package devcolibri;

public class Operators {

    static int first = 5;
    static int second = 9;
    static int third = 10;
    static boolean bool = false;

    public static void main(String[] args) {

        /*
        Logical Operators - && / || / !
        Comparison Operators - == / != / > / < / >= / <=
        Assignment Operators - = / += / -= / *= / /= / %= / &= / |= / ^= / >>= / <<=
        Arithmetic Operators - + / - / * / / / % / ++ / --
         */

        if(!(first == second) || bool) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }

        if(second % first == 0) {
            System.out.println("true");
        } else if(third % first == 0) {
            System.out.println("false");
        } else {
            System.out.println("err");
        }

    }
}
