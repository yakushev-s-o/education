package offtop.hyperskill_manager;

import com.google.gson.*;
import offtop.hyperskill_manager.data.Data;
import offtop.hyperskill_manager.data.Project;
import offtop.hyperskill_manager.data.Step;
import offtop.hyperskill_manager.data.Topic;
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
    public static final String SITE_LINK = "https://hyperskill.org/";
    public static final String JSON_PATH = "src/offtop/hyperskill_manager/files/answer-list.json";
    public static final String DATA_PATH = "src/offtop/hyperskill_manager/files/data-list.json";
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

    // Получить данные трека и записать в файл
    public void getData(int track) {
        Topic topic = getTopics(track);
        List<Project> projects = getProjects(track);
        List<Step> steps = getSteps(topic);

        // Записываем JSON-объекта в файл
        try (FileWriter writer = new FileWriter(DATA_PATH)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            gson.toJson(new Data(topic, projects, steps), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Получаем список топиков
    public Topic getTopics(int track) {
        List<String> listTopic = new ArrayList<>();
        List<String> listDescendants = new ArrayList<>();

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

                    // Проверяем, является ли топик родительским
                    if (obj.get("parent_id").isJsonNull()) {
                        JsonArray descendantsArr = obj.getAsJsonArray("descendants");

                        // Получаем массив дочерних топиков
                        for (JsonElement s : descendantsArr) {
                            listDescendants.add(String.valueOf(s));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new Topic(listTopic, listDescendants);
    }

    // Получаем список проектов
    public List<Project> getProjects(int track) {
        List<Project> projectList = new ArrayList<>();

        String urlTrack = "https://hyperskill.org/api/tracks/" + track + "?format=json";

        // Получаем JSON-объект с данными
        try (InputStream trackInputStream = new URL(urlTrack).openStream()) {
            JsonElement trackJsonElement = JsonParser.parseReader(new InputStreamReader(trackInputStream));
            JsonObject trackJsonObject = trackJsonElement.getAsJsonObject();

            // Получаем массив проектов
            JsonArray trackProjectsArray = trackJsonObject.getAsJsonArray("tracks");

            for (JsonElement projectElement : trackProjectsArray) {
                JsonObject projectObj = projectElement.getAsJsonObject();
                JsonArray projectArray = projectObj.getAsJsonArray("projects");

                for (JsonElement projectName : projectArray) {
                    String urlProject = "https://hyperskill.org/api/projects/" + projectName + "?format=json";

                    // Получаем JSON-объект с данными
                    try (InputStream projectInputStream = new URL(urlProject).openStream()) {
                        JsonElement projectJsonElement = JsonParser.parseReader(new InputStreamReader(projectInputStream));
                        JsonObject projectJsonObject = projectJsonElement.getAsJsonObject();

                        // Получаем массив данных проекта
                        JsonArray projectDataArray = projectJsonObject.getAsJsonArray("projects");

                        for (JsonElement projectElement1 : projectDataArray) {
                            JsonObject projectObj1 = projectElement1.getAsJsonObject();

                            int projectId = projectObj1.get("id").getAsInt();
                            String projectTitle = projectObj1.get("title").getAsString();
                            List<String> stagesIds = new ArrayList<>();

                            for (JsonElement stageId : projectObj1.getAsJsonArray("stages_ids")) {
                                stagesIds.add(String.valueOf(stageId));
                            }

                            projectList.add(new Project(projectId, projectTitle, stagesIds));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return projectList;
    }

    // Получаем список тем и заданий
    public List<Step> getSteps(Topic topics) {
        List<Step> steps = new ArrayList<>();

        for (String topic : topics.getDescendants()) {

            int i = 1;
            boolean isNext = true;

            // Пока есть следующая страница выполняем цикл
            while (isNext) {
                String url = "https://hyperskill.org/api/steps?format=json&topic=" + topic + "&page_size=100&page=" + i++ + "";

                driver.get(url);

                // Получаем содержимое страницы в виде текста
                String pageSource = driver.findElement(By.tagName("pre")).getText();

                // Получаем JSON-объект с данными
                JsonElement jsonElement = JsonParser.parseString(pageSource);
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // Проверяем, есть ли следующая страница с данными
                JsonObject meta = jsonObject.getAsJsonObject("meta");

                if (!meta.get("has_next").getAsBoolean()) {
                    isNext = false;
                }

                int id = 0;
                String title = "";
                List<String> listStepTrue = new ArrayList<>();
                List<String> listStepFalse = new ArrayList<>();

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
                        // Разделяем списки на выполненные и не выполненные
                        if (obj.get("is_completed").getAsBoolean()) {
                            // Если - практика, то добавляем ID практики
                            listStepTrue.add(obj.get("id").getAsString());
                        } else {
                            listStepFalse.add(obj.get("id").getAsString());
                        }
                    }
                }

                steps.add(new Step(id, title, listStepTrue, listStepFalse));
            }
        }

        driver.quit();

        return steps;
    }

    // Получаем список объектов из файла
    public <T> T getFileData(Type type, String path) {
        Gson gson = new Gson();
        File file = new File(path);
        T result = null;

        if (file.exists() && file.length() != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);

                if (jsonElement.isJsonArray()) {
                    // Считываем список объектов
                    result = gson.fromJson(jsonElement, type);
                } else {
                    // Считываем одиночный объект
                    result = gson.fromJson(jsonElement.getAsJsonObject(), type);
                }

                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
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
