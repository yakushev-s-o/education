package offtop.hyperskill;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HyperSkillOnline {
    public static void main(String[] args) throws InterruptedException, IOException {
        // Устанавливаем путь к драйверу браузера
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");
        // Создаем экземпляр драйвера
        WebDriver driver = new ChromeDriver();

        // Выполняем авторизацию
        login(driver);

        // Переходим на страницу теста
        driver.get("https://hyperskill.org/learn/step/15238");

        // Выбираем первый ответ в тесте
        sendTest(driver);

        // Переходим на страницу теста
        driver.get("http://91.217.76.232/learn/step/15238");

        // Получаем список правильных ответов и сохраняем его в файл
        List<String> correctAnswers = getTest(driver);
        String fileName = "src/offtop/hyperskill/" + "correct-answers.json";
        List<Answers> testDataList = new ArrayList<>();
        testDataList.add(new Answers("https://hyperskill.org/learn/step/15238", correctAnswers)); // 1 ответ
//        testDataList.add(new Answers("http://91.217.76.232/learn/step/1982", correctAnswers)); // 2 ответа
//        testDataList.add(new Answers("http://91.217.76.232/learn/step/3412", correctAnswers)); // ответ с текстом
//        testDataList.add(new Answers("http://91.217.76.232/learn/step/2165", correctAnswers)); // ответ с кодом
//        testDataList.add(new Answers("http://91.217.76.232/learn/step/2499", correctAnswers)); // ответ с перестановкой
//        testDataList.add(new Answers("http://91.217.76.232/learn/step/2123", correctAnswers)); // ответ с матрицей
        saveCorrectAnswersToFile(fileName, testDataList);

        // Закрываем браузер
        driver.quit();
    }

    // Метод для авторизации на сайте
    private static void login(WebDriver driver) throws InterruptedException {
        driver.get("https://hyperskill.org/login");

        if (checkDownload(driver, "//input[@type='email']")) {
            WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
            WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
            WebElement signInButton = driver.findElement(By.cssSelector("button[data-cy='submitButton']"));

            // Вводим логин и пароль и нажимаем кнопку входа
            emailField.sendKeys("yakushev.s.o@ya.ru");
            passwordField.sendKeys("{yx#e%B9~SGl4@Cr");
            signInButton.click();
        }

        Thread.sleep(5000);
    }

    // Метод для получения ответа из текстового поля
    private static String getText(WebDriver driver) {
        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
        return input.getAttribute("value");
    }

    // Метод для записи ответа в текстовое поле
    private static void sendText(WebDriver driver) {
        Actions actions = new Actions(driver);
        WebElement radioBtn = driver.findElement(By.xpath("//input[@placeholder='Type your answer here...']"));
        actions.moveToElement(radioBtn).click().sendKeys("123").perform();
    }

    // Метод для получения ответа из поля с кодом
    private static String getCode(WebDriver driver) {
        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        return input.getText();
    }

    // Метод для записи ответа в поле с кодом
    private static void sendCode(WebDriver driver) {
        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        input.clear();
        input.sendKeys("123");
    }

    // Метод для получения списка правильных ответов из теста
    private static List<String> getTest(WebDriver driver) {
        List<String> correctAnswers = null;
        if (checkDownload(driver, "//div[@class='submission submission-correct']")) {
            String pageSource = driver.getPageSource();
            Document doc = Jsoup.parse(pageSource);

            correctAnswers = new ArrayList<>();
            Elements checkedInputs = doc.select("input[type=radio][checked], input[type=checkbox][checked]");
            for (Element checkedInput : checkedInputs) {
                String value = checkedInput.attr("value");
                correctAnswers.add(value);
            }
        }

        return correctAnswers;
    }

    // Метод для выбора ответа в тесте
    private static void sendTest(WebDriver driver) {
        if (checkDownload(driver, "//div[@class='submission']")) {
            Actions actions = new Actions(driver);
            WebElement radioBtn = driver.findElement(By.cssSelector("input[type='radio'][value='1']"));
            actions.moveToElement(radioBtn).click().perform();
            WebElement signInButton = driver.findElement(By.cssSelector("button[id='sendBtn']"));
//        signInButton.click();
        }
    }

    // Метод для сохранения списка правильных ответов в файл в формате JSON
    private static void saveCorrectAnswersToFile(String fileName, List<Answers> testDataList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Answers> existingData = new ArrayList<>();

        // Проверяем, существует ли файл, и если да, то загружаем его содержимое в память
        File file = new File(fileName);
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Type listType = new TypeToken<List<Answers>>() {
            }.getType();
            existingData = gson.fromJson(reader, listType);
            reader.close();
        }

        // Добавляем новые данные к уже существующим данным в памяти
        existingData.addAll(testDataList);

        // Записываем обновленные данные в файл
        FileWriter writer = new FileWriter(file);
        gson.toJson(existingData, writer);
        writer.close();
    }

    // Метод для проверки состояния загрузки страницы
    private static boolean checkDownload(WebDriver driver, String s) {
        Duration timeout = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s)));

        return element.isDisplayed();
    }
}

class Answers {
    String url;
    List<String> answers;

    public Answers(String url, List<String> answers) {
        this.url = url;
        this.answers = answers;
    }
}
