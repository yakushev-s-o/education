package thinkingInJava.c4_controlling_execution;

public class E7_OneHundredTest {
    static int whileCycle(){
        int i = 0;
        int stop = 99;
        while (i <= 100) {
            System.out.print(i + " ");
            i++;
            if(i == stop) {
                break;
            }
        }
        return i;
    }

    public static void main(String[] args) {
        System.out.println(whileCycle());
    }
}
