package hyperskill.battleship;

public enum Ship {
    AIRCRAFT_CARRIER("Aircraft Carrier", 5),
    BATTLESHIP("Battleship", 4),
    SUBMARINE("Submarine", 3),
    CRUISER("Cruiser", 3),
    DESTROYER("Destroyer", 2);

    private final String name;
    private final int length;
    private int[] coordinate;

    private boolean checked;

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public int[] getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(int[] coordinate) {
        this.coordinate = coordinate;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.checked = false;
    }
}
