package hyperskill.battleship;

import java.util.Arrays;

public class Field {
    private char[][] field;

    public void clearField() {
        field = new char[10][10];
        for (char[] chars : field) {
            Arrays.fill(chars, '~');
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
}
