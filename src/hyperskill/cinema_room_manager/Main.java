//package hyperskill.cinema_room_manager;
//
//import java.util.Scanner;
//
//public class Main {
//    private static final char SEAT = 'S';
//    private static final char BUSY = 'B';
//    private static final int MAX_SEATS = 60;
//    private static final int TICKET_NORMAL_PRICE = 10;
//    private static final int TICKET_LOW_PRICE = 8;
//
//    public static void printField(int[][] field) {
//        System.out.println("Cinema:");
//        for (int i = 0; i <= field.length; i++) {
//            if (i == 0) {
//                System.out.print("  ");
//            } else {
//                System.out.print(i + " ");
//            }
//            for (int j = 0; j <= field[i].length; j++) {
//                if (i == 0) {
//                    System.out.print(j + 1 + " ");
//                } else {
//                    System.out.printf("%s ", SEAT);
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    public static void buyTicket(int[] field, int[] place) {
//        if (field[0] * field[1] <= MAX_SEATS || field[0] / 2 >= place[0]) {
//            System.out.printf("Ticket price: $%d\n", TICKET_NORMAL_PRICE);
//        } else {
//            System.out.printf("Ticket price: $%d\n", TICKET_LOW_PRICE);
//        }
//        PLACE = place;
//    }
//
//    public static void menu() {
//        Scanner sc = new Scanner(System.in);
//        System.out.println("Enter the number of rows:");
//        FIELD[0] = sc.nextInt();
//        System.out.println("Enter the number of seats in each row:");
//        FIELD[1] = sc.nextInt();
//
//        while (true) {
//            System.out.println("""
//                    1. Show the seats
//                    2. Buy a ticket
//                    0. Exit""");
//
//            int num = sc.nextInt();
//            if (num == 1) {
//                printField(FIELD, PLACE);
//            } else if (num == 2) {
//                System.out.println("Enter a row number:");
//                PLACE[0] = sc.nextInt();
//                System.out.println("Enter a seat number in that row:");
//                PLACE[1] = sc.nextInt();
//                buyTicket(FIELD, PLACE);
//            } else if (num == 0) {
//                break;
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        int[][] arr = new int[7][7];
//        printField(arr);
//    }
//}
