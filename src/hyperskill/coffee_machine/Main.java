package hyperskill.coffee_machine;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CoffeeMachine coffeeMachine = new CoffeeMachine(400, 540, 120, 9, 550);
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNext()) {
                coffeeMachine.run(scanner.next());
            }
        }
    }
}
