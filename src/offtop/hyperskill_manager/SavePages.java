package offtop.hyperskill_manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class SavePages extends Util{
    // Получаем список проектов
    public List<String> getProjects(int track) {
        List<String> listProject = new ArrayList<>();

        String url = "https://hyperskill.org/api/tracks/" + track + "?format=json";

        // Получаем JSON-объект с данными
        try (InputStream inputStream = new URL(url).openStream()) {
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            // Получаем массив проектов
            JsonArray projects = jsonObject.getAsJsonArray("tracks");

            for (JsonElement project : projects) {
                JsonObject obj = project.getAsJsonObject();
                JsonArray descendantsArr = obj.getAsJsonArray("projects");

                for (JsonElement s : descendantsArr) {
                    listProject.add(String.valueOf(s));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return listProject;
    }

    // Получаем список топиков для карты
    public List<String> getKnowledgeMap(int track) {
        List<String> listTopic = new ArrayList<>();

        int i = 1;
        boolean isNext = true;

        // Пока есть следующая страница выполняем цикл
        while (isNext) {
            String url = "https://hyperskill.org/api/topic-relations?format=json&track_id=" + track + "&page_size=100&page=" + i++ + "";

            // Получаем JSON-объект с данными
            try (InputStream inputStream = new URL(url).openStream()) {
                JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Проверяем, есть ли следующая страница с данными
                JsonObject meta = jsonObject.getAsJsonObject("meta");

                if (!meta.get("has_next").getAsBoolean()) {
                    isNext = false;
                }

                // Получаем массив топиков
                JsonArray topicRelationsArr = jsonObject.getAsJsonArray("topic-relations");

                for (JsonElement element : topicRelationsArr) {
                    JsonObject obj = element.getAsJsonObject();

                    listTopic.add(String.valueOf(obj.get("id")));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("listTopic - " + listTopic);

        return listTopic;
    }

//    public void saveTopics(List<String> listTopic) {
//        for (String topic : listTopic) {
//            if (isFileExists(FOLDER_PATH + TOPIC_LINK, topic)) {
//                driver.get(SITE_LINK + TOPIC_LINK + topic);
//
//                waitDownloadElement("//div[@class='knowledge-map-group']");
//
//                delay(1000);
//
//                save(TOPIC_LINK, topic);
//            }
//        }
//
//        driver.quit();
//    }

    // Получаем список тем
//    public List<String> getSteps(List<String> topics) {
//        List<String> listSteps = new ArrayList<>();
//
//        for (String topic : topics) {
//
//            int i = 1;
//            boolean isNext = true;
//
//            // Пока есть следующая страница выполняем цикл
//            while (isNext) {
//                String url = "https://hyperskill.org/api/steps?format=json&topic=" + topic + "&page_size=100&page=" + i + "";
//
//                // Получаем JSON-объект с данными
//                try (InputStream inputStream = new URL(url).openStream()) {
//                    JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
//                    JsonObject jsonObject = jsonElement.getAsJsonObject();
//
//                    // Проверяем, есть ли следующая страница с данными
//                    JsonObject meta = jsonObject.getAsJsonObject("meta");
//
//                    if (!meta.get("has_next").getAsBoolean()) {
//                        isNext = false;
//                    }
//
//                    // Получаем массив шагов
//                    JsonArray topicRelationsArr = jsonObject.getAsJsonArray("steps");
//
//                    for (JsonElement element : topicRelationsArr) {
//                        JsonObject obj = element.getAsJsonObject();
//
//                        // Проверяем тип шага (теория или практика)
//                        if (obj.get("type").getAsString().equals("theory")) {
//                            listSteps.add(String.valueOf(obj.get("topic_theory").getAsInt()));
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        System.out.println("listSteps - " + listSteps);
//
//        return listSteps;
//    }

//    public void saveSteps(List<String> listStep) {
//        for (String step : listStep) {
//            if (isFileExists(FOLDER_PATH + STEP_LINK, step)) {
//                driver.get(SITE_LINK + STEP_LINK + step);
//
//                waitDownloadElement("//a[@class='text-gray']");
//
//                if (isHideTheory()) {
//                    Actions actions = new Actions(driver);
//                    WebElement element = driver.findElement(By.xpath("//a[@class='ml-3'][text()=' Expand all ']"));
//                    actions.moveToElement(element).click().perform();
//                    waitDownloadElement("//button[@click-event-target='start_praticting']");
//                }
//
//                delay(1000);
//
//                save(STEP_LINK, step);
//            }
//        }
//
//        driver.quit();
//    }

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

//    private void save(String path, String page) {
//        File file = new File(FOLDER_PATH + path);
//
//        // Проверяем наличие папки и создаем при необходимости
//        if (!file.exists()) {
//            boolean created = file.mkdirs();
//            if (!created) {
//                return;
//            }
//        }
//
//        // Получить исходный код страницы
//        String pageSource = driver.getPageSource();
//
//        // Сохранение кода страницы в файл и сохранением кодировки
//        try {
//            String filePath = FOLDER_PATH + path + page + ".html";
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8));
//            writer.write(pageSource);
//            writer.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
