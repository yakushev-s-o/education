package thinkingInJava.c4_initialization_and_cleanup;

public class E16_StringArray {
    public static void main(String[] args) {
        String[] arr = new String[]{"first", "second", "third", "fourth"};
        for(String s: arr) {
            System.out.println(s);
        }
    }
}
