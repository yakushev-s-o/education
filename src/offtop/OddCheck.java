package offtop;

public class OddCheck {
    private static boolean checkSlow(int num) {
        return num % 2 == 1; // Odd (num % 2 == 0 -> Even)
    }

    private static boolean checkFast(int num) {
        return (num & 1) == 1; // Odd ((num & 1) == 0 -> Even)
    }

    public static void main(String[] args) {
        long nTime = System.nanoTime(); // Start timer
        System.out.println(checkSlow(5));
        System.out.println(checkSlow(-5)); // Wrong
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime)/1_000_000.0); // Stop timer
        long nTime2 = System.nanoTime(); // Start timer
        System.out.println(checkFast(5));
        System.out.println(checkFast(-5));
        System.out.printf("Time -> %,1.3f ms\n", (System.nanoTime() - nTime2)/1_000_000.0); // Stop timer
    }
}

/*
true
false
Time -> 0,240 ms
true
true
Time -> 0,038 ms
 */