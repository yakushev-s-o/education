import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileProcessor {

    private static final String FOLDER_PATH = "C:/Users/Admin/Desktop/test"; // replace with the actual folder path
    private static final String REGEX_LINK = "https?://[^\\s]+";

    public static void main(String[] args) {
        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                String fileContent = readFile(file);
                String link = findFirstLink(fileContent);
                String newFileName = createNewFileName(link);
                String newFilePath = createNewFilePath(link);
                fileContent = replaceLinks(fileContent);
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

    private static String findFirstLink(String fileContent) {
        Matcher matcher = Pattern.compile(REGEX_LINK).matcher(fileContent);
        if (matcher.find()) {
            return matcher.group();
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
            path.append("\\").append(parts[i]);
            createFolder(path.toString());
        }
        return path.append("\\").append(parts[parts.length - 1]) + ".html";
    }

    public static String replaceLinks(String input) {
        String localRoot = "C:/Users/Admin/Desktop/test";
        return input.replaceAll("https://hyperskill.org([\\w-/]+)(\\s|>)", localRoot + "$2.html");
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
            return new File(newPath.
                    toString());
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
            if(!dir.mkdirs()) {
                System.err.println("Failed to create directory: " + path);
            }
        }
    }

}