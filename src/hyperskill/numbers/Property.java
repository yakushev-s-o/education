package hyperskill.numbers;

public enum Property {
    EVEN(1),
    ODD(1),
    DUCK(2),
    SPY(2),
    SQUARE(3),
    SUNNY(3),
    HAPPY(4),
    SAD(4),
    BUZZ(5),
    PALINDROMIC(6),
    GAPFUL(7),
    JUMPING(8);

    int code;

    Property(int code) {
        this.code = code;
    }
    public void  changeValue() {
        this.code -= this.code + this.code;
    }
}
