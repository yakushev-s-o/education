package hyperskill.rockpaperscissors;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String users = scanner.nextLine();
        String computer;

        if (users.equals("paper")) {
            computer = "scissors";
        } else if (users.equals("scissors")) {
            computer = "rock";
        } else {
            computer = "paper";
        }

        System.out.println("Sorry, but the computer chose " + computer);
    }
}
