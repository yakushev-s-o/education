package thinkingInJava;

import java.util.*;
import static thinkingInJava.util.Print.*;

/** Первая программа.
 * Выводит строку и текущее число.
 * @author Брюс Эккель
 * @author www.site.ru
 * @version 1.0.0.0
 */
public class HelloDate {
    /** Точка входа в класс и приложение.
     * @param args Массив строковых агрументов.
     */
    public static void main(String[] args) {
        System.out.println("Привет, сегодня: ");
        System.out.println(new Date());
        print("Короткие команды!");
    }
}