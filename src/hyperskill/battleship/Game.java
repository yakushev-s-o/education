package hyperskill.battleship;

import java.util.Scanner;

public class Game {
    public void run() {
        boolean isGameOver = false;
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        System.out.printf(Messages.PLAYER.toString(), "Player 1");
        player1.field.printField(false);
        addShips(player1.field);
        changeTurn();

        System.out.printf(Messages.PLAYER.toString(), "Player 2");
        player2.field.printField(false);
        addShips(player2.field);
        changeTurn();

        System.out.println(Messages.GAME_STARTS);

        // a1 a5 c1 c4 e1 e3 g1 g3 i1 i2
        while (!isGameOver) {
            player2.field.printField(true);
            System.out.println("---------------------");
            player1.field.printField(false);
            System.out.printf(Messages.TURN.toString(), "Player 1");
            isGameOver = player2.field.takeShot();
            if (isGameOver) {
                break;
            }
            changeTurn();
            player1.field.printField(true);
            System.out.println("---------------------");
            player2.field.printField(false);
            System.out.printf(Messages.TURN.toString(), "Player 2");
            isGameOver = player1.field.takeShot();
            if (isGameOver) {
                break;
            }
            changeTurn();
        }
    }

    private void addShips(Field field) {
        for (Ship ship : Ship.values()) {
            System.out.printf(Messages.ENTER_SHIP.toString(), ship.getName(), ship.getLength());
            field.placeShip(ship);
            field.printField(false);
        }
    }

    private void changeTurn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.ENTER);
        scanner.nextLine();
    }
}
