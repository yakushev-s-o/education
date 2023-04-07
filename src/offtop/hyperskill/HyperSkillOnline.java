package offtop.hyperskill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HyperSkillOnline {
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

//        login(driver);
//        Thread.sleep(10000);

        goToTest(driver);
        Thread.sleep(10000);

//        selectFirstAnswer(driver);
//        Thread.sleep(10000);

        List<String> correctAnswers = getCorrectAnswers(driver);
        String testUrl = "http://91.217.76.232/learn/step/9708";
        String desktopPath = System.getProperty("user.home") + "/Desktop";
        String fileName = desktopPath + "/correct-answers.json";
        saveCorrectAnswersToFile(fileName, testUrl, correctAnswers);

//        driver.quit();
    }

//    private static void login(WebDriver driver) {
//        driver.get("https://hyperskill.org/login");
//
//        WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
//        WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
//        WebElement signInButton = driver.findElement(By.cssSelector("button[data-cy='submitButton']"));
//
//        emailField.sendKeys("yakushev.s.o@ya.ru");
//        passwordField.sendKeys("{yx#e%B9~SGl4@Cr");
//        signInButton.click();
//    }

    private static void goToTest(WebDriver driver) {
//        driver.get("https://hyperskill.org/learn/step/3741");
        driver.get("http://91.217.76.232/learn/step/9708");
    }

//    private static void selectFirstAnswer(WebDriver driver) {
//        WebElement radioBtn = driver.findElement(By.cssSelector("input[type='radio'][value='1']"));
//        Actions actions = new Actions(driver);
//        actions.moveToElement(radioBtn).click().perform();
//        WebElement signInButton = driver.findElement(By.cssSelector("button[id='sendBtn']"));
//        signInButton.click();
//    }

    private static List<String> getCorrectAnswers(WebDriver driver) {
        String pageSource = driver.getPageSource();
        Document doc = Jsoup.parse(pageSource);

        List<String> correctAnswers = new ArrayList<>();
        Elements checkedInputs = doc.select("input[type=radio][checked], input[type=checkbox][checked]");
        for (Element checkedInput : checkedInputs) {
            String value = checkedInput.attr("value");
            correctAnswers.add(value);
        }

        return correctAnswers;
    }

    private static void saveCorrectAnswersToFile(String fileName, String testUrl, List<String> correctAnswers) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("url", testUrl);
        JsonArray jsonArray = new JsonArray();
        for (String answer : correctAnswers) {
            jsonArray.add(answer);
        }
        jsonObject.add("answers", jsonArray);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(fileName);
        FileWriter writer = new FileWriter(file);
        gson.toJson(jsonObject, writer);
        writer.close();
    }
}
