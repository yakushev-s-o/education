package thinkingInJava.c4_initialization_and_cleanup;

public class E19_VarArgs {

    public static void print(String... args) {
        for(String s : args){
            System.out.println(s);
        }
    }

    public static void main(String[] args) {
        print("First" , "Second");
        String[] strings = new String[] {"Third, Fourth"};
        print(strings);
    }
}
