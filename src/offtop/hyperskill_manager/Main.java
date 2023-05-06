package offtop.hyperskill_manager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Automation test = new Automation();
        PageSorting pages = new PageSorting();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Выберите режим:
                    1. Сортировка страниц
                    2. Получить правильные ответы
                    3. Ответить на тесты
                    4. Получить список тем и заданий
                    5. Выйти""");

            int mode = sc.nextInt();

            if (mode == 1) {
                pages.run();
            } else if (mode == 2) {
                test.createDriver(true);
                test.login();
                test.getAnswers();
            } else if (mode == 3) {
                test.createDriver(false);
                test.login();
                test.sendAnswers();
            } else if (mode == 4) {
                test.getSteps(test.getTopics(12));
            } else if (mode == 5) {
                System.exit(0);
            }
        }
    }
}
