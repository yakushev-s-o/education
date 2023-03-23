package hyperskill.battleship;

import java.util.Arrays;
import java.util.Scanner;

public class Field {
    private char[][] field;
    private final static int FIELD_SIZE = 10;
    private static final char FOG = '~';
    private static final char SHIP = 'O';
    private static final char SHIT = 'X';
    private static final char MISS = 'M';

    public void run() {
        initial();
        display();
        fill(Ship.AIRCRAFT_CARRIER);
        display();
    }

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

    public void fill(Ship ship) {
        Scanner sc = new Scanner(System.in);
        int[] c1 = convertCoordinates(sc.next());
        int[] c2 = convertCoordinates(sc.next());

        int[][] coordinate;

        if (c1[1] > c2[1] || c1[0] > c2[0]) {
            coordinate = new int[][]{c2, c1};
        } else {
            coordinate = new int[][]{c1, c2};
        }


        if (coordinate[0][0] < 1 || coordinate[0][0] > 10 || coordinate[1][0] < 1 || coordinate[1][0] > 10) {
            System.out.println("Error! Invalid input!");
        } else if (coordinate[0][0] != coordinate[1][0] && coordinate[0][1] != coordinate[1][1]) {
            System.out.println("Error! Wrong ship location! Try again:");
        } else if ((Math.abs(coordinate[0][1] - coordinate[1][1]) + 1 != ship.getLength()) &&
                (Math.abs(coordinate[0][0] - coordinate[1][0]) + 1 != ship.getLength())) {
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
        } else {
            for (int i = coordinate[0][0] - 2; i <= coordinate[1][0]; i++) {
                for (int j = coordinate[0][1] - 2; j <= coordinate[1][1]; j++) {
                    if ((i >= 0 && i < field.length) && (j >= 0 && j < field.length)) {
                        if (field[i][j] == 'O') {
                            System.out.println("Error! You placed it too close to another one. Try again:");
                            return;
                        }
                    }
                }
            }
        }

        for (int i = coordinate[0][0] - 1; i <= coordinate[1][0] - 1; i++) {
            for (int j = coordinate[0][1] - 1; j <= coordinate[1][1] - 1; j++) {
                field[i][j] = 'O';
            }
        }
    }

    public static int[] convertCoordinates(String s) {
        int[] coordinate = {0, Integer.parseInt(s.substring(1))};

        switch (s.substring(0, 1)) {
            case "A" -> coordinate[0] = 1;
            case "B" -> coordinate[0] = 2;
            case "C" -> coordinate[0] = 3;
            case "D" -> coordinate[0] = 4;
            case "E" -> coordinate[0] = 5;
            case "F" -> coordinate[0] = 6;
            case "G" -> coordinate[0] = 7;
            case "H" -> coordinate[0] = 8;
            case "I" -> coordinate[0] = 9;
            case "J" -> coordinate[0] = 10;
        }

        return coordinate;
    }
}
