package offtop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class ConverterHyperSkill {
    public static void main(String[] args) {
        File folder = new File("C:\\Users\\Admin\\Desktop\\test");
        formatFileNames(Objects.requireNonNull(folder.listFiles()));
        File[] listOfFiles = folder.listFiles();

        for (File file : Objects.requireNonNull(listOfFiles)) {
            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                String link = extractLink(fileContent, 0);
                String clearLink = extractLink(fileContent, 1);
                String newLink = "file:///" + file.getAbsolutePath().replace("\\", "/");
                boolean practice = fileContent.contains("dropdown b-dropdown dd-topic-progress show btn-group");
                replaceStringInFiles(listOfFiles, link, clearLink, newLink, practice);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String extractLink(String input, int key) {
        String[] lines = input.split("\n");
        String urlLine = lines[2];
        int startIndex = urlLine.indexOf("https://");
        int endIndex = urlLine.indexOf("#");
        if (key == 1) {
            if (endIndex == -1) {
                endIndex = urlLine.length() - 1;
            }
            return urlLine.substring(startIndex, endIndex);
        }
        return urlLine.substring(startIndex, urlLine.length() - 1);
    }

    public static void replaceStringInFiles(File[] listOfFiles, String searchString, String clearSearchString, String replaceString, boolean practice) {
        String leftOld = "Theory </a><button data-v-5a34a0c7";
        String rightOld = "Practice </button>";
        String leftNew = "Theory </a><a data-v-5a34a0c7=\"\" href=\"" + replaceString + "\" class=\"btn router-link-exact-active active-route btn-white active\" ";
        String rightNew = "Practice </a>";
        String comment = searchString.substring(searchString.length() - 8);
        String useful = searchString.substring(searchString.length() - 12);

        for (File file : listOfFiles) {
            try {
                String fileContent = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
                String[] lines = fileContent.split("\\r?\\n");
                StringBuilder updatedContent = new StringBuilder();
                String clearLink = extractLink(fileContent, 1);

                int lineNumber = 1;
                for (String line : lines) {
                    if (clearSearchString.equals(clearLink)) {
                        if ("#comment".equals(comment) && lineNumber != 3) {
                            line = line.replaceAll("#comment", replaceString);
                        }
                        if ("#useful_link".equals(useful) && lineNumber != 3) {
                            line = line.replaceAll("#useful_link", replaceString);
                        }
                        if (practice) {
                            line = line.replaceAll(leftOld, leftNew);
                            line = line.replaceAll(rightOld, rightNew);
                        }
                    }
                    if (lineNumber != 3) {
                        line = line.replaceAll(searchString, replaceString);
                    }
                    updatedContent.append(line).append("\n");
                    lineNumber++;
                }
                Files.write(file.toPath(), updatedContent.toString().getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void formatFileNames(File[] listOfFiles) {
        for (File file : listOfFiles) {
            String fileName = file.getName();
            fileName = fileName.replace(" - JetBrains Academy — Learn programming by building your own apps", "");
            fileName = fileName.replace(" – JetBrains Academy — Learn programming by building your own apps", "");
            fileName = fileName.replace(" - ", "_");
            fileName = fileName.replace(" ", "_");
            File newFile = new File(file.getParent(), fileName);
            boolean success = file.renameTo(newFile);
            if (!success) {
                System.out.println("Failed to rename file: " + file.getName());
            }
        }
    }
}

/*
<div data-v-cca8e734="" data-v-fde0ce8e="" class="user-avatar"
data-component-name="UserAvatar"><span data-v-cca8e734="" href="#" target="_self" tabindex="-1"
aria-disabled="true" class="b-avatar disabled badge-primary rounded-circle" data-tooltip-event-part="user"
data-component-name="BAvatar" style="width:32px;height:32px;background-color:rgb(0,0,136);font-size:12.8px">
<span class="b-avatar-custom"> SY </span></span></div>
 */