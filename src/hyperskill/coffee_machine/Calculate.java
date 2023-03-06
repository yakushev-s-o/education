package hyperskill.coffee_machine;

public class Calculate {
    final static int water = 200;
    final static int milk = 50;
    final static int coffee = 15;

    public static void calculator(int cups) {
        System.out.printf("%d ml of water\n%d ml of milk\n%d g of coffee beans",
                water * cups, milk * cups, coffee * cups);
    }
}
