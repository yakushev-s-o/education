package offtop.hyperskill_manager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Введите номер трека:");

            int track = sc.nextInt();

            Automation test = new Automation(track);
            SavePages save = new SavePages(track);
            Util util = new Util(track);

            while (true) {
                System.out.println("""
                        Выберите режим:
                        1. Получить данные
                        2. Сохранить страницы
                        3. Получить правильные ответы
                        4. Ответить на тесты
                        5. Выйти""");

                int mode = sc.nextInt();

                if (mode == 1) {
                    util.createDriver(true);
                    util.login();
                    util.getData(12);
                } else if (mode == 2) {
                    System.out.println("""
                        Выберите режим:
                        1. Сохранить топики
                        2. Сохранить проекты
                        3. Сохранить этапы
                        4. Сохранить темы
                        5. Сохранить все""");

                    int saveMode = sc.nextInt();

                    save.createDriver(true);
                    save.login();

                    if (saveMode == 1) {
                        save.saveTopics();
                    } else if (saveMode == 2) {
                        save.saveProjects();
                    } else if (saveMode == 3) {
                        save.saveStages();
                    } else if (saveMode == 4) {
                        save.saveSteps();
                    } else if (saveMode == 5) {
                        save.saveTopics();
                        save.saveProjects();
                        save.saveStages();
                        save.saveSteps();
                    }
                } else if (mode == 3) {
                    test.createDriver(true);
                    test.login();
                    test.getAnswers();
                } else if (mode == 4) {
                    test.createDriver(false);
                    test.login();
                    test.sendAnswers();
                } else if (mode == 5) {
                    System.exit(0);
                }
            }
        }
    }
}
