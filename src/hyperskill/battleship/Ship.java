package hyperskill.battleship;

public class Ship {
    private final String name;
    private final int length;
    private int[] coordinate;
    private boolean checked;

    Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.checked = false;
    }

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
}
