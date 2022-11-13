package thinkingInJava.с3_operators;

import java.util.Random;

public class CoinFlip {
    public static void main(String[] args) {
        Random rnd = new Random();
        System.out.println(rnd.nextBoolean() ? "Орел" : "Решка");
    }
}
