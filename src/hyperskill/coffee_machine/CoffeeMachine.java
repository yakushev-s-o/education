package hyperskill.coffee_machine;

import java.util.Scanner;

public class CoffeeMachine {
    private int water = 400;
    private int milk = 540;
    private int coffee = 120;
    private int cups = 9;
    private int money = 550;

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

    public void request() {
        Scanner sc = new Scanner(System.in);

        System.out.println(Messages.ACTION);
        String action = sc.next();

        switch (action) {
            case "buy" -> {
                System.out.println(Messages.BUY);
                int type = sc.nextInt();
                buy(type);
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
        }
    }

//    public int calcCups(int water, int milk, int coffee) {
//        int count = 0;
//
//        while (water >= 200 && milk >= 50 && coffee >= 15) {
//            count++;
//            water -= this.water;
//            milk -= this.milk;
//            coffee -= this.coffee;
//        }
//
//        return count;
//    }

//    public void estimate(int calc, int need) {
//        if (calc == need) {
//            System.out.println(Messages.I_CAN_MAKE);
//        } else if (calc > need) {
//            System.out.printf(Messages.I_CAN_MAKE_MORE.toString(), calc - need);
//        } else {
//            System.out.printf(Messages.I_CAN_NOT_MAKE.toString(), calc);
//        }
//    }

    public void printResource() {
        System.out.printf(Messages.RESOURCE.toString(),
                getWater(), getMilk(), getCoffee(), getCups(), getMoney());
    }

    public void buy(int type) {
        switch (type) {
            case 1 -> {
                setWater(this.water - 250);
                setCoffee(this.coffee - 16);
                setMoney(this.money + 4);
                setCups(this.cups - 1);
            }
            case 2 -> {
                setWater(this.water - 350);
                setMilk(this.milk - 75);
                setCoffee(this.coffee - 20);
                setMoney(this.money + 7);
                setCups(this.cups - 1);
            }
            case 3 -> {
                setWater(this.water - 200);
                setMilk(this.milk - 100);
                setCoffee(this.coffee - 12);
                setMoney(this.money + 6);
                setCups(this.cups - 1);
            }
        }
    }

    public void fill(int water, int milk, int coffee, int cups) {
        setWater(this.water + water);
        setMilk(this.milk + milk);
        setCoffee(this.coffee + coffee);
        setCups(this.cups + cups);
    }

    public void take() {
        setMoney(0);
    }
}
