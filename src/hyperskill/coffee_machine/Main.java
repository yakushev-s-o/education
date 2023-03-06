package hyperskill.coffee_machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
//        System.out.println("""
//                        Starting to make a coffee
//                        Grinding coffee beans
//                        Boiling water
//                        Mixing boiled water with crushed coffee beans
//                        Pouring coffee into the cup
//                        Pouring some milk into the cup
//                        Coffee is ready!""");

        Scanner sc = new Scanner(System.in);
        int cups = sc.nextInt();

        System.out.printf("For %d cups of coffee you will need:\n", cups);
        calculator(cups);
    }

    public static void calculator(int cups) {
        final int water = 200;
        final int milk = 50;
        final int coffee = 15;

        System.out.printf("%d ml of water\n%d ml of milk\n%d g of coffee beans",
                water * cups, milk * cups, coffee * cups);
    }
}
