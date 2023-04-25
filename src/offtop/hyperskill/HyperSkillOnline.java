package offtop.hyperskill;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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
    private static WebDriver driver;

    public static void main(String[] args) throws InterruptedException, IOException {
        // Устанавливаем путь к драйверу браузера
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\chromedriver_win32\\chromedriver.exe");

        // Создаем экземпляр драйвера
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        // Выполняем авторизацию
//        login(driver);

        getAllAnswers();

        testSendMethods();

        // Закрываем браузер
        driver.quit();
    }

    private static void getAllAnswers() throws IOException {
        final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/learn/step/";
        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();

        List<Answers> listAnswers = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                driver.get(FOLDER_PATH + file.getName());
                listAnswers.add(getListAnswers(file.getName()));
            }
        }

        saveCorrectAnswersToFile(listAnswers);
    }

    // Получаем правильный ответ и сохраняем его в файл
    private static Answers getListAnswers(String page) {
        waitDownloadElement("//div[@class='step-problem']");

        final String SINGLE = "Select one option from the list";
        final String MULTIPLE = "Select one or more options from the list";
        final String CODE = "Write a program in Java 17";
        final String TEXT = "Enter a number";
        final String MATCH = "Match the items from left and right columns";
        final String MATRIX = "Choose one or more options for each row";

        WebElement element = driver.findElement(By.xpath("//div[@class='mb-1 text-gray']/span"));

        String originPage = "https://hyperskill.org/learn/step/";
        String modPage = originPage + page.replace(".html", "");

        Answers answer = null;

        switch (element.getText()) {
            case SINGLE, MULTIPLE -> answer = new Answers(modPage, getTestAnswers()); // один / несколько ответов
            case CODE -> answer = new Answers(modPage, getCode()); // ответ с кодом
            case TEXT -> answer = new Answers(modPage, getText()); // ответ с текстом
            case MATCH -> answer = new Answers(modPage, getMatch()); // ответ с перестановкой
            case MATRIX -> answer = new Answers(modPage, getMatrix()); // ответ с матрицей
        }

        return answer;
    }

    private static void testSendMethods() {
//        int[] answers = new int[]{2,3};
//        sendTestAnswers(driver, answers);
//
//        String code = "import java.util.Scanner;\n\nclass Main {\n    public static void main(String[] args) {\n        Scanner scanner \u003d new Scanner(System.in);\n        // start coding here\n        int n \u003d scanner.nextInt();\n        if (n \u003c\u003d 12 \u0026\u0026 n \u003e -15) {\n            System.out.println(\"True\");\n        } else if (n \u003e 14 \u0026\u0026 n \u003c 17) {\n            System.out.println(\"True\");\n        } else if (n \u003e\u003d 19) {\n            System.out.println(\"True\");\n        } else {\n            System.out.println(\"False\");\n        }\n    }\n}";
//        sendCode(driver, code);
//
//        String answer = "123";
//        sendText(driver, answer);
//
//        String[] answers = new String[]{"// a comment;end-of-line comment",
//                "/* a comment */;multi-line comment",
//                "/** a comment */;doc comment"};
//        sendMatch(driver, answers);
//
//        boolean[][] answers = new boolean[][]{
//                {true, false, false, true},
//                {true, true, true, true},
//                {true, true, true, true},
//                {true, true, true, true}};
//        sendMatrix(driver, answers);
    }

    // Авторизация на сайте
    private static void login() {
        driver.get("https://hyperskill.org/login");

        waitDownloadElement("//input[@type='email']");

        WebElement emailField = driver.findElement(By.xpath("//input[@type='email']"));
        WebElement passwordField = driver.findElement(By.xpath("//input[@type='password']"));
        WebElement signInButton = driver.findElement(By.cssSelector("button[data-cy='submitButton']"));

        emailField.sendKeys("yakushev.s.o@ya.ru");
        passwordField.sendKeys("{yx#e%B9~SGl4@Cr");
        signInButton.click();

        waitDownloadElement("//div[@class='user-avatar']");
    }

    // Получаем список правильных ответов из теста
    private static List<String> getTestAnswers() {
        waitDownloadElement("//div[@class='step-problem']");

        List<String> correctAnswers = new ArrayList<>();
        List<WebElement> input = driver.findElements(By.cssSelector("input[type=radio][checked], input[type=checkbox][checked]"));

        for (WebElement answer : input) {
            correctAnswers.add(answer.getAttribute("value"));
        }

        return correctAnswers;
    }

    // Выбираем ответы в тесте
    private static void sendTestAnswers(int[] answer) {
        waitDownloadElement("//div[@class='step-problem']");

        for (int i : answer) {
            Actions actions = new Actions(driver);
            WebElement input = driver.findElement(By.cssSelector("input[type='checkbox'][value='" + i + "']"));
            actions.moveToElement(input).click().perform();
        }

//        sendAnswer();
    }

    // Получаем ответ из поля с кодом
    private static String getCode() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        return input.getText();
    }

    // Записываем ответ в поле с кодом
    private static void sendCode(String code) {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        input.clear();

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].innerText = '" + code.replace("\n", "\\n") + "';", input);

//        sendAnswer();
    }

    // Получаем ответ из текстового поля
    private static String getText() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
        return input.getAttribute("value");
    }

    // Записываем ответ в текстовое поле
    private static void sendText(String answer) {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
        input.sendKeys(answer);

//        sendAnswer();
    }

    // Получаем список правильных ответов из теста с сопоставлением
    private static List<String[]> getMatch() {
        waitDownloadElement("//div[@class='step-problem']");

        List<String[]> correctAnswers = new ArrayList<>();
        List<WebElement> count = driver.findElements(By.xpath("//div[@class='left-side__line']"));

        for (int i = 1; i <= count.size(); i++) {
            String question = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/div[1]/div[" + i + "]/span";
            String answer = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + i + "]/div/span";
            WebElement element1 = driver.findElement(By.xpath(question));
            WebElement element2 = driver.findElement(By.xpath(answer));

            String[] s = new String[]{element1.getText(), element2.getText()};
            correctAnswers.add(s);
        }

        return correctAnswers;
    }

    // Выбираем ответы в тесте с сопоставлением
    private static void sendMatch(String[] correctAnswer) {
        waitDownloadElement("//div[@class='step-problem']");

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

//        sendAnswer();
    }

    // Получить матрицу правильных ответов из теста
    private static boolean[][] getMatrix() {
        waitDownloadElement("//div[@class='step-problem']");

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

        return matrix;
    }

    // Выбираем правильные ответы в тесте с матрицей
    private static void sendMatrix(boolean[][] correctAnswer) {
        waitDownloadElement("//div[@class='step-problem']");

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

//        sendAnswer();
    }

    // Нажимаем на кнопку "Send"
    private static void sendAnswer() {
        WebElement signInButton = driver.findElement(By.cssSelector("button[id='sendBtn']"));
        signInButton.click();
    }

    // Проверяем состояние загрузки страницы
    private static void waitDownloadElement(String s) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s))).isDisplayed();
    }

    // Сохраняем список правильных ответов в файл в формате JSON
    private static void saveCorrectAnswersToFile(List<Answers> testDataList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<Answers> existingData = new ArrayList<>();

        // Проверяем, существует ли файл, и если да, то загружаем его содержимое в память
        File file = new File("src/offtop/hyperskill/correct-answers.json");
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
}
