package hyperskill.tictactoe;

import java.util.*;

public class Main {
    private static char[][] clearField() {
        char[][] arr = new char[3][3];
        for (char[] row : arr) {
            Arrays.fill(row, ' ');
        }
        return arr;
    }

    private static void printField(char[][] ch) {
        System.out.printf("""
                        ---------
                        | %s %s %s |
                        | %s %s %s |
                        | %s %s %s |
                        ---------
                        """,
                ch[0][0], ch[0][1], ch[0][2],
                ch[1][0], ch[1][1], ch[1][2],
                ch[2][0], ch[2][1], ch[2][2]);
    }


    private static int getState(char[][] inputArr) {
        int win = 0;

        if (inputArr[0][0] == inputArr[0][1] && inputArr[0][1] == inputArr[0][2] ||
                inputArr[0][0] == inputArr[1][0] && inputArr[1][0] == inputArr[2][0]) {
            if (inputArr[0][0] != ' ') {
                win = 1;
            }
        }

        if (inputArr[0][0] == inputArr[1][1] && inputArr[1][1] == inputArr[2][2] ||
                inputArr[1][0] == inputArr[1][1] && inputArr[1][1] == inputArr[1][2] ||
                inputArr[0][2] == inputArr[1][1] && inputArr[1][1] == inputArr[2][0] ||
                inputArr[0][1] == inputArr[1][1] && inputArr[1][1] == inputArr[2][1]) {
            if (inputArr[1][1] != ' ') {
                win = 2;
            }
        }

        if (inputArr[0][2] == inputArr[1][2] && inputArr[1][2] == inputArr[2][2] ||
                inputArr[2][0] == inputArr[2][1] && inputArr[2][1] == inputArr[2][2]) {

            if (inputArr[2][2] != ' ') {
                win = 3;
            }
        }

        return win;
    }

    private static int countSpace(char[][] inputArr) {
        int count = 0;
        for (char[] chars : inputArr) {
            for (char ch : chars) {
                if (ch == ' ') {
                    count++;
                }
            }
        }
        return count;
    }

    private static void printState(char[][] inputArr, int win) {
        if (countSpace(inputArr) == 0 && win == 0) {
            System.out.println("Draw");
        } else if (win > 0) {
            System.out.println(inputArr[win - 1][win - 1] + " wins");
        }
    }

    private static void readMove(char[][] inputArr, char moveChar) {
        Scanner scanner = new Scanner(System.in);
        int x, y;

        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                x = scanner.nextInt();
                y = scanner.nextInt();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("You should enter numbers!");
                continue;
            }

            if (x > 3 || x < 1 || y > 3 || y < 1) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else if (inputArr[x - 1][y - 1] != ' ') {
                System.out.println("This cell is occupied! Choose another one!");
            } else {
                inputArr[x - 1][y - 1] = moveChar;
                break;
            }
        }
    }

    private static void startGame(char[][] inputArr) {
        boolean playerTurn = true;
        while (getState(inputArr) == 0 && countSpace(inputArr) != 0) {
            if (playerTurn) {
                readMove(inputArr, 'X');
            } else {
                readMove(inputArr, 'O');
            }
            printField(inputArr);
            playerTurn = !playerTurn;
        }
        printState(inputArr, getState(inputArr));
    }

    public static void main(String[] args) {
        char[][] inputArr = clearField();
        printField(inputArr);
        startGame(inputArr);
    }
}
