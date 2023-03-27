package hyperskill.battleship;

public class Game {
    public void run() {
        Field field = new Field();
        field.printField(field.getField());

        for (Ship ship : Ship.values()) {
            System.out.printf(Messages.ENTER_SHIP.toString(), ship.getName(), ship.getLength());
            field.placeShip(ship);
            field.printField(field.getField());
        }

        System.out.println(Messages.GAME_STARTS);
        field.printField(field.getFogField());

        int count = 0;
        while (count < 5) {
            System.out.println(Messages.TAKE_SHOT);
            field.takeShot();
            count += field.isSunk();
        }

        field.printField(field.getField());
        System.out.println(Messages.WON);
    }
}
