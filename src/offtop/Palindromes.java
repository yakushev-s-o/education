package offtop;

public class Palindromes {
    public static boolean isPalindrome(String s) {
        String normalizedText = normalize(s);
        return normalizedText.equals(reverse(normalizedText));
    }

    private static String normalize(String s) {
        return s.toLowerCase().replaceAll("\\W+", "");
    }

    private static String reverse(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    public static void printPalindromes(String s) {
        System.out.println(isPalindrome(s) ? "palindrome" : "not palindrome");
    }

    public static void main(String[] args) {
        printPalindromes("Madam, I’m Adam");
    }
}