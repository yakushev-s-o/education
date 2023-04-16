package offtop.hyperskill;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        login(driver);

        driver.get("https://hyperskill.org/learn/step/2123");
//        driver.get("http://91.217.76.232/learn/step/2123");

        boolean[][] b = new boolean[][] {
                {true, false, false, true},
                {true, true, true, true},
                {true, true, true, true},
                {true, true, true, true}};
        sendMatrix(driver, b);

//        driver.quit();
    }

    private static void getMatrix(WebDriver driver) {
        if (checkDownload(driver, "//div[@class='submission submission-correct']")) {
            WebElement tbody = driver.findElement(By.tagName("tbody"));
            List<WebElement> rows = tbody.findElements(By.tagName("tr"));
            int rowCount = rows.size();
            List<WebElement> columns = rows.get(0).findElements(By.tagName("td"));
            int columnCount = columns.size() - 1;

            boolean[][] matrix = new boolean[rowCount][columnCount];
            for (int i = 1; i <= rowCount; i++) {
                for (int j = 1; j <= columnCount; j++) {
                    String s = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/table/tbody/tr" +
                            "[" + i + "]/td[" + (j + 1) + "]/div/div";
                    WebElement checkbox = driver.findElement(By.xpath(s));

                    matrix[i - 1][j - 1] = "custom-checkbox checked disabled".equals(checkbox.getAttribute("class"));
                }
            }

            System.out.println(Arrays.deepToString(matrix));
        }
    }

    private static void sendMatrix(WebDriver driver, boolean[][] correctAnswer) {
        if (checkDownload(driver, "//div[@class='submission']")) {
            for (int i = 1; i <= correctAnswer.length; i++) {
                for (int j = 1; j <= correctAnswer[i - 1].length; j++) {
                    String s = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/table/tbody/tr" +
                            "[" + i + "]/td[" + (j + 1) + "]/div/div";
                    WebElement checkbox = driver.findElement(By.xpath(s));

                    if (correctAnswer[i - 1][j - 1]) {
                        checkbox.click();
                    }
                }
            }

        }
    }

    private static void login(WebDriver driver) {
        driver.get("https://hyperskill.org/login");

        if (checkDownload(driver, "//input[@type='email']")) {
            WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
            WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
            WebElement signInButton = driver.findElement(By.cssSelector("button[data-cy='submitButton']"));

            emailField.sendKeys("yakushev.s.o@ya.ru");
            passwordField.sendKeys("{yx#e%B9~SGl4@Cr");
            signInButton.click();
        }

        checkDownload(driver, "//div[@class='user-avatar']");
    }

    private static boolean checkDownload(WebDriver driver, String s) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s))).isDisplayed();
    }
}
