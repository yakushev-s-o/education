package offtop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperSkillOffline {

    private static final String FOLDER_PATH = "C:/Users/Admin/Desktop/test";

    public static void main(String[] args) {
        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileContent = readFile(file);
                boolean practice = fileContent.contains("dropdown b-dropdown dd-topic-progress show btn-group");
                String link = findFirstLink(fileContent, practice);
                String newFileName = createNewFileName(link);
                String newFilePath = createNewFilePath(link);
                fileContent = replaceLinks(fileContent);
                fileContent = replaceOther(fileContent, newFilePath);
                writeFile(file, fileContent);
                File newFile = renameFile(file, newFileName);
                moveFile(newFile, newFilePath);
            }
        }
    }

    private static String readFile(File file) {
        try {
            return new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String findFirstLink(String fileContent, boolean practice) {
        Matcher matcher = Pattern.compile("https?://\\S+").matcher(fileContent);
        if (matcher.find()) {
            String link = matcher.group();
            if (practice) {
                link += "practice";
            }
            return link.replaceAll("[?#]", "");
        }
        return "";
    }

    private static String createNewFileName(String link) {
        String[] parts = link.split("/");
        return parts[parts.length - 1] + ".html";
    }

    private static String createNewFilePath(String link) {
        String[] parts = link.split("/");
        StringBuilder path = new StringBuilder(FOLDER_PATH);
        for (int i = 3; i < parts.length - 1; i++) {
            path.append("/").append(parts[i]);
            createFolder(path.toString());
        }
        return path.append("/").append(parts[parts.length - 1]) + ".html";
    }

    public static String replaceLinks(String fileContent) {
        String regex = "https://[\\w+-=.#?/]+\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);
        StringBuilder sb = new StringBuilder();
        while (matcher.find()) {
            String link = matcher.group();
            String newLink = link.replace("https://hyperskill.org", FOLDER_PATH);
            if (!newLink.matches(".*\\.[a-zA-Z]+$") && link.contains("https://hyperskill.org")) {
                newLink += ".html";
            }
            matcher.appendReplacement(sb, newLink);
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceOther(String fileContent, String newFilePath) {
        String condition = "hint.html|practice.html|useful_link.html|comment.html|.html";
        String leftOld = "Theory </a><button data-v-5a34a0c7";
        String rightOld = "Practice </button>";
        String leftNew = "Theory </a><a data-v-5a34a0c7=\"\" href=\"" +
                newFilePath.replaceAll(condition, "practice.html") + "\" ";
        String rightNew = "Practice </a>";

        return fileContent.replace("href=#comment", "href=" +
                        newFilePath.replaceAll(condition, "comment.html"))
                .replace("href=#useful_link", "href=" +
                        newFilePath.replaceAll(condition, "useful_link.html"))
                .replace("href=#hint", "href=" +
                        newFilePath.replaceAll(condition, "hint.html"))
                .replace(leftOld, leftNew).replace(rightOld, rightNew)
                .replace("?track=", "track=")
                .replaceAll("Sergey Yakushev|> SY <", "");
    }

    private static void writeFile(File file, String fileContent) {
        try {
            Files.write(file.toPath(), fileContent.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static File renameFile(File file, String newName) {
        Path newPath = Paths.get(file.getParent(), newName);
        try {
            Files.move(file.toPath(), newPath, StandardCopyOption.REPLACE_EXISTING);
            return new File(newPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return file;
        }
    }

    private static void moveFile(File originalFile, String newPath) {
        try {
            File newFile = new File(newPath);
            Path source = originalFile.toPath();
            Path target = newFile.toPath();
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createFolder(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                System.err.println("Failed to create directory: " + path);
            }
        }
    }
}