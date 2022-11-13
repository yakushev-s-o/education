package thinkingInJava.c5_initialization_and_cleanup;

public class E15_NoStaticInit {
    private String s;
    {
        s = "String";
    }

    E15_NoStaticInit(){
        System.out.println("First");
    }

    public static void main(String[] args) {
        System.out.println(new E15_NoStaticInit().s);
    }
}
