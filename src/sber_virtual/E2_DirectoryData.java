package sber_virtual;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

public class E2_DirectoryData {
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(Paths.get("src/files/city_ru.csv")); // Считываем CSV файл и парсим его в массив объектов
            scanner.useDelimiter(System.getProperty("line.separator")); // Генерируем файл с окончанием строк (для разных ОС)
            while (scanner.hasNext()) {
                City city = parseLine(scanner.next()); // Парсим строку в объект City
                System.out.println(city);
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static City parseLine(String line) {
        Scanner scanner = new Scanner(line); // Считываем строку
        scanner.useDelimiter("\\s*;\\s*"); // Делим строку по разделителю ";"
        scanner.skip("\\d*"); // Пропускаем первую ячейку
        String name = scanner.next();
        String region = scanner.next();
        String district = scanner.next();
        int population = scanner.nextInt();
        String foundation = null;
        if (scanner.hasNext()) {
            foundation = scanner.next();
        }
        return new City(name, region, district, population, foundation);
    }

    public static class City {
        private final String name;
        private final String region;
        private final String district;
        private final int population;
        private final String foundation;

        public City(String name, String region, String district, int population, String foundation) {
            this.name = name;
            this.region = region;
            this.district = district;
            this.population = population;
            this.foundation = foundation;
        }

        @Override
        public String toString() {
            return "Employee {" +
                    "name='" + name + '\'' +
                    ", region='" + region + '\'' +
                    ", district='" + district + '\'' +
                    ", population=" + population +
                    ", foundation=" + foundation +
                    '}';
        }
    }
}
