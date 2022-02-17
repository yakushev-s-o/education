package devcolibri.syntax;

public class Branching {

    static int i = 5;
    static boolean b = false;

    public static void main(String[] args) {

        /*
        Branch Operator (if / else if / else)
         */

        if(b) {
            System.out.println("branch if 1");
        } else if (i > 10) {
            System.out.println("branch if 2");
        } else {
            System.out.println("branch if 3");
        }

        /*
        Branch Operator (switch / case)
         */

        switch (i) {
            case 1:
                System.out.println("branch switch 1");
                break;
            case 3:
                System.out.println("branch switch 3");
                break;
            case 5:
                System.out.println("branch switch 5");
                break;
            default:
                System.out.println("branch switch default");
        }

    }
}
