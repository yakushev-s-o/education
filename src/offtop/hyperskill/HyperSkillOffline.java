package offtop.hyperskill;

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
//    private static final String NEW_FOLDER_PATH = "C:/Users/Admin/Desktop/res";

    public static void main(String[] args) {
        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileContent = readFile(file);
                boolean checkPractice = fileContent.contains("dropdown b-dropdown dd-topic-progress show btn-group");
                String link = findFirstLink(fileContent, checkPractice);
                String newFileName = createNewFileName(link);
                String newFilePath = createNewFilePath(link);
                fileContent = replaceLinks(fileContent);
                fileContent = replaceOther(fileContent, link, newFilePath.replace(FOLDER_PATH, ""));
//                fileContent = replaceOther(fileContent, link, newFilePath);
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
            return link.replaceAll("\\?track=\\d+", "").replaceAll("[?#]", "");
        }
        return "";
    }

    private static String createNewFileName(String link) {
        String[] parts = link.split("/");
        return parts[parts.length - 1] + ".html";
    }

    private static String createNewFilePath(String link) {
        String[] parts = link.split("/");
//        StringBuilder path = new StringBuilder(NEW_FOLDER_PATH);
        StringBuilder path = new StringBuilder(FOLDER_PATH);
        for (int i = 3; i < parts.length - 1; i++) {
            path.append("/").append(parts[i]);
            createFolder(path.toString());
        }
        return path.append("/").append(parts[parts.length - 1]) + ".html";
    }

    public static String replaceLinks(String fileContent) {
        String regex = "https://[\\w+-=.#?/]+\\b"; // searches for all links in a file
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileContent);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (matcher.find()) {
            if (i != 0) { // skip first match
                String link = matcher.group();
                String newLink = link.replace("https://hyperskill.org", "");
//                String newLink = link.replace("https://hyperskill.org", NEW_FOLDER_PATH);
                if (!newLink.matches(".*\\.[a-zA-Z]+$") && link.contains("https://hyperskill.org")) { // if the link is without extension
                    newLink += ".html";
                }
                matcher.appendReplacement(sb, newLink);
            }
            i++;
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String replaceOther(String fileContent, String link, String newFilePath) {
        String data = "saved date: \\w{3} \\w{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2} GMT[+\\-]\\d{4} \\([^()]*\\)";
        String condition = "solutions.html|hint.html|practice.html|useful_link.html|comment.html|.html";

        String practiceLeftOld = "Theory </a><button";
        String practiceLeftNew = "Theory </a><a";
        String practiceRightOld = "> Practice </button>";
        String practiceRightNew = " href=" + newFilePath.replaceAll(condition, "practice.html") + "> Practice </a>";

        String avatarOld = "# target=_self class=\"nav-link dropdown-toggle text-decoration-none d-flex align-items-center py-0\"";
//        String avatarNew = NEW_FOLDER_PATH + "/profile/370575247.html" + avatarOld.substring(1);
        String avatarNew = "/profile/370575247.html" + avatarOld.substring(1);

        Pattern pattern = Pattern.compile(data);

        return pattern.matcher(fileContent).replaceAll("info: " + link)
                .replace("href=#comment", "href=" +
                        newFilePath.replaceAll(condition, "comment.html"))
                .replace("href=#useful_link", "href=" +
                        newFilePath.replaceAll(condition, "useful_link.html"))
                .replace("href=#hint", "href=" +
                        newFilePath.replaceAll(condition, "hint.html"))
                .replace("href=#solutions", "href=" +
                        newFilePath.replaceAll(condition, "solutions.html"))
                .replace(practiceLeftOld, practiceLeftNew).replace(practiceRightOld, practiceRightNew)
                .replace(avatarOld, avatarNew)
                .replaceAll("\\?track=\\d+", "")
                .replace("Sergey Yakushev", "Admin Admin")
                .replace("> SY <", "> AA <");
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