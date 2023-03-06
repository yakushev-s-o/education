package hyperskill.coffee_machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println(Messages.HOW_MANY_WATER);
        int water = sc.nextInt();
        System.out.println(Messages.HOW_MANY_MILK);
        int milk = sc.nextInt();
        System.out.println(Messages.HOW_MANY_COFFEE);
        int coffee = sc.nextInt();
        System.out.println(Messages.HOW_NEED_COFFEE);
        int need = sc.nextInt();

        Calculate calculate = new Calculate();
        calculate.estimate(water, milk, coffee, need);
    }
}
