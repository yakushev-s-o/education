package hyperskill.battleship;

import java.util.*;

public class Field {
    private final char[][] field;
    private final char[][] fogField;
    private final static int FIELD_SIZE = 10;
    private static final char FOG = '~';
    private static final char SHIP = 'O';
    private static final char HIT = 'X';
    private static final char MISS = 'M';

    private final Scanner sc = new Scanner(System.in);

    public Field() {
        field = new char[FIELD_SIZE][FIELD_SIZE];
        fogField = new char[FIELD_SIZE][FIELD_SIZE];
        initialize(field);
        initialize(fogField);
    }

    public char[][] getField() {
        return field;
    }

    public char[][] getFogField() {
        return fogField;
    }

    public void initialize(char[][] field) {
        for (char[] chars : field) {
            Arrays.fill(chars, FOG);
        }
    }

    public void printField(char[][] field) {
        char letter = 'A';
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        for (char[] chars : field) {
            System.out.print(letter++);
            for (char c : chars) {
                System.out.print(" " + c);
            }
            System.out.println();
        }
    }

    // a1 a5 c1 c4 e1 e3 g1 g3 i1 i2
    public void placeShip(Ship ship) {
        while (true) {
            int[] coordinate;

            try {
                coordinate = getCoordinates();
                isPossibleToPlaceShip(coordinate, ship);
                ship.setCoordinate(coordinate);

                for (int i = coordinate[0] - 1; i <= coordinate[2] - 1; i++) {
                    for (int j = coordinate[1] - 1; j <= coordinate[3] - 1; j++) {
                        field[i][j] = SHIP;
                    }
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.print(e.getMessage());
            }
        }
    }

    private void isPossibleToPlaceShip(int[] coordinate, Ship ship) {
        if (coordinate[0] != coordinate[2] && coordinate[1] != coordinate[3]) {
            throw new IllegalArgumentException(Messages.ERROR_LOCATION.toString());
        } else if ((Math.abs(coordinate[1] - coordinate[3]) + 1 != ship.getLength()) &&
                (Math.abs(coordinate[0] - coordinate[2]) + 1 != ship.getLength())) {
            throw new IllegalArgumentException(String.format(Messages.ERROR_LENGTH.toString(), ship.getName()));
        } else {
            for (int i = coordinate[0] - 2; i <= coordinate[2]; i++) {
                for (int j = coordinate[1] - 2; j <= coordinate[3]; j++) {
                    if ((i >= 0 && i < field.length) && (j >= 0 && j < field.length)) {
                        if (field[i][j] == SHIP) {
                            throw new IllegalArgumentException(Messages.ERROR_CLOSE.toString());
                        }
                    }
                }
            }
        }
    }

    public State takeShot() {
        while (true) {
            int[] coordinate;

            try {
                coordinate = getCoordinate(sc.next());

                switch (field[coordinate[0] - 1][coordinate[1] - 1]) {
                    case SHIP -> {
                        field[coordinate[0] - 1][coordinate[1] - 1] = HIT;
                        fogField[coordinate[0] - 1][coordinate[1] - 1] = HIT;
                        if (isSunk()) {
                            return State.SANK;
                        } else {
                            return State.HIT;
                        }
                    }
                    case FOG -> {
                        field[coordinate[0] - 1][coordinate[1] - 1] = MISS;
                        fogField[coordinate[0] - 1][coordinate[1] - 1] = MISS;
                        return State.MISS;
                    }
                    case HIT -> {
                        return State.HIT_SHOT;
                    }
                    case MISS -> {
                        return State.HIT_MISS;
                    }
                }

                break;
            } catch (IllegalArgumentException e) {
                System.out.print(e.getMessage());
            }
        }

        return null;
    }

    public boolean isSunk() {
        for (Ship ship : Ship.values()) {
            int count = 0;
            if (!ship.isChecked()) {
                for (int i = ship.getCoordinate()[0] - 1; i <= ship.getCoordinate()[2] - 1; i++) {
                    for (int j = ship.getCoordinate()[1] - 1; j <= ship.getCoordinate()[3] - 1; j++) {
                        if (field[i][j] == HIT){
                            count++;
                        }
                    }
                }
            }

            if (count == ship.getLength()) {
                ship.setChecked(true);
                return true;
            }
        }

        return false;
    }

    private int[] getCoordinate(String coordinate) {
        int x = coordinate.toUpperCase().charAt(0) - 'A' + 1;

        int y;
        try {
            y = Integer.parseInt(coordinate.substring(1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(Messages.ERROR_INPUT.toString());
        }

        if (x < 1 || x > 10 || y < 1 || y > 10) {
            throw new IllegalArgumentException(Messages.ERROR_INPUT.toString());
        }

        return new int[]{x, y};
    }

    private int[] getCoordinates() {
        int[] coordinate1 = getCoordinate(sc.next());
        int[] coordinate2 = getCoordinate(sc.next());

        if (coordinate1[1] > coordinate2[1] || coordinate1[0] > coordinate2[0]) {
            return new int[]{coordinate2[0], coordinate2[1], coordinate1[0], coordinate1[1]};
        } else {
            return new int[]{coordinate1[0], coordinate1[1], coordinate2[0], coordinate2[1]};
        }
    }
}
