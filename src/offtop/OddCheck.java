package offtop;

public class OddCheck {
    private static boolean checkSlow(int num) {
        return num % 2 == 1; // Odd (num % 2 == 0 -> Even)
    }

    private static boolean checkFast(int num) {
        return (num & 1) == 0; // Odd ((num & 1) == 0 -> Even)
    }

    public static void main(String[] args) {
        System.out.println(checkSlow(5));
        System.out.println(checkSlow(-5)); // Wrong
        System.out.println(checkFast(5));
        System.out.println(checkFast(-5));
    }
}
