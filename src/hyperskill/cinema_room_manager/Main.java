package hyperskill.cinema_room_manager;

import java.util.Scanner;

public class Main {
    private static final char SEAT = 'S';
    private static final char BUSY = 'B';
    private static final int MAX_SEATS = 60;
    private static final int TICKET_NORMAL_PRICE = 10;
    private static final int TICKET_LOW_PRICE = 8;
    private static char[][] cinema;
    private static int tickets;
    private static float percentage;
    private static int currentIncome;
    private static int totalIncome;
    private static int totalNumOfSeats;

    public static Scanner sc = new Scanner(System.in);

    public static void clearSeats() {
        for (int i = 1; i < cinema.length; i++) {
            for (int j = 1; j < cinema[i].length; j++) {
                cinema[i][j] = SEAT;
            }
        }
        tickets = 0;
        percentage = 0.00f;
        currentIncome = 0;
    }

    public static void printSeats() {
        System.out.println("Cinema:");
        for (int i = 0; i < cinema.length; i++) {
            for (int j = 0; j < cinema[i].length; j++) {
                if (i == 0 && j == 0) {
                    System.out.print("  ");
                } else if (i == 0) {
                    System.out.print(j + " ");
                } else if (j == 0) {
                    System.out.print(i + " ");
                } else {
                    System.out.print(cinema[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void buyTicket() {
        System.out.println("Enter a row number:");
        int row = sc.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seat = sc.nextInt();

        if (row > cinema.length - 1 || row < 0 || seat > cinema[0].length - 1 || seat < 0) {
            System.out.println("Wrong input!");
            buyTicket();
        } else if (cinema[row][seat] == BUSY) {
            System.out.println("That ticket has already been purchased!");
            buyTicket();
        } else {
            if (totalNumOfSeats < MAX_SEATS || cinema.length / 2 > row) {
                currentIncome = row * seat * TICKET_NORMAL_PRICE;
                System.out.printf("Ticket price: $%d\n", TICKET_NORMAL_PRICE);
            } else {
                currentIncome =  row / 2 * seat * 10 + (row - (row / 2)) * seat * 8;
                System.out.printf("Ticket price: $%d\n", TICKET_LOW_PRICE);
            }
            cinema[row][seat] = BUSY;
            tickets++;
        }
    }

    public static void cancelTicket() {
        System.out.println("Enter the row number of the seat you would like to cancel:");
        int row = sc.nextInt();
        System.out.println("Enter the seat number in that row:");
        int seat = sc.nextInt();
        cinema[row][seat] = SEAT;
    }

    public static void statistics() {
        percentage = 100f / (float) totalNumOfSeats * (float) tickets;

        System.out.println("Number of purchased tickets: " + tickets);
        System.out.printf("Percentage: %.2f%%\n", percentage);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static void start() {
        System.out.println("Enter the number of rows:");
        int row = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seat = sc.nextInt();
        cinema = new char[row + 1][seat + 1];
        totalNumOfSeats = row * seat;

        for (int i = 1; i < cinema.length; i++) {
            for (int j = 1; j < cinema[i].length; j++) {
                if (totalNumOfSeats < MAX_SEATS) {
                    totalIncome = row * seat * TICKET_NORMAL_PRICE;
                } else {
                    totalIncome +=  row / 2 * seat * 10 + (row % 2 == 0 ? row / 2 : row / 2 + 1) * seat * 8;
                }
            }
        }
    }

    public static void menu() {
        start();
        clearSeats();

        while (true) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    4. Cancel ticket purchase
                    5. Clear Seats
                    0. Exit""");

            int num = sc.nextInt();

            if (num == 1) {
                printSeats();
            } else if (num == 2) {
                buyTicket();
            } else if (num == 3) {
                statistics();
            } else if (num == 4) {
                cancelTicket();
            } else if (num == 5) {
                clearSeats();
            } else if (num == 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}