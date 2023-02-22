package hyperskill.numbers;

public enum Messages {

    GREETING("Welcome to Amazing Numbers!"),
    INSTRUCTIONS("""
            Supported requests:
            - enter a natural number to know its properties;
            - enter two natural numbers to obtain the properties of the list:
              * the first parameter represents a starting number;
              * the second parameter shows how many consecutive numbers are to be printed;
            - two natural numbers and a property to search for;
            - two natural numbers and two properties to search for;
            - separate the parameters with one space;
            - enter 0 to exit."""
    ),
    PROMPT("Enter a request: "),
    FIRST_ERROR("The first parameter should be a natural number or zero."),
    SECOND_ERROR("The second parameter should be a natural number."),
    THIRD_ERROR("""
            The property [%s] is wrong.
            Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]
            """),
    FOURTH_ERROR("""
            The properties [%s, %s] are wrong.
            Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY]
            """),
    MUTUALLY_EXCLUSIVE("""
            The request contains mutually exclusive properties: [%s, %s]
            There are no numbers with these properties.
            """),
    GOODBYE("Goodbye!");

    private final String message;

    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
