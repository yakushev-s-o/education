package offtop.hyperskill_manager;

import com.google.common.reflect.TypeToken;
import com.google.gson.*;
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
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TestAutomation {
    private static WebDriver driver;
    private static final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/learn/step/";
    private static final String CHROMEDRIVER_PATH = "C:\\tools\\chromedriver_win32\\chromedriver.exe";
    private static final String JSON_PATH = "src/offtop/hyperskill_manager/correct-answers.json";
    private static final String STEP_PATH = "https://hyperskill.org/learn/step/";

    private void createChromeDriver() {
        // Устанавливаем путь к драйверу браузера
        System.setProperty("webdriver.chrome.driver", CHROMEDRIVER_PATH);

        // Создаем экземпляр драйвера
        driver = new ChromeDriver();
        driver.manage().window().maximize();

//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--headless");
//        options.addArguments("--disable-gpu");
//        driver = new ChromeDriver(options);
    }

    // Выполняем авторизацию на сайте
    public void login() {
        createChromeDriver();

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

    // Получаем список топиков
    public List<String> getDescendants() {
        String url = "https://hyperskill.org/api/topic-relations?format=json&track_id=12";

        List<String> list = new ArrayList<>();

        try (InputStream inputStream = new URL(url).openStream()) {
            JsonElement jsonElement = JsonParser.parseReader(new InputStreamReader(inputStream));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray topicRelationsArr = jsonObject.getAsJsonArray("topic-relations");

            JsonObject objWithNullParentId;
            for (JsonElement element : topicRelationsArr) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("parent_id").isJsonNull()) {
                    objWithNullParentId = obj;
                    JsonArray descendantsArr = objWithNullParentId.getAsJsonArray("descendants");
                    list.add(String.valueOf(descendantsArr));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Получаем все правильные ответы и по очереди сохраняем в файл
    public void getAnswers() {
        createChromeDriver();

        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (!checkMatch(file.getName())) {
                    driver.get(FOLDER_PATH + file.getName());

                    // Проверяем что это страница с тестом
                    WebElement element = driver.findElement(By.cssSelector("[click-event-target='practice']"));

                    if (element.getAttribute("aria-pressed").equals("true") && checkCorrect()) {
                        saveCorrectAnswerToFile(getCorrectAnswer(file.getName()));
                    }
                }
            }
        }

        driver.quit();
    }

    // Заполняем правильные ответы из файла на сайте
    public void sendAnswers() {
        for (Answers answer : getFileData()) {
            if (!answer.getChecked()) {
                driver.get(answer.getUrl());

                if (!checkCorrect()) {
                    switch (answer.getMode()) {
                        case 1 -> {
                            sendTestSingle(answer.getAnswerStr());
                            answer.setChecked(true);
                        }
                        case 2 -> {
                            sendTestMultiple(answer.getAnswerArr());
                            answer.setChecked(true);
                        }
                        case 3 -> {
                            sendCode(answer.getAnswerStr());
                            answer.setChecked(true);
                        }
                        case 4 -> {
                            sendTextNum(answer.getAnswerStr());
                            answer.setChecked(true);
                        }
                        case 5 -> {
                            sendTextShort(answer.getAnswerStr());
                            answer.setChecked(true);
                        }
                        case 6 -> {
                            sendMatch(answer.getAnswerListArr());
                            answer.setChecked(true);
                        }
                        case 7 -> {
                            sendMatrix(answer.getAnswerBoolean());
                            answer.setChecked(true);
                        }
                    }
                }

                delay();
            }
        }

        driver.quit();
    }

    // Проверяем ссылку на совпадение в файле
    private boolean checkMatch(String page) {
        for (Answers answer : getFileData()) {
            if (answer.getUrl().equals(STEP_PATH + page.replace(".html", ""))) {
                return true;
            }
        }

        return false;
    }

    // Сохраняем правильный ответ в файл в формате JSON
    private void saveCorrectAnswerToFile(Answers answer) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        File file = new File(JSON_PATH);

        // Добавляем новые данные к уже существующим данным в памяти
        List<Answers> listAnswer = getFileData();
        listAnswer.add(answer);

        // Записываем обновленные данные в файл
        try {
            FileWriter writer = new FileWriter(file);
            gson.toJson(listAnswer, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Получаем правильный ответ используя подходящий метод
    private Answers getCorrectAnswer(String srcPage) {
        waitDownloadElement("//div[@class='step-problem']");

        final String SINGLE = "Select one option from the list";
        final String MULTIPLE = "Select one or more options from the list";
        final String CODE = "Write a program in Java";
        final String TEXT_NUM = "Enter a number";
        final String TEXT_SHORT = "Enter a short text";
        final String MATCH = "Match the items from left and right columns";
        final String MATRIX = "Choose one or more options for each row";

        WebElement element = driver.findElement(By.xpath("//div[@class='mb-1 text-gray']/span"));
        String page = STEP_PATH + srcPage.replace(".html", "");

        String text = element.getText();
        if (text.equals(SINGLE)) {
            return new Answers(page, false, 1, getTestSingle());
        } else if (text.equals(MULTIPLE)) {
            return new Answers(page, false, 2, getTestMultiple());
        } else if (text.contains(CODE)) {
            return new Answers(page, false, 3, getCode());
        } else if (text.equals(TEXT_NUM)) {
            return new Answers(page, false, 4, getTextNum());
        } else if (text.equals(TEXT_SHORT)) {
            return new Answers(page, false, 5, getTextShort());
        } else if (text.equals(MATCH)) {
            return new Answers(page, false, 6, getMatch());
        } else if (text.equals(MATRIX)) {
            return new Answers(page, false, 7, getMatrix());
        }

        return new Answers(page, false, 0, "");
    }

    // Получаем список объектов Answers из файла
    private List<Answers> getFileData() {
        Gson gson = new Gson();
        File file = new File(JSON_PATH);
        List<Answers> listAnswers = new ArrayList<>();

        // Проверяем, существует ли заполненный файл, и если да, то загружаем его содержимое в память
        if (file.exists() && file.length() != 0) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                Type listType = new TypeToken<List<Answers>>() {
                }.getType();
                listAnswers = gson.fromJson(reader, listType);
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return listAnswers;
    }

    // Задержка между переходами
    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Получаем один ответ из теста
    private String getTestSingle() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement element = driver.findElement(By.xpath("//input[@type='radio' and @checked]/following-sibling::label/div"));

        return element.getText();
    }

    // Выбираем один ответ в тесте
    private void sendTestSingle(String answer) {
        waitDownloadElement("//div[@class='step-problem']");

        Actions actions = new Actions(driver);
        List<WebElement> elements = driver.findElements(By.xpath("//label[@class='custom-control-label']"));

        for (WebElement element : elements) {
            if (element.getText().equals(answer)) {
                actions.moveToElement(element).click().perform();
            }
        }

//        clickOnButtonSend();
    }

    // Получаем несколько ответов из теста
    private String[] getTestMultiple() {
        waitDownloadElement("//div[@class='step-problem']");

        List<String> correctAnswers = new ArrayList<>();
        List<WebElement> input = driver.findElements(By.xpath("//input[@type='checkbox' and @checked]/following-sibling::label/div"));

        for (WebElement answer : input) {
            correctAnswers.add(answer.getText());
        }

        return correctAnswers.toArray(new String[0]);
    }

    // Выбираем несколько ответов в тесте
    private void sendTestMultiple(String[] answer) {
        waitDownloadElement("//div[@class='step-problem']");

        for (String i : answer) {
            Actions actions = new Actions(driver);
            WebElement input = driver.findElement(By.xpath("//input[@type='checkbox']/following-sibling::label/div[normalize-space()='" + i + "']"));
            actions.moveToElement(input).click().perform();
        }

//        clickOnButtonSend();
    }

    // Получаем ответ из поля с кодом
    private String getCode() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        return input.getText();
    }

    // Записываем ответ в поле с кодом
    private void sendCode(String code) {
        waitDownloadElement("//div[@class='cm-content']");

        WebElement input = driver.findElement(By.xpath("//div[@class='cm-content']"));
        input.clear();

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].innerText = '" + code.replace("\n", "\\n")
                .replace("'", "\\'") + "';", input);

//        clickOnButtonSend();
    }

    // Получаем ответ из текстового поля
    private String getTextNum() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));

        return input.getAttribute("value");
    }

    // Записываем ответ в текстовое поле
    private void sendTextNum(String answer) {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//input[@type='number']"));
        input.sendKeys(answer);

//        clickOnButtonSend();
    }

    // Получаем ответ из текстового поля
    private String getTextShort() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//textarea"));

        return input.getText();
    }

    // Записываем ответ в текстовое поле
    private void sendTextShort(String answer) {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement input = driver.findElement(By.xpath("//textarea"));
        input.sendKeys(answer);

//        clickOnButtonSend();
    }

    // Получаем список правильных ответов из теста с сопоставлением
    private List<String[]> getMatch() {
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
    private void sendMatch(List<String[]> correctAnswers) {
        waitDownloadElement("//div[@class='step-problem']");

        for (int i = 1; i <= correctAnswers.size(); i++) {
            String question = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[1]/div[" + i + "]/span";
            WebElement element1 = driver.findElement(By.xpath(question));
            String text1 = element1.getText();

            String[] res = null;
            for (String[] ans : correctAnswers) {
                res = ans;

                if (res[0].equals(text1)) {
                    break;
                }
            }

            boolean checkTrue = true;
            while (checkTrue) {
                for (int j = 1; j <= correctAnswers.size(); j++) {
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

        // sendAnswer();
    }

    // Получить матрицу правильных ответов из теста
    private boolean[][] getMatrix() {
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
    private void sendMatrix(boolean[][] correctAnswer) {
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

//        clickOnButtonSend();
    }

    // Проверяем тест на Correct
    private boolean checkCorrect() {
        waitDownloadElement("//div[@class='step-problem']");

        try {
            driver.findElement(By.xpath("//strong[@class='text-success' and text()=' Correct. ']"));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // Нажимаем на кнопку "Send"
    private void clickOnButtonSend() {
        WebElement signInButton = driver.findElement(By.cssSelector("button[id='sendBtn']"));
        signInButton.click();
    }

    // Проверяем состояние загрузки страницы
    private void waitDownloadElement(String s) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(s))).isDisplayed();
    }
}
