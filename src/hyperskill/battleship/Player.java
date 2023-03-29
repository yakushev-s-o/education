package hyperskill.battleship;

public class Player {
    Field field;
    String name;

    public Player(String name) {
        this.field = new Field();
        this.name = name;
    }
}