package hyperskill.cinema_room_manager;

import java.util.Scanner;

public class Main {
    private static final char SEAT = 'S';
    private static final char BUSY = 'B';
    private static final int MAX_SEATS = 60;
    private static final int TICKET_NORMAL_PRICE = 10;
    private static final int TICKET_LOW_PRICE = 8;

    public static void clearSeats(char[][] field) {
        for (int i = 1; i < field.length; i++) {
            for (int j = 1; j < field[i].length; j++) {
                field[i][j] = SEAT;
            }
        }
    }

    public static void printSeats(char[][] field) {
        System.out.println("Cinema:");
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (i == 0) {
                    System.out.print(j + " ");
                } else if (j == 0) {
                    System.out.print(i + " ");
                } else {
                    System.out.print(field[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void buyTicket(char[][] seats, int row, int seat) {
        if (seats.length * seats[0].length < MAX_SEATS ||
                seats.length / 2 > row) {
            System.out.printf("Ticket price: $%d\n", TICKET_NORMAL_PRICE);
        } else {
            System.out.printf("Ticket price: $%d\n", TICKET_LOW_PRICE);
        }
        seats[row][seat] = BUSY;
    }

    public static void menu() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int row = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seat = sc.nextInt();
        char[][] seats = new char[row + 1][seat + 1];

        clearSeats(seats);

        while (true) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Cancel ticket purchase
                    4. Clear Seats
                    0. Exit""");

            int num = sc.nextInt();
            if (num == 1) {
                printSeats(seats);
            } else if (num == 2) {
                System.out.println("Enter a row number:");
                row = sc.nextInt();
                System.out.println("Enter a seat number in that row:");
                seat = sc.nextInt();
                buyTicket(seats, row, seat);
            } else if (num == 3) {
                System.out.println("Enter the row number of the seat you would like to cancel:");
                row = sc.nextInt();
                System.out.println("Enter the seat number in that row:");
                seat = sc.nextInt();
                seats[row][seat] = SEAT;
            } else if (num == 4) {
                clearSeats(seats);
            } else if (num == 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}