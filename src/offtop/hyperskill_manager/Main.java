package offtop.hyperskill_manager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TestAutomation test = new TestAutomation();
        PageSorting pages = new PageSorting();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("""
                    Выберите режим:
                    1. Сортировка страниц
                    2. Получить правильные ответы
                    3. Ответить на тесты
                    4. Выйти""");

            int mode = sc.nextInt();

            if (mode == 1) {
                pages.run();
            } else if (mode == 2) {
                test.getAnswers();
            } else if (mode == 3) {
                test.login();
                test.sendAnswers();
            } else if (mode == 4) {
                System.exit(0);
            }
        }
    }
}
