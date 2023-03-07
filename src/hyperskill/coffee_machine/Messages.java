package hyperskill.coffee_machine;

public enum Messages {
    INSTRUCTIONS("""
            Starting to make a coffee
            Grinding coffee beans
            Boiling water
            Mixing boiled water with crushed coffee beans
            Pouring coffee into the cup
            Pouring some milk into the cup
            Coffee is ready!
            """),
    HOW_NEED_COFFEE("Write how many cups of coffee you will need:"),
    HOW_MANY_WATER("Write how many ml of water the coffee machine has:"),
    HOW_MANY_MILK("Write how many ml of milk the coffee machine has:"),
    HOW_MANY_COFFEE("Write how many grams of coffee beans the coffee machine has:"),
    I_CAN_MAKE("Yes, I can make that amount of coffee"),
    I_CAN_MAKE_MORE("Yes, I can make that amount of coffee (and even %d more than that)"),
    I_CAN_NOT_MAKE("No, I can make only %d cup(s) of coffee"),
    RESOURCE("""
            The coffee machine has:
            %d ml of water
            %d ml of milk
            %d g of coffee beans
            %d disposable cups
            $%d of money
            """),
    ACTION("Write action (buy, fill, take):"),
    BUY("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:"),
    ADD_WATER("Write how many ml of water you want to add:"),
    ADD_MILK("Write how many ml of milk you want to add:"),
    ADD_COFFEE("Write how many grams of coffee beans you want to add:"),
    ADD_CUPS("Write how many disposable cups you want to add:"),
    TAKE("I gave you $%d\n");

    final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
