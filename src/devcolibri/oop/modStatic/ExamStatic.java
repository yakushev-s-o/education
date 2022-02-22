package devcolibri.oop.modStatic;

public class ExamStatic {

    public static int a = 7;
    static int b = 1; // access modifier package

    public static String print() {
        return "Hello static!";
    }

    public static int iteration() {
        return b++;
    }

}
