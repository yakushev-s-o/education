package hyperskill.rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static int money;
    private static String userName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        File file = new File("rating.txt");

        System.out.print("Enter your name: ");

        userName = scanner.nextLine();
        money = readFile(file);

        System.out.println("Hello, " + userName);

        while (true) {
            String users = scanner.nextLine();

            if ("!rating".equals(users)) {
                System.out.println("Your rating: " + money);
            } else if (users.equals("scissors") || users.equals("rock") || users.equals("paper")) {
                String computer = switch (new Random().nextInt(3)) {
                    case 0 -> "scissors";
                    case 1 -> "rock";
                    default -> "paper";
                };
                checkWin(users, computer);
            } else if (users.equals("!exit")) {
                System.out.println("Bye!");
                break;
            } else {
                System.out.println("Invalid input");
            }
        }
    }

    private static void checkWin(String users, String computer) {
        if (users.equals(computer)) {
            System.out.printf("There is a draw (%s)\n", computer);
            money += 50;
        } else if (users.equals("paper") && computer.equals("scissors") ||
                users.equals("scissors") && computer.equals("rock") ||
                users.equals("rock") && computer.equals("paper")) {
            System.out.printf("Sorry, but the computer chose %s\n", computer);
        } else {
            System.out.printf("Well done. The computer chose %s and failed\n", computer);
            money += 100;
        }
    }

    private static int readFile(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String str = scanner.nextLine();
                if (str.contains(userName)) {
                    return Integer.parseInt(str.split(" ")[1]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No file found: " + file);
        }
        return 0;
    }
}
