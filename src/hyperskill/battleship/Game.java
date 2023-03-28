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

        System.out.println(Messages.TAKE_SHOT);

        int count = 0;
        while (count < 5) {
            switch (field.takeShot()) {
                case HIT -> {
                    field.printField(field.getFogField());
                    System.out.println(Messages.HIT);
                }
                case MISS -> {
                    field.printField(field.getFogField());
                    System.out.println(Messages.MISS);
                }
                case HIT_SHOT -> {
                    field.printField(field.getFogField());
                    System.out.println(Messages.HIT_SHOT);
                }
                case HIT_MISS -> {
                    field.printField(field.getFogField());
                    System.out.println(Messages.HIT_MISS);
                }
                case SANK -> {
                    field.printField(field.getFogField());
                    if (++count == 5) {
                        System.out.println(Messages.WON);
                    } else {
                        System.out.println(Messages.SANK);
                    }
                }
            }
        }

        field.printField(field.getField());
    }
}
