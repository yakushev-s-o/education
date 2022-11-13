package thinkingInJava.c5_initialization_and_cleanup;

public class E09_ThisConst {

    String s = "constructor";

    E09_ThisConst(String s){
         s = this.s;
        System.out.println("First " + s);
    }

    E09_ThisConst(){
        this("Test");
        System.out.println("Second constructor");
    }

    public static void main(String[] args) {
        E09_ThisConst c = new E09_ThisConst();
    }
}
