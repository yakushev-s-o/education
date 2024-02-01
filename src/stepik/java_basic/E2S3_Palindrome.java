package stepik.java_basic;

public class E2S3_Palindrome {
    /**
     * Checks if given <code>text</code> is a palindrome.
     *
     * @param text any string
     * @return <code>true</code> when <code>text</code> is a palindrome, <code>false</code> otherwise
     */
    public static boolean isPalindrome(String text) {
        String str = text.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        StringBuilder stringBuilder = new StringBuilder(str);
        String strNew = stringBuilder.reverse().toString().toLowerCase();

        return strNew.equals(str);
    }
}
