package hyperskill.battleship;

public enum Messages {
    ENTER_AIRCRAFT_CARRIER("Enter the coordinates of the Aircraft Carrier (5 cells):"),
    ENTER_BATTLESHiP("Enter the coordinates of the Battleship (4 cells):"),
    ENTER_SUBMARINE("Enter the coordinates of the Submarine (3 cells):"),
    ENTER_CRUISER("Enter the coordinates of the Cruiser (3 cells):"),
    ENTER_DESTROYER("Enter the coordinates of the Destroyer (2 cells):"),
    ERROR_LENGTH("Error! Wrong length of the %s! Try again:"),
    ERROR_LOCATION("Error! Wrong ship location! Try again:"),
    ERROR_CLOSE("Error! You placed it too close to another one. Try again:");

    String message;

    Messages (String message) {
        this.message = message;
    }
}
