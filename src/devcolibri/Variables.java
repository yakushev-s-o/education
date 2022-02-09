package devcolibri;

public class Variables {

    /*
    Примитивные типы данных
     */

    static byte b = 127; // -128 -> 127;
    static short s = 32767; // -32768 -> 32767
    static int i = 2147483647; // -2147483648 -> 2147483647
    static long l = 9223372036854775807L; // -9223372036854775808 до 9223372036854775807
    static float f = 3.4E38f; // -3.4E+38 -> 3.4E+38
    static double d = 1.7E308; // -1.7E+308 -> 1.7E+308
    static char c = 'c'; // c = 99
    static boolean bt = true;
    static boolean bf = false;

    public static void main(String[] args) {

        System.out.println("byte: " + b + "\n" +
                "short: " + s + "\n" +
                "int: " + i + "\n" +
                "long: " + l + "\n" +
                "float: " + f + "\n" +
                "double: " + d + "\n" +
                "char: " + c + "\n" +
                "boolean true: " + bt + "\n" +
                "boolean false: " + bf);
    }
}
