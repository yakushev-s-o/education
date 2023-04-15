package offtop.hyperskill;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        login(driver);

        driver.get("https://hyperskill.org/learn/step/2499");
//        driver.get("http://91.217.76.232/learn/step/2499");

        String[] correctAnswers = new String[]{"// a comment;end-of-line comment",
                "/* a comment */;multi-line comment",
                "/** a comment */;doc comment"};
        swapElements(driver, correctAnswers);

//        driver.quit();
    }

    private static void swapElements(WebDriver driver, String[] correctAnswer) {
        if (checkDownload(driver, "//div[@class='left-side__line']")) {
            for (int i = 1; i <= correctAnswer.length; i++) {
                String question = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[1]/div[" + i + "]/span";
                WebElement element1 = driver.findElement(By.xpath(question));
                String text1 = element1.getText();

                String[] res = null;
                for (String s : correctAnswer) {
                    res = s.split(";");

                    if (res[0].equals(text1)) {
                        break;
                    }
                }

                boolean checkTrue = true;
                while (checkTrue) {
                    for (int j = 1; j <= correctAnswer.length; j++) {
                        String answer = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + j + "]/div/span";
                        String upArrow = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + j +
                                "]/div/div[2]/button[" + 1 + "]";
                        String downArrow = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + j +
                                "]/div/div[2]/button[" + 2 + "]";
                        WebElement element2 = driver.findElement(By.xpath(answer));
                        String text2 = element2.getText();

                        if (text2.equals(res[1])) {
                            if (i != j) {
                                WebElement arrow = driver.findElement(By.xpath(i < j ? upArrow : downArrow));
                                arrow.click();
                            } else {
                                checkTrue = false;
                            }
                        }
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
