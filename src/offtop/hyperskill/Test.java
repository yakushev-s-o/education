package offtop.hyperskill;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Test {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");

        // Create a new instance of the ChromeDriver
        WebDriver driver = new ChromeDriver();

        login(driver);

        driver.get("https://hyperskill.org/learn/step/2499");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        swapElements(driver);

        // Close the browser
//        driver.quit();
    }

    private static void swapElements(WebDriver driver) {
        WebElement element1 = driver.findElement(By.xpath("//span[@class='value px-3' and contains(text(), 'doc comment')]"));
        WebElement element2 = driver.findElement(By.xpath("//span[@class='value px-3' and contains(text(), 'multi-line comment')]"));

        int x1 = element1.getLocation().getX();
        int y1 = element1.getLocation().getY();
        int x2 = element2.getLocation().getX();
        int y2 = element2.getLocation().getY();

        String js = String.format("window.scrollBy(%d,%d)", x2 - x1, y2 - y1);
        ((JavascriptExecutor) driver).executeScript(js);

        JavascriptExecutor jsss = (JavascriptExecutor) driver;
        jsss.executeScript("arguments[0].setAttribute('style', 'position: absolute; z-index: 10000;')", element1);
        jsss.executeScript("arguments[0].removeAttribute('draggable')", element1);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Actions actions = new Actions(driver);
        actions.dragAndDrop(element1, element2).build().perform();
    }

    private static void login(WebDriver driver) {
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

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkDownload(WebDriver driver, String s) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s)));

        return element.isDisplayed();
    }
}
