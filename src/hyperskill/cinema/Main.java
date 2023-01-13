package hyperskill.cinema;

import java.util.*;

public class Main {
    private static final char SEAT = 'S';
    private static final char BUSY = 'B';
    private static final int MAX_SEATS = 60;
    private static final int TICKET_NORMAL_PRICE = 10;
    private static final int TICKET_LOW_PRICE = 8;
    private static char[][] cinema;
    private static int tickets;
    private static int currentIncome;
    private static int totalIncome;
    public static int rowCinema;
    public static int seatCinema;

    public static Scanner sc = new Scanner(System.in);

    public static void clearCinema() {
        for (int i = 1; i < cinema.length; i++) {
            for (int j = 1; j < cinema[i].length; j++) {
                cinema[i][j] = SEAT;
            }
        }
        tickets = 0;
        currentIncome = 0;
        totalIncome = 0;
    }

    public static void printCinema() {
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
        while (true) {
            int row, seat;
            try {
                System.out.println("Enter a row number:");
                row = sc.nextInt();
                System.out.println("Enter a seat number in that row:");
                seat = sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("You should enter numbers!");
                continue;
            }

            if (row > cinema.length - 1 || row < 0 || seat > cinema[0].length - 1 || seat < 0) {
                System.out.println("Wrong input!");
            } else if (cinema[row][seat] == BUSY) {
                System.out.println("That ticket has already been purchased!");
            } else {
                if (rowCinema * seatCinema < MAX_SEATS || rowCinema / 2 >= row) {
                    currentIncome += TICKET_NORMAL_PRICE;
                    System.out.printf("Ticket price: $%d\n", TICKET_NORMAL_PRICE);
                } else {
                    currentIncome += TICKET_LOW_PRICE;
                    System.out.printf("Ticket price: $%d\n", TICKET_LOW_PRICE);
                }
                cinema[row][seat] = BUSY;
                tickets++;
                break;
            }
        }
    }

    public static void printStat() {
        if (rowCinema * seatCinema < MAX_SEATS) {
            totalIncome = rowCinema * seatCinema * TICKET_NORMAL_PRICE;
        } else {
            totalIncome = rowCinema / 2 * seatCinema * TICKET_NORMAL_PRICE +
                    (rowCinema % 2 == 0 ? rowCinema / 2 : rowCinema / 2 + 1) * seatCinema * TICKET_LOW_PRICE;
        }

        System.out.println("Number of purchased tickets: " + tickets);
        System.out.printf("Percentage: %.2f%%\n", 100f / (float) (rowCinema * seatCinema) * (float) tickets);
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
    }

    public static void createCinema() {
        while (true) {
            try {
                System.out.println("Enter the number of rows:");
                rowCinema = sc.nextInt();
                System.out.println("Enter the number of seats in each row:");
                seatCinema = sc.nextInt();

                if (rowCinema < 0 || seatCinema < 0) {
                    System.out.println("Wrong input!");
                    continue;
                }
            } catch (Exception e) {
                sc.nextLine();
                System.out.println("You should enter numbers!");
                continue;
            }
            break;
        }
        cinema = new char[rowCinema + 1][seatCinema + 1];
    }

    public static void menu() {
        createCinema();
        clearCinema();

        while (true) {
            System.out.println("""
                    1. Show the seats
                    2. Buy a ticket
                    3. Statistics
                    4. Clear Seats
                    5. New Cinema
                    0. Exit""");

            int num = sc.nextInt();

            if (num == 1) {
                printCinema();
            } else if (num == 2) {
                buyTicket();
            } else if (num == 3) {
                printStat();
            } else if (num == 4) {
                clearCinema();
            } else if (num == 5) {
                createCinema();
                clearCinema();
            } else if (num == 0) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        menu();
    }
}