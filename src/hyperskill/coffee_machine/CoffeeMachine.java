package hyperskill.coffee_machine;

public class CoffeeMachine {
    private MachineState current;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int money;

    public CoffeeMachine(int water, int milk, int beans, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.money = money;
        menu();
    }

    public void run(String input) {
        switch (current) {
            case MENU -> action(input);
            case BUY -> buy(input);
            case FILL_WATER -> fillingWater(input);
            case FILL_MILK -> fillingMilk(input);
            case FILL_BEANS -> fillingBeans(input);
            case FILL_CUPS -> fillingCups(input);
        }
    }

    public void menu() {
        current = MachineState.MENU;
        System.out.println(Messages.ACTION);
    }

    public void action(String input) {
        switch (input) {
            case "take" -> take();
            case "buy" -> {
                System.out.println(Messages.BUY);
                current = MachineState.BUY;
            }
            case "fill" -> {
                System.out.println(Messages.ADD_WATER);
                current = MachineState.FILL_WATER;
            }
            case "remaining" -> remaining();
            case "exit" -> System.exit(0);
        }
    }

    public void buy(String input) {
        switch (input) {
            case "1" -> coffeeMaking(CoffeeStandards.ESPRESSO);
            case "2" -> coffeeMaking(CoffeeStandards.LATTE);
            case "3" -> coffeeMaking(CoffeeStandards.CAPPUCCINO);
            case "back" -> menu();
        }
    }

    public void coffeeMaking(CoffeeStandards standards) {
        if (water < standards.getWater()) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "water");
        } else if (milk < standards.getMilk()) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "milk");
        } else if (beans < standards.getBeans()) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "beans");
        } else {
            System.out.println(Messages.ENOUGH_RESOURCE);
            water -= standards.getWater();
            milk -= standards.getMilk();
            beans -= standards.getBeans();
            money += standards.getPrice();
            cups -= 1;
        }
        menu();
    }

    public void fillingWater(String input) {
        water += Integer.parseInt(input);
        current = MachineState.FILL_MILK;
        System.out.println(Messages.ADD_MILK);

    }

    public void fillingMilk(String input) {
        milk += Integer.parseInt(input);
        current = MachineState.FILL_BEANS;
        System.out.println(Messages.ADD_BEANS);
    }

    public void fillingBeans(String input) {
        beans += Integer.parseInt(input);
        current = MachineState.FILL_CUPS;
        System.out.println(Messages.ADD_CUPS);
    }

    public void fillingCups(String input) {
        cups += Integer.parseInt(input);
        menu();
    }

    public void take() {
        System.out.printf(Messages.TAKE.toString(), money);
        money = 0;
        menu();
    }

    public void remaining() {
        System.out.printf(Messages.RESOURCE.toString(), water, milk, beans, cups, money);
        menu();
    }
}
