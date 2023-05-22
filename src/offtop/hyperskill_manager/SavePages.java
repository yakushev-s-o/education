package offtop.hyperskill_manager;

import offtop.hyperskill_manager.data.Data;
import offtop.hyperskill_manager.data.Project;
import offtop.hyperskill_manager.data.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SavePages extends Util {
    private final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/";

    // Сохраняем страницы с топиками
    public void saveTopics() {
        // Получаем данные из файла
        Data data = getFileData(Data.class, DATA_PATH);

        for (String topic : data.getTopic_relations().getTopics()) {
            // Проверяем, что файл существует
            if (isFileExists(FOLDER_PATH + "knowledge-map/", topic)) {
                driver.get(SITE_LINK + "knowledge-map/" + topic);

                waitDownloadElement("//ol[@class='breadcrumb mb-4']");

                delay(1000);

                save("knowledge-map/", topic);
            }
        }

        driver.quit();
    }

    // Сохраняем страницы с проектами
    public void saveProjects() {
        // Получаем данные из файла
        Data data = getFileData(Data.class, DATA_PATH);

        // Сохраняем главные страницы
        for (Project project : data.getProjects()) {
            if (isFileExists(FOLDER_PATH + "projects/", String.valueOf(project.getId()))) {
                driver.get(SITE_LINK + "projects/" + project.getId());

                waitDownloadElement("//a[@click-event-target='back_to_projects']");

                delay(1000);

                save("projects/", String.valueOf(project.getId()));
            }
        }

        // Сохраняем этапы проектов
        for (Project project : data.getProjects()) {
            for (String stages : project.getStages_ids()) {
                if (isFileExists(FOLDER_PATH + "projects/" + project.getId() + "/stages/" + stages, "implement")) {
                    driver.get(SITE_LINK + "projects/" + project.getId() + "/stages/" + stages + "/implement");

                    waitDownloadElement("//div[@class='tabs']");

                    delay(1000);

                    save("projects/" + project.getId() + "/stages/" + stages + "/", "implement");
                }
            }
        }

        driver.quit();
    }

    // Сохраняем страницы с темами
    public void saveSteps() {
        // Получаем данные из файла
        Data data = getFileData(Data.class, DATA_PATH);

        for (Step steps : data.getSteps()) {
            if (isFileExists(FOLDER_PATH + "learn/step/", String.valueOf(steps.getId()))) {
                driver.get(SITE_LINK + "learn/step/" + steps.getId());

                waitDownloadElement("//a[@class='text-gray']");

                // Проверяем что теория раскрыта
                if (isHideTheory()) {
                    Actions actions = new Actions(driver);
                    WebElement element = driver.findElement(By.xpath("//a[@class='ml-3'][text()=' Expand all ']"));
                    actions.moveToElement(element).click().perform();
                    waitDownloadElement("//button[@click-event-target='start_praticting']");
                }

                delay(1000);

                save("learn/step/", String.valueOf(steps.getId()));
            }
        }

        driver.quit();
    }

    // Проверяем, что файл существуем
    private boolean isFileExists(String path, String fileName) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().equals(fileName + ".html")) {
                    return false;
                }
            }
        }

        return true;
    }

    // Проверяем что страница полностью раскрылась
    private boolean isHideTheory() {
        try {
            driver.findElement(By.xpath("//a[@class='ml-3'][text()=' Expand all ']"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Сохранение страниц
    private void save(String path, String page) {
        File file = new File(FOLDER_PATH + path);

        // Проверяем наличие папки и создаем при необходимости
        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (!created) {
                return;
            }
        }

        // Получение HTML и CSS кода страницы
        String pageSource = (String) ((JavascriptExecutor) driver).executeScript(
                "var html = new XMLSerializer().serializeToString(document.doctype) + document.documentElement.outerHTML;" +
                        "var css = Array.from(document.styleSheets).reduce((cssCode, styleSheet) => {" +
                        "   try {" +
                        "       Array.from(styleSheet.cssRules).forEach(rule => {" +
                        "           cssCode += rule.cssText + '\\n';" +
                        "       });" +
                        "   } catch(error) {" +
                        "       console.warn('Failed to read CSS rules:', error);" +
                        "   }" +
                        "   return cssCode;" +
                        "}, '');" +
                        "return html + '\\n\\n<style>\\n' + css + '</style>';"
        );

        // Сохранение кода страницы в файл и сохранением кодировки
        try {
            String filePath = FOLDER_PATH + path + page + ".html";
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
            writer.write(pageSource);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
