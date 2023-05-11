package offtop.hyperskill_manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class SavePages extends Util {
    private final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/";

    public void saveTopics(List<String> listTopic) {
        for (String topic : listTopic) {
            if (isFileExists(FOLDER_PATH + TOPIC_LINK, topic)) {
                driver.get(SITE_LINK + TOPIC_LINK + topic);

                waitDownloadElement("//div[@class='knowledge-map-group']");

                delay(1000);

                save(TOPIC_LINK, topic);
            }
        }

        driver.quit();
    }

    public void saveSteps(List<String> listStep) {
        for (String step : listStep) {
            if (isFileExists(FOLDER_PATH + STEP_LINK, step)) {
                driver.get(SITE_LINK + STEP_LINK + step);

                waitDownloadElement("//a[@class='text-gray']");

                if (isHideTheory()) {
                    Actions actions = new Actions(driver);
                    WebElement element = driver.findElement(By.xpath("//a[@class='ml-3'][text()=' Expand all ']"));
                    actions.moveToElement(element).click().perform();
                    waitDownloadElement("//button[@click-event-target='start_praticting']");
                }

                delay(1000);

                save(STEP_LINK, step);
            }
        }

        driver.quit();
    }

    private boolean isFileExists(String path, String fileName) {
        File dir = new File(path);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().replace(".html", "").equals(fileName)) {
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

    private void save(String path, String page) {
        File file = new File(FOLDER_PATH + path);

        // Проверяем наличие папки и создаем при необходимости
        if (!file.exists()) {
            boolean created = file.mkdirs();
            if (!created) {
                return;
            }
        }

        // Получить исходный код страницы
        String pageSource = driver.getPageSource();

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
