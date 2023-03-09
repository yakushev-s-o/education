package hyperskill.coffee_machine;

import java.util.Scanner;

public class CoffeeMachine {
    final int WATER_IN_CUP = 200;
    final int MILK_IN_CUP = 50;
    final int COFFEE_IN_CUP = 15;
    private int water;
    private int milk;
    private int coffee;
    private int cups;
    private int money;

    public CoffeeMachine(int water, int milk, int coffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.coffee = coffee;
        this.cups = cups;
        this.money = money;
    }

    public int getWater() {
        return water;
    }

    public void setWater(int water) {
        this.water = water;
    }

    public int getMilk() {
        return milk;
    }

    public void setMilk(int milk) {
        this.milk = milk;
    }

    public int getCoffee() {
        return coffee;
    }

    public void setCoffee(int coffee) {
        this.coffee = coffee;
    }

    public int getCups() {
        return cups;
    }

    public void setCups(int cups) {
        this.cups = cups;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void run() {
        boolean check = true;
        try (Scanner sc = new Scanner(System.in)) {
            do {
                System.out.println(Messages.ACTION);
                String action = sc.next();

                switch (action) {
                    case "buy" -> {
                        System.out.println(Messages.BUY);
                        String type = sc.next();
                        if (!"back".equals(type) && checkResource()) {
                            buy(Integer.parseInt(type));
                        }
                    }
                    case "fill" -> {
                        System.out.println(Messages.ADD_WATER);
                        int water = sc.nextInt();
                        System.out.println(Messages.ADD_MILK);
                        int milk = sc.nextInt();
                        System.out.println(Messages.ADD_COFFEE);
                        int coffee = sc.nextInt();
                        System.out.println(Messages.ADD_CUPS);
                        int cups = sc.nextInt();
                        fill(water, milk, coffee, cups);
                    }
                    case "take" -> {
                        System.out.printf(Messages.TAKE.toString(), this.getMoney());
                        take();
                    }
                    case "remaining" -> remaining();
                    case "exit" -> check = false;
                }
            } while (check);
        }
    }

    public boolean checkResource() {
        if (getWater() < WATER_IN_CUP) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "water");
            return false;
        } else if (getMilk() < MILK_IN_CUP) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "milk");
            return false;
        } else if (getCoffee() < COFFEE_IN_CUP) {
            System.out.printf(Messages.NO_ENOUGH_RESOURCE.toString(), "coffee");
            return false;
        } else {
            System.out.println(Messages.ENOUGH_RESOURCE);
        }

        return true;
    }

    public void buy(int type) {
        switch (type) {
            case 1 -> {
                setWater(getWater() - 250);
                setCoffee(getCoffee() - 16);
                setMoney(getMoney() + 4);
                setCups(getCups() - 1);
            }
            case 2 -> {
                setWater(getWater() - 350);
                setMilk(getMilk() - 75);
                setCoffee(getCoffee() - 20);
                setMoney(getMoney() + 7);
                setCups(getCups() - 1);
            }
            case 3 -> {
                setWater(getWater() - 200);
                setMilk(getMilk() - 100);
                setCoffee(getCoffee() - 12);
                setMoney(getMoney() + 6);
                setCups(getCups() - 1);
            }
        }
    }

    public void fill(int water, int milk, int coffee, int cups) {
        setWater(getWater() + water);
        setMilk(getMilk() + milk);
        setCoffee(getCoffee() + coffee);
        setCups(getCups() + cups);
    }

    public void take() {
        setMoney(0);
    }

    public void remaining() {
        System.out.printf(Messages.RESOURCE.toString(),
                getWater(), getMilk(), getCoffee(), getCups(), getMoney());
    }
}
