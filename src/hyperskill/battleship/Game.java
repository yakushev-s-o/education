package hyperskill.battleship;

public class Game {
    public void run() {
        Field field = new Field();
        field.initial();
        field.printField();

        for (Ship ship : Ship.values()) {
            field.placeShip(ship);
            field.printField();
        }

        System.out.println(Messages.GAME_STARTS);
        field.printField();
        field.takeShot();
        field.printField();
    }
}
