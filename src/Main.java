import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        String filePath = "http://91.217.76.232/learn/step/18461";
        List<String> correctAnswers = getCorrectAnswers(filePath);
        System.out.println("Correct answers are: " + correctAnswers);
    }

    public static List<String> getCorrectAnswers(String filePath) throws IOException {
        List<String> correctAnswers = new ArrayList<>();
        Document doc = Jsoup.connect(filePath).get();
        Elements checkedInputs = doc.select("input[type=radio][checked], input[type=checkbox][checked]");
        for (Element checkedInput : checkedInputs) {
            String value = checkedInput.attr("value");
            correctAnswers.add(value);
        }
        return correctAnswers;
    }
}
