package thinkingInJava.c4_initialization_and_cleanup;

public class E11_Finalize {

    public void finalize(){
        System.out.println("Finalize!!!");
    }

    public static void main(String[] args) {
        new E11_Finalize();
        System.gc();
    }
}
