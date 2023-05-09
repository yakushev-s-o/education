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

public class SavePages {
    private static WebDriver driver;
    private static final String CHROMEDRIVER_PATH = "C:\\tools\\chromedriver_win32\\chromedriver.exe";
    private static final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/";
    private static final String SITE_LINK = "https://hyperskill.org/";
    private static final String TOPIC_LINK = "knowledge-map/";
    private static final String STEP_LINK = "learn/step/";

    public void createDriver() {
        // Устанавливаем путь к драйверу браузера
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);

        // Создаем экземпляр драйвера
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    // Выполняем авторизацию на сайте
    public void login() {
        driver.get("https://hyperskill.org/login");

        waitDownloadElement("//input[@type='email']");

        WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
        WebElement signInButton = driver.findElement(By.xpath("//button[@data-cy='submitButton']"));

        emailField.sendKeys("yakushev.s.o@ya.ru");
        passwordField.sendKeys("{yx#e%B9~SGl4@Cr");
        signInButton.click();

        waitDownloadElement("//h1[@data-cy='curriculum-header']");
    }

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

    // Получаем список топиков для шагов
    public List<String> getTopics(int track) {
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

                    // Проверяем, является ли топик родительским
                    if (obj.get("parent_id").isJsonNull()) {
                        JsonArray descendantsArr = obj.getAsJsonArray("descendants");

                        // Получаем массив дочерних топиков
                        for (JsonElement s : descendantsArr) {
                            listTopic.add(String.valueOf(s));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("listTopic - " + listTopic);

        return listTopic;
    }

    public void saveTopics(List<String> listTopic) {
        for (String topic : listTopic) {
            if (isFileExists(FOLDER_PATH + TOPIC_LINK, topic)) {
                driver.get(SITE_LINK + TOPIC_LINK + topic);

                waitDownloadElement("//div[@class='knowledge-map-group']");

                delay();

                save(TOPIC_LINK, topic);
            }
        }

        driver.quit();
    }

    // Получаем список тем
    public List<String> getSteps(List<String> topics) {
        List<String> listSteps = new ArrayList<>();

        for (String topic : topics) {

            int i = 1;
            boolean isNext = true;

            // Пока есть следующая страница выполняем цикл
            while (isNext) {
                String url = "https://hyperskill.org/api/steps?format=json&topic=" + topic + "&page_size=100&page=" + i + "";

                // Получаем JSON-объект с данными
                try (InputStream inputStream = new URL(url).openStream()) {
                    JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    // Проверяем, есть ли следующая страница с данными
                    JsonObject meta = jsonObject.getAsJsonObject("meta");

                    if (!meta.get("has_next").getAsBoolean()) {
                        isNext = false;
                    }

                    // Получаем массив шагов
                    JsonArray topicRelationsArr = jsonObject.getAsJsonArray("steps");

                    for (JsonElement element : topicRelationsArr) {
                        JsonObject obj = element.getAsJsonObject();

                        // Проверяем тип шага (теория или практика)
                        if (obj.get("type").getAsString().equals("theory")) {
                            listSteps.add(String.valueOf(obj.get("topic_theory").getAsInt()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("listSteps - " + listSteps);

        return listSteps;
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

                delay();

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

    // Проверяем состояние загрузки страницы
    private void waitDownloadElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.and(
                ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)),
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)),
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        ));
    }

    // Задержка между переходами
    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
