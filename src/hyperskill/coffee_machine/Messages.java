package hyperskill.coffee_machine;

public enum Messages {
    NEED_COFFEE("For %d cups of coffee you will need:\n");

    final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
