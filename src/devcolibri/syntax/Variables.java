package devcolibri.syntax;

public class Variables {

    /*
    Primitive Data Types
     */

    static byte b = 127; // def. 0 / -128 -> 127; / 1 byte
    static short s = 32767; // def. 0 / -32768 -> 32767 / 2 byte
    static int i = 2147483647; // def. 0 / -2147483648 -> 2147483647 / 4 byte
    static long l = 9223372036854775807L; // def. 0L /  -9223372036854775808 до 9223372036854775807 / 8 byte
    static float f = 3.4E38f; // def. 0f / -3.4E+38 -> 3.4E+38 / 4 byte
    static double d = 1.7E308d; // def. 0d /  -1.7E+308 -> 1.7E+308 / 8 byte
    static char c = 'c'; // def. '\u0000' / '\u0000' -> '\uffff' / 'c' = 99 / 2 byte
    static boolean bt = true; // def. false / 1 byte ()
    static boolean bf = false; // def. false

    /*
    Reference Data Types
     */

    static String st = "text"; // def. null
    static String str = "123";

    /*
    Primitive Wrapper Class
     */

    static Byte sB = 1;
    static Short sS = 5;
    static Integer sI = 9;
    static Long sL = 150L;
    static Float sF = 5.9f;
    static Double sD = 15.7d;
    static Character sC = 'a';
    static Boolean sBT = true;
    static Boolean sBF = false;

    /*
    Constants
     */

    static final int CONST = 40;

    /*
    Type Casting: Explicit (Double > Long > Float > Int > Short > Byte) & Implicit
     */

    static int convInt = (int)3.99; // Explicit (Double to Int)
    static int convChar = 'c'; // Implicit (Char to Int)

    static Integer consDouble = sD.intValue(); // Explicit (sDouble to sInt)
    static Long consInt = sI.longValue(); // Implicit (sInt to sLong)

    static Integer textToInt = Integer.parseInt(str); // Explicit (String to Int)

    /*
    Math operations
     */

    static int sumIntByte = i + b - 2147483647; // sum int and byte
    static int multiCharInt = c * (i - 2147483645); // multi char and int
    static double mathSQRT = Math.sqrt(sI); // returns the square root of the argument

    public static void main(String[] args) {

        System.out.println("byte: " + b + "\n" +
                "short: " + s + "\n" +
                "int: " + i + "\n" +
                "long: " + l + "\n" +
                "float: " + f + "\n" +
                "double: " + d + "\n" +
                "char: " + c + "\n" +
                "boolean true: " + bt + "\n" +
                "boolean false: " + bf + "\n" +
                "String: " + st + "\n" +
                "CONST: " + CONST +  "\n" +
                "sByte: " + sB + "\n" +
                "sShort: " + sS + "\n" +
                "sInt: " + sI + "\n" +
                "sLong: " + sL + "\n" +
                "sFloat: " + sF + "\n" +
                "sDouble: " + sD + "\n" +
                "sChar: " + sC + "\n" +
                "sBoolean true: " + sBT + "\n" +
                "sBoolean false: " + sBF + "\n" +
                "Double to Int: " + convInt + "\n" +
                "Char to Int: " + convChar + "\n" +
                "sDouble to sInt: " + consDouble + "\n" +
                "sInt to sLong: " + consInt + "\n" +
                "String to Int: " + (textToInt + 2) + "\n" +
                "String + number: " + (st + 2) + "\n" +
                "Sum int and byte: " + sumIntByte + "\n" +
                "Multi char and int: " + multiCharInt + "\n" +
                "Square of the sI: " + mathSQRT);
    }
}
