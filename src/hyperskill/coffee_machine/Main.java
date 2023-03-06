package hyperskill.coffee_machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int cups = sc.nextInt();

        System.out.printf(Messages.NEED_COFFEE.toString(), cups);
        Calculate.calculator(cups);
    }
}
