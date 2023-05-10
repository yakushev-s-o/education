package offtop.hyperskill_manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static WebDriver driver;
    private static final String CHROMEDRIVER_PATH = "C:/tools/chromedriver_win32/chromedriver.exe";
    public static final String SITE_LINK = "https://hyperskill.org/learn/step/";
    public static final String JSON_PATH = "src/offtop/hyperskill_manager/answer-list.json";
    public static final String DATA_PATH = "src/offtop/hyperskill_manager/data-list.json";
    public static final String TOPIC_LINK = "knowledge-map/";
    public static final String STEP_LINK = "learn/step/";

    public void createDriver(boolean hide) {
        // Устанавливаем путь к драйверу браузера
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);
        ChromeOptions options = new ChromeOptions();

        // Создаем экземпляр драйвера в фоне если true
        if (hide) {
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            driver = new ChromeDriver(options);
        } else {
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
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

    // Получаем список топиков
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

        return listTopic;
    }

    // Получаем список тем и заданий и сохраняем в файл
    public void getSteps(List<String> topics) {
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

                    int id = 0;
                    String title = "";
                    List<String> listStep = new ArrayList<>();

                    // Получаем массив шагов
                    JsonArray topicRelationsArr = jsonObject.getAsJsonArray("steps");

                    for (JsonElement element : topicRelationsArr) {
                        JsonObject obj = element.getAsJsonObject();

                        // Проверяем тип шага (теория или практика)
                        if (obj.get("type").getAsString().equals("theory")) {
                            // Если тип - теория, то получаем ID теории и название
                            id = obj.get("topic_theory").getAsInt();
                            title = obj.get("title").getAsString();
                        } else if (obj.get("type").getAsString().equals("practice")) {
                            // Если - практика, то добавляем ID практики
                            listStep.add(obj.get("id").getAsString());
                        }
                    }

                    // Получаем текущий список шагов из файла
                    List<Data> listData = getFileData(new TypeToken<List<Data>>() {
                    }.getType(), DATA_PATH);
                    // Добавляем новый шаг в список и записываем в файл
                    saveToFile(new Data(id, title, listStep), listData, DATA_PATH);

                    System.out.println(id);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Получаем список объектов из файла
    public <T> List<T> getFileData(Type type, String path) {
        Gson gson = new Gson();
        File file = new File(path);
        List<T> list = new ArrayList<>();

        // Проверяем, существует ли заполненный файл, и если да, то загружаем его содержимое в память
        if (file.exists() && file.length() != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                list = gson.fromJson(reader, type);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return list;
    }

    // Сохраняем объект в файл в формате JSON
    public <T> void saveToFile(T answer, List<T> list, String path) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(path);

        // Добавляем новые данные к уже существующим в памяти
        list.add(answer);

        // Записываем обновленные данные в файл
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(list, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Проверяем состояние загрузки страницы
    public boolean waitDownloadElement(String xpath) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.and(
                ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)),
                ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)),
                ExpectedConditions.elementToBeClickable(By.xpath(xpath))
        ));
    }

    // Задержка между переходами
    public void delay(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
