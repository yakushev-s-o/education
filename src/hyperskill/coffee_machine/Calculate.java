package hyperskill.coffee_machine;

public class Calculate {
    final int water = 200;
    final int milk = 50;
    final int coffee = 15;

    public int calculate(int water, int milk, int coffee) {
        int count = 0;

        while (water >= 200 && milk >= 50 && coffee >= 15) {
            count++;
            water -= this.water;
            milk -= this.milk;
            coffee -= this.coffee;
        }

        return count;
    }

    public void estimate(int water, int milk, int coffee, int need) {
        int calc = calculate(water, milk, coffee);

        if (calc == 1 && calc == need) {
            System.out.println(Messages.I_CAN_MAKE);
        } else if (calc > need) {
            System.out.printf(Messages.I_CAN_MAKE_MORE.toString(), calc);
        } else {
            System.out.printf(Messages.I_CAN_NOT_MAKE.toString(), calc);
        }
    }
}
