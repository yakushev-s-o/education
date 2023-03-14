package hyperskill.bullscows;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bullscows bullscows = new Bullscows();
//        bullscows.grader(sc.nextLine());
//        bullscows.printGrader();
        bullscows.randomSecretCode(sc.nextInt());
    }
}
