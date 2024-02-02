package stepik.java_basic;

public class E2S4_PrintTextPerRole {
    /**
     * Groups rows by roles, numbers them, and returns the result as text.
     *
     * @param roles any text
     * @param textLines any text
     * @return sorted text by roles
     */
    public static String printTextPerRole(String[] roles, String[] textLines) {
        StringBuilder formatScript = new StringBuilder();

        for (String role : roles) {
            formatScript.append(String.format("%s:\n", role));

            for (int j = 0; j < textLines.length; j++) {
                if (textLines[j].startsWith(role + ":")) {
                    formatScript.append(String.format("%d)%s\n", j + 1,
                            textLines[j].substring(role.length() + 1)));
                }
            }

            formatScript.append("\n");
        }

        return formatScript.toString();
    }
}
