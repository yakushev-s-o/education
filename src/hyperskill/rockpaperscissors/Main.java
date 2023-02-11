package hyperskill.rockpaperscissors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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

        String[] options = gameOptions(scanner.nextLine());

        System.out.println("Okay, let's start");

        while (true) {
            String users = scanner.nextLine();

            int usersNum = -1;
            for (int i = 0; i < options.length; i++) {
                if (options[i].equals(users)) {
                    usersNum = i;
                }
            }

            if ("!rating".equals(users)) {
                System.out.println("Your rating: " + money);
            } else if (users.equals("!exit")) {
                System.out.println("Bye!");
                break;
            } else if (usersNum == -1) {
                System.out.println("Invalid input");
            } else {
                int computerNum = new Random().nextInt(options.length);
                checkWin(usersNum, computerNum, options);
            }
        }
    }

    private static void checkWin(int usersNum, int computerNum, String[] options) {
        int count = options.length / 2;
        String[] winArr = new String[count];
        int j = 0;
        for (int i = usersNum + 1; i < options.length; i++) {
            winArr[j++] = options[i];
            count--;
            if (count == 0) {
                break;
            }
        }
        for (int i = 0; i < count; i++) {
            winArr[j++] = options[i];
        }

        boolean win = true;
        for (String s : winArr) {
            if (s.equals(options[computerNum])) {
                win = false;
                break;
            }
        }

        if (usersNum == computerNum) {
            System.out.printf("There is a draw (%s)\n", options[computerNum]);
            money += 50;
        } else if (win) {
            System.out.printf("Well done. The computer chose %s and failed\n", options[computerNum]);
            money += 100;
        } else {
            System.out.printf("Sorry, but the computer chose %s\n", options[computerNum]);
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

    private static String[] gameOptions(String options) {
        if ("".equals(options)) {
            return new String[]{"scissors", "rock", "paper"};
        }
        return options.split(",");
    }
}
