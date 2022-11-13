package thinkingInJava.c5_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

public class E22_EnumSwitch {

    enum Money {ONE, FIVE, TEN, TWENTY, FIFTY, HUNDRED}

    public static void main(String[] args) {
        for (Money m : Money.values()) {
            switch (m) {
                case ONE:
                    print("$1");
                    break;
                case FIVE:
                    print("$5");
                    break;
                case TEN:
                    print("$10");
                    break;
                case TWENTY:
                    print("$20");
                    break;
                case FIFTY:
                    print("$50");
                    break;
                case HUNDRED:
                    print("$100");
                    break;
            }
        }
    }
}
