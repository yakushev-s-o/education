package hyperskill.battleship;

import java.util.Scanner;

public class Game {
    public void run() {
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        System.out.printf(Messages.PLAYER.toString(), "Player 1");
        player1.field.initField();
        player1.field.printField(false);
        player1.field.placeShips();
        changeTurn();

        System.out.printf(Messages.PLAYER.toString(), "Player 2");
        player2.field.initField();
        player2.field.printField(false);
        player2.field.placeShips();
        changeTurn();

        System.out.println(Messages.GAME_STARTS);
        System.out.println(Messages.TAKE_SHOT);

        while (true) {
            player2.field.printField(true);
            System.out.println("---------------------");
            player1.field.printField(false);
            System.out.printf(Messages.TURN.toString(), "Player 1");

            if (player2.field.takeShot()) {
                break;
            }

            changeTurn();

            player1.field.printField(true);
            System.out.println("---------------------");
            player2.field.printField(false);
            System.out.printf(Messages.TURN.toString(), "Player 2");

            if (player1.field.takeShot()) {
                break;
            }

            changeTurn();
        }
    }

    private void changeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.ENTER);
        scanner.nextLine();
    }
}
