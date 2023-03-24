package hyperskill.battleship;

import java.util.*;

public class Field {
    private char[][] field;
    private final static int FIELD_SIZE = 10;
    private static final char FOG = '~';
    private static final char SHIP = 'O';
    private static final char SHIT = 'X';
    private static final char MISS = 'M';

    public void initial() {
        field = new char[FIELD_SIZE][FIELD_SIZE];
        for (char[] chars : field) {
            Arrays.fill(chars, FOG);
        }
    }

    public void display() {
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

    public void run() {
        initial();
        display();

        for (Ship ship : Ship.values()) {
            fill(ship);
            display();
        }
    }

    public void fill(Ship ship) {
        int[][] coordinate;

        do {
            System.out.printf(Messages.ENTER_SHIP.toString(), ship.getName(), ship.getLength());
            coordinate = setCoordinates();
        } while (!checkValid(coordinate, ship));


        for (int i = coordinate[0][0] - 1; i <= coordinate[1][0] - 1; i++) {
            for (int j = coordinate[0][1] - 1; j <= coordinate[1][1] - 1; j++) {
                field[i][j] = SHIP;
            }
        }
    }

    public boolean checkValid(int[][] coordinate, Ship ship) {
        if (coordinate[0][0] < 1 || coordinate[0][0] > 10 || coordinate[1][0] < 1 || coordinate[1][0] > 10) {
            System.out.println(Messages.ERROR_INPUT);
            return false;
        } else if (coordinate[0][0] != coordinate[1][0] && coordinate[0][1] != coordinate[1][1]) {
            System.out.println(Messages.ERROR_LOCATION);
            return false;
        } else if ((Math.abs(coordinate[0][1] - coordinate[1][1]) + 1 != ship.getLength()) &&
                (Math.abs(coordinate[0][0] - coordinate[1][0]) + 1 != ship.getLength())) {
            System.out.printf(Messages.ERROR_LENGTH.toString(), ship.getName());
            return false;
        } else {
            for (int i = coordinate[0][0] - 2; i <= coordinate[1][0]; i++) {
                for (int j = coordinate[0][1] - 2; j <= coordinate[1][1]; j++) {
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

    public int[][] setCoordinates() {
        Scanner sc = new Scanner(System.in);

        int[] x = convertCoordinates(sc.next());
        int[] y = convertCoordinates(sc.next());

        if (x[1] > y[1] || x[0] > y[0]) {
            return new int[][]{y, x};
        } else {
            return new int[][]{x, y};
        }
    }

    public int[] convertCoordinates(String s) {
        int first = "ABCDEFGHIJ".indexOf(s.toUpperCase().charAt(0)) + 1;
        int second = Integer.parseInt(s.substring(1));
        return new int[]{first, second};
    }
}
