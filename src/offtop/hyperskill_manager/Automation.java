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

public class Automation {
    private static WebDriver driver;
    private static final String FOLDER_PATH = "C:/Users/Admin/Desktop/test/learn/step/";
    private static final String CHROMEDRIVER_PATH = "C:\\tools\\chromedriver_win32\\chromedriver.exe";
    private static final String JSON_PATH = "src/offtop/hyperskill_manager/answer-list.json";
    private static final String STEP_PATH = "https://hyperskill.org/learn/step/";
    private static final String STEP_LIST_PATH = "src/offtop/hyperskill_manager/step-list.json";

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
                    List<Step> listSteps = getFileData(new TypeToken<List<Step>>() {
                    }.getType(), STEP_LIST_PATH);
                    // Добавляем новый шаг в список и записываем в файл
                    saveToFile(new Step(id, title, listStep), listSteps, STEP_LIST_PATH);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Получаем все правильные ответы и по очереди сохраняем в файл
    public void getAnswers() {
        createChromeDriver();

        File folder = new File(FOLDER_PATH);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                // Пропускаем если есть совпадение ссылки в файле
                if (!checkMatch(file.getName())) {
                    driver.get(FOLDER_PATH + file.getName());

                    // Проверяем что это страница с тестом
                    WebElement element = driver.findElement(By.cssSelector("[click-event-target='practice']"));

                    if (element.getAttribute("aria-pressed").equals("true") && checkCorrect()) {
                        // Получаем список ответов из файла
                        List<Answer> listAnswers = getFileData(new TypeToken<List<Answer>>() {
                        }.getType(), JSON_PATH);
                        // Добавляем новый ответ в список и записываем файл
                        saveToFile(getCorrectAnswer(file.getName()), listAnswers, JSON_PATH);
                    }
                }
            }
        }

        driver.quit();
    }

    // Заполняем правильные ответы из файла на сайте
    public void sendAnswers() {
        // Получаем список ответов из файла
        List<Answer> answers = getFileData(new TypeToken<List<Answer>>() {
        }.getType(), JSON_PATH);

        for (Answer answer : answers) {
            if (!answer.isChecked()) {
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
                            sendMatrix(answer.getMatrixAnswer());
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
        // Получаем список ответов из файла
        List<Answer> answers = getFileData(new TypeToken<List<Answer>>() {
        }.getType(), JSON_PATH);

        for (Answer answer : answers) {
            if (answer.getUrl().equals(STEP_PATH + page.replace(".html", ""))) {
                return true;
            }
        }

        return false;
    }

    // Получаем правильный ответ используя подходящий метод
    private Answer getCorrectAnswer(String srcPage) {
        waitDownloadElement("//div[@class='step-problem']");

        final String SINGLE = "Select one option from the list";
        final String MULTIPLE = "Select one or more options from the list";
        final String CODE = "Write a program in";
        final String TEXT_NUM = "Enter a number";
        final String TEXT_SHORT = "Enter a short text";
        final String MATCH = "Match the items from left and right columns";
        final String MATRIX_MORE = "Choose one or more options for each row";
        final String MATRIX_ONE = "Choose one option for each row";

        WebElement element = driver.findElement(By.xpath("//div[@class='mb-1 text-gray']/span"));
        String page = STEP_PATH + srcPage.replace(".html", "");

        String text = element.getText();

        if (text.equals(SINGLE)) {
            return new Answer(page, false, 1, getTestSingle());
        } else if (text.equals(MULTIPLE)) {
            return new Answer(page, false, 2, getTestMultiple());
        } else if (text.contains(CODE)) {
            return new Answer(page, false, 3, getCode());
        } else if (text.equals(TEXT_NUM)) {
            return new Answer(page, false, 4, getTextNum());
        } else if (text.equals(TEXT_SHORT)) {
            return new Answer(page, false, 5, getTextShort());
        } else if (text.equals(MATCH)) {
            return new Answer(page, false, 6, getMatch());
        } else if (text.equals(MATRIX_MORE) || text.equals(MATRIX_ONE)) {
            return new Answer(page, false, 7, getMatrix());
        }

        return new Answer(page, false, 0, "");
    }

    // Получаем список объектов из файла
    private <T> List<T> getFileData(Type type, String path) {
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
    private <T> void saveToFile(T answer, List<T> list, String path) {
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
    private String[][] getMatch() {
        waitDownloadElement("//div[@class='step-problem']");

        List<String[]> correctAnswers = new ArrayList<>();
        List<WebElement> count = driver.findElements(By.xpath("//div[@class='left-side__line']"));

        for (int i = 1; i <= count.size(); i++) {
            String question = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/div[1]/div[" + i + "]/span";
            String answer = "/html/body/div[1]/div[1]/div/div[1]/div/div[4]/div/div/div[1]/div/div/div[2]/div/div[" + i + "]/div/span";
            WebElement element1 = driver.findElement(By.xpath(question));
            WebElement element2 = driver.findElement(By.xpath(answer));

            String[] pairs;

            // Если текст не найден, ищем изображение
            if (element1.getText().equals("")) {
                String questionImg = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/div[1]/div[" + i + "]/span/img";
                element1 = driver.findElement(By.xpath(questionImg));
                pairs = new String[]{element1.getAttribute("src"), element2.getText()};
            } else {
                pairs = new String[]{element1.getText(), element2.getText()};
            }

            correctAnswers.add(pairs);
        }

        return correctAnswers.toArray(new String[0][]);
    }

    // Выбираем ответы в тесте с сопоставлением
    private void sendMatch(String[][] correctAnswers) {
        waitDownloadElement("//div[@class='step-problem']");

        for (int i = 1; i <= correctAnswers.length; i++) {
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
                for (int j = 1; j <= correctAnswers.length; j++) {
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
    private List<Matrix> getMatrix() {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement thead = driver.findElement(By.tagName("thead"));
        List<WebElement> head = thead.findElements(By.tagName("tr"));
        List<WebElement> columnsArr = head.get(0).findElements(By.tagName("th"));

        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> rowArr = tbody.findElements(By.tagName("tr"));

        List<Matrix> matrixList = new ArrayList<>();

        for (int i = 1; i < rowArr.size() + 1; i++) {
            for (int j = 1; j < columnsArr.size(); j++) {
                String s = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/table/tbody/tr" +
                        "[" + i + "]/td[" + (j + 1) + "]/div/div";
                WebElement checkbox = driver.findElement(By.xpath(s));
                boolean check = "custom-checkbox checked disabled".equals(checkbox.getAttribute("class")) ||
                        "custom-radio checked disabled".equals(checkbox.getAttribute("class"));
                List<WebElement> nameRow = rowArr.get(i - 1).findElements(By.tagName("td"));

                matrixList.add(new Matrix(nameRow.get(0).getText(), columnsArr.get(j).getText(), check));
            }
        }

        return matrixList;
    }

    // Выбираем правильные ответы в тесте с матрицей
    private void sendMatrix(List<Matrix> matrixList) {
        waitDownloadElement("//div[@class='step-problem']");

        WebElement thead = driver.findElement(By.tagName("thead"));
        List<WebElement> head = thead.findElements(By.tagName("tr"));
        List<WebElement> columnsArr = head.get(0).findElements(By.tagName("th"));

        WebElement tbody = driver.findElement(By.tagName("tbody"));
        List<WebElement> rowArr = tbody.findElements(By.tagName("tr"));

        for (int i = 1; i < rowArr.size() + 1; i++) {
            for (int j = 1; j < columnsArr.size(); j++) {
                List<WebElement> nameRow = rowArr.get(i - 1).findElements(By.tagName("td"));

                for (Matrix matrix : matrixList) {
                    if (matrix.getName_row().equals(nameRow.get(0).getText()) &&
                            matrix.getName_columns().equals(columnsArr.get(j).getText()) && matrix.isCheck()) {
                        String s = "/html/body/div[1]/div[1]/div/div/div/div[4]/div/div/div[1]/div[1]/div/table/tbody/tr" +
                                "[" + i + "]/td[" + (j + 1) + "]/div/div";
                        WebElement checkbox = driver.findElement(By.xpath(s));
                        checkbox.click();
                    }
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
