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
                    replaceStringInFiles(listOfFiles, link, file.getAbsolutePath().replace("\\", "/"));
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

    public static void replaceStringInFiles(File[] listOfFiles, String searchString, String replaceString) {
        try {
            for (File file : listOfFiles) {
                String fileContent = new String(Files.readAllBytes(file.toPath()));
                String[] lines = fileContent.split("\\r?\\n");
                StringBuilder updatedContent = new StringBuilder();

                String link = getLinkFromString(fileContent);

                int lineNumber = 1;
                for (String line : lines) {
                    String searchStringComment = searchString.substring(0, searchString.length() - 8);
                    String linkUsefulLinks = link.substring(0, link.length() - 12);

                    if (searchStringComment.equals(link) || searchStringComment.equals(linkUsefulLinks)) {
                        line = line.replaceAll("#comment", "file:///" + replaceString);
                        line = line.replaceAll("#useful_link", "file:///" + replaceString);
                    } else if (lineNumber != 3) {
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