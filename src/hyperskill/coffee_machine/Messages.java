package hyperskill.coffee_machine;

public enum Messages {
    RESOURCE("""
            The coffee machine has:
            %d ml of water
            %d ml of milk
            %d g of coffee beans
            %d disposable cups
            $%d of money
            """),
    ACTION("Write action (buy, fill, take, remaining, exit):"),
    BUY("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino:"),
    ADD_WATER("Write how many ml of water you want to add:"),
    ADD_MILK("Write how many ml of milk you want to add:"),
    ADD_COFFEE("Write how many grams of coffee beans you want to add:"),
    ADD_CUPS("Write how many disposable cups you want to add:"),
    TAKE("I gave you $%d\n"),
    ENOUGH_RESOURCE("I have enough resources, making you a coffee!"),
    NO_ENOUGH_RESOURCE("Sorry, not enough %s!\n");

    final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
