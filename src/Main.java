import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File folder = new File("C:\\Users\\Admin\\Desktop\\test");

        formatFileNames(Objects.requireNonNull(folder.listFiles()));

        File[] listOfFiles = folder.listFiles();
        for (File file : Objects.requireNonNull(listOfFiles)) {
            if (file.isFile() && file.getName().endsWith(".html")) {
                try {
                    String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                    String link = getLinkFromString(fileContent);
                    boolean practice = checkPractice(fileContent);
                    replaceStringInFiles(listOfFiles, link, file.getAbsolutePath().replace("\\", "/"), practice);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getLinkFromString(String content) {
        try (Scanner scanner = new Scanner(content)) {
            for (int i = 0; i < 2; i++) {
                scanner.nextLine();
            }
            String secondLine = scanner.nextLine();
            int index = secondLine.indexOf("url: http");
            if (index != -1) {
                return secondLine.substring(index + 5, secondLine.length() - 1);
            }
        }
        return "No link found";
    }

    public static boolean checkPractice(String content) {
        try (Scanner scanner = new Scanner(content)) {
            for (int i = 0; i < content.length(); i++) {
                int index = scanner.nextLine().indexOf("url: http");
                if (index != -1) {
                    return content.contains("dropdown b-dropdown dd-topic-progress show btn-group");
                }
            }

        }
        return false;
    }

    public static void replaceStringInFiles(File[] listOfFiles, String searchString, String replaceString, boolean practice) {
        String leftOld = "Theory </a><button data-v-5a34a0c7";
        String rightOld = "Practice </button>";
        String leftNew = "Theory </a><a data-v-5a34a0c7=\"\" href=\"file:///" + replaceString + "\" class=\"btn router-link-exact-active active-route btn-white active\" ";
        String rightNew = "Practice </a>";
        String comment = searchString.substring(searchString.length() - 8);
        String useful = searchString.substring(searchString.length() - 12);
        String searchComment = searchString.substring(0, searchString.length() - 8);
        String searchUseful = searchString.substring(0, searchString.length() - 12);

        try {
            for (File file : listOfFiles) {
                String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                String[] lines = fileContent.split("\\r?\\n");
                StringBuilder updatedContent = new StringBuilder();

                String link = getLinkFromString(fileContent);
                String linkComment = link.substring(0, link.length() - 8);
                String linkUseful = link.substring(0, link.length() - 12);

                int lineNumber = 1;
                for (String line : lines) {
                    if (searchString.equals(link) || searchString.equals(linkComment) || searchString.equals(linkUseful) ||
                            searchComment.equals(link) || searchUseful.equals(link)) {
                        if ("#comment".equals(comment)) {
                            line = line.replaceAll("#comment", "file:///" + replaceString);
                        }
                        if ("#useful_link".equals(useful)) {
                            line = line.replaceAll("#useful_link", "file:///" + replaceString);
                        }
                        if (practice) {
                            line = line.replaceAll(leftOld, leftNew);
                            line = line.replaceAll(rightOld, rightNew);
                        }
                    }
                    if (lineNumber != 3) {
                        line = line.replaceAll(searchString, "file:///" + replaceString);
                    }
                    updatedContent.append(line).append("\n");
                    lineNumber++;
                }
                Files.write(file.toPath(), updatedContent.toString().getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void formatFileNames(File[] listOfFiles) {
        for (File file : listOfFiles) {
            String fileName = file.getName();
            fileName = fileName.replace("JetBrains Academy", "");
            fileName = fileName.replace("Learn programming by building your own apps", "");
            fileName = fileName.replace(" — ", "");
            fileName = fileName.replace(" - ", "");
            fileName = fileName.replace(" – ", "");
            fileName = fileName.replace(" ", "_");
            File newFile = new File(file.getParent(), fileName);
            boolean success = file.renameTo(newFile);
            if (!success) {
                System.out.println("Failed to rename file: " + file.getName());
            }
        }
    }
}