package thinkingInJava.c5_initialization_and_cleanup;

public class E18_ArrayInit {

    E18_ArrayInit(String s){
        System.out.println(s);
    }

    public static void main(String[] args) {
        E18_ArrayInit[] arr = new E18_ArrayInit[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new E18_ArrayInit("String " + (i + 1));
        }
    }
}

