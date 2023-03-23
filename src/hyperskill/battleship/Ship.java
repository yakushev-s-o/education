package hyperskill.battleship;

public enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int length;

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }
}
