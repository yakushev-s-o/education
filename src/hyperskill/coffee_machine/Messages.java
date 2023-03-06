package hyperskill.coffee_machine;

public enum Messages {
    INSTRUCTIONS("""
            Starting to make a coffee
            Grinding coffee beans
            Boiling water
            Mixing boiled water with crushed coffee beans
            Pouring coffee into the cup
            Pouring some milk into the cup
            Coffee is ready!"""),
    HOW_NEED_COFFEE("Write how many cups of coffee you will need:"),
    HOW_MANY_WATER("Write how many ml of water the coffee machine has:"),
    HOW_MANY_MILK("Write how many ml of milk the coffee machine has:"),
    HOW_MANY_COFFEE("Write how many grams of coffee beans the coffee machine has:"),
    I_CAN_MAKE("Yes, I can make that amount of coffee"),
    I_CAN_MAKE_MORE("Yes, I can make that amount of coffee (and even %d more than that)"),
    I_CAN_NOT_MAKE("No, I can make only %d cup(s) of coffee");

    final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
