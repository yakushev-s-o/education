package offtop;

import java.util.Arrays;

public class Anagrams {
    public static boolean areAnagrams(String a, String b) {
        char[] charsFromA = getSortedChars(a);
        char[] charsFromB = getSortedChars(b);
        return Arrays.equals(charsFromA, charsFromB);
    }

    private static char[] getSortedChars(String s) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        return chars;
    }

    public static void printAnagrams(String a, String b) {
        System.out.println(areAnagrams(a, b) ? "anagrams" : "not anagrams");
    }

    public static void main(String[] args) {
        printAnagrams("silent", "listen");
    }
}
