package thinkingInJava.c4_initialization_and_cleanup;

public class E14_StaticDef {

    static public void print(){
        System.out.println("f = " + f + "\n" + "s = " + s);
    }

    private static String f = "first";
    private static String s;
    static {
        s = "second";
    }

    public static void main(String[] args) {
        print();
    }
}
