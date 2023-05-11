package offtop.hyperskill_manager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Automation test = new Automation();
        SavePages save = new SavePages();
        Util util = new Util();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Выберите режим:
                    1. Сохранить страницы
                    2. Получить правильные ответы
                    3. Ответить на тесты
                    4. Получить данные
                    5. Выйти""");

            int mode = sc.nextInt();

            if (mode == 1) {
                save.createDriver(false);
                save.login();
//                save.saveTopics();
//                save.saveProjects();
//                save.saveSteps();
            } else if (mode == 2) {
                test.createDriver(true);
                test.login();
                test.getAnswers();
            } else if (mode == 3) {
                test.createDriver(false);
                test.login();
                test.sendAnswers();
            } else if (mode == 4) {
                util.createDriver(true);
                util.login();
                util.getData(12);
            } else if (mode == 5) {
                System.exit(0);
            }
        }
    }
}
