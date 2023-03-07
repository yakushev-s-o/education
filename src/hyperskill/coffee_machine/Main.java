package hyperskill.coffee_machine;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine();
        coffeeMachine.printResource();
        coffeeMachine.request();
        coffeeMachine.printResource();
    }
}
