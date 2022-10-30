package thinkingInJava.c4_initialization_and_cleanup;

public class E21_Enum {

    enum Money {ONE, FIVE, TEN, TWENTY, FIFTY, HUNDRED}

    public static void main(String[] args) {
        for(Money m : Money.values()) {
            System.out.println(m + " - " + m.ordinal());
        }
    }

}
