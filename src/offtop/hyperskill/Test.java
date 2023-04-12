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

        swapElements(driver);

//        driver.quit();
    }

    private static void swapElements(WebDriver driver) {
        if (checkDownload(driver, "//div[@class='submission']")) {
            int first = 0;
            int second = 0;

            for (int i = 1; i < 4; i++) {
                String question = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[1]/div[" + 1 + "]/span";

                WebElement element = driver.findElement(By.xpath(question));
                String text = element.getText();

                if ("// a comment".equals(text)) {
                   first = i;
                   break;
                }
            }

            while (first != second) {
                for (int i = 1; i < 4; i++) {
                    String test = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + i + "]/div/span";
                    String upArrow = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + i +
                            "]/div/div[2]/button[" + 1 + "]";
                    String downArrow = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + i +
                            "]/div/div[2]/button[" + 2 + "]";

                    WebElement element = driver.findElement(By.xpath(test));
                    String text = element.getText();

                    if ("end-of-line comment".equals(text)) {
                        second = i;

                        if (first == second) {
                            break;
                        }

                        WebElement arrow = driver.findElement(By.xpath(first < second ? upArrow : downArrow));
                        arrow.click();
                        System.out.println("first = " + first + " / " + "second = " + second);
                        System.out.println(first > second ? "upArrow" : "downArrow");
                        break;
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

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDownload(WebDriver driver, String s) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s))).isDisplayed();
    }
}
