package hyperskill.battleship;

import java.util.*;

public class Field {
    private char[][] field;
    private char[][] fogField;
    private final static int FIELD_SIZE = 10;
    private final static String LETTERS = "ABCDEFGHIJ";
    private static final char FOG = '~';
    private static final char SHIP = 'O';
    private static final char HIT = 'X';
    private static final char MISS = 'M';

    private final Scanner sc = new Scanner(System.in);

    public void clearField() {
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (char[] chars : field) {
            Arrays.fill(chars, FOG);
        }
    }

    public void printField() {
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

    public void placeShip(Ship ship) {
        while (true) {
            int[] coordinate;

            try {
                coordinate = getCoordinates();
            } catch (NumberFormatException e) {
                System.out.println(Messages.ERROR_INPUT);
                continue;
            }

            if (isCoordinatesValid(coordinate) && isPossibleToPlaceShip(coordinate, ship)) {
                for (int i = coordinate[0] - 1; i <= coordinate[2] - 1; i++) {
                    for (int j = coordinate[1] - 1; j <= coordinate[3] - 1; j++) {
                        field[i][j] = SHIP;
                    }
                }
                break;
            }
        }
    }

    public boolean isCoordinatesValid(int[] coordinate) {
        if (coordinate[0] < 1 || coordinate[0] > 10 || coordinate[1] < 1 || coordinate[1] > 10) {
            System.out.println(Messages.ERROR_INPUT);
            return false;
        }
        return true;
    }

    public boolean isPossibleToPlaceShip(int[] coordinate, Ship ship) {
        if (coordinate[0] != coordinate[2] && coordinate[1] != coordinate[3]) {
            System.out.println(Messages.ERROR_LOCATION);
            return false;
        } else if ((Math.abs(coordinate[1] - coordinate[3]) + 1 != ship.getLength()) &&
                (Math.abs(coordinate[0] - coordinate[2]) + 1 != ship.getLength())) {
            System.out.printf(Messages.ERROR_LENGTH.toString(), ship.getName());
            return false;
        } else {
            for (int i = coordinate[0] - 2; i <= coordinate[2]; i++) {
                for (int j = coordinate[1] - 2; j <= coordinate[3]; j++) {
                    if ((i >= 0 && i < field.length) && (j >= 0 && j < field.length)) {
                        if (field[i][j] == SHIP) {
                            System.out.println(Messages.ERROR_CLOSE);
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void takeShot() {
        while (true) {
            int[] coordinate;

            try {
                coordinate = getCoordinates();
            } catch (NumberFormatException e) {
                System.out.println(Messages.ERROR_INPUT);
                continue;
            }

            if (isCoordinatesValid(coordinate)) {
                if (fogField[coordinate[0] - 1][coordinate[1] - 1] == SHIP) {
                    field[coordinate[0] - 1][coordinate[1] - 1] = HIT;
                    fogField[coordinate[0] - 1][coordinate[1] - 1] = HIT;
                    printField();
                    System.out.println(Messages.HIT);
                } else if (fogField[coordinate[0] - 1][coordinate[1] - 1] == FOG) {
                    field[coordinate[0] - 1][coordinate[1] - 1] = MISS;
                    fogField[coordinate[0] - 1][coordinate[1] - 1] = MISS;
                    printField();
                    System.out.println(Messages.MISS);
                }
                break;
            }
        }
    }

    public int[] getCoordinates() {
        String[] coordinate = sc.nextLine().split(" ");

        int firstL = LETTERS.indexOf(coordinate[0].toUpperCase().charAt(0)) + 1;
        int firstN = Integer.parseInt(coordinate[0].substring(1));

        if (coordinate.length > 1) {
            int secondL = LETTERS.indexOf(coordinate[1].toUpperCase().charAt(0)) + 1;
            int secondN = Integer.parseInt(coordinate[1].substring(1));

            if (firstN > secondN || firstL > secondL) {
                return new int[]{secondL, secondN, firstL, firstN};
            } else {
                return new int[]{firstL, firstN, secondL, secondN};
            }
        } else {

            return new int[]{firstL, firstN};
        }
    }

    public void setCopyField() {
        fogField = field;
    }

    public void setField() {
        field = fogField;
    }
}
