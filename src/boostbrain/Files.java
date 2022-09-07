package boostbrain;

import java.io.*;

public class Files {
    public static void main(String[] args) throws IOException {

        // Получить список текущего каталога
        File dir = new File("."); // Получить текущий каталог
        String [] names = dir.list(); // Массив содержимого каталога
        assert names != null;
        for (String name : names) {
            System.out.println(name);
        }

        // Получить размер в байтах, проверить доступность файла
        File file = new File("README.md"); // Получить файл "имя файла"
        long sizeFile = file.length(); // Получить размер файла в байтах
        System.out.println(file + " = " + sizeFile + " байт");

        // Вывести в консоль данные файла
        FileInputStream inputStream = new FileInputStream(file); // Чтение байтов из файла
        int sizeInputStream = inputStream.available(); // Сколько байт доступно для чтения
        byte [] data = new byte[sizeInputStream]; // Массив размером с файл
        inputStream.read(data); // Прочитать данные файла
        String text = new String(data); // Записать содержимое файла в String
        System.out.println(text);

        // Записать данные в файл без перезаписи
        FileOutputStream outputStream = new FileOutputStream(file, true); // Запись байтов в файл, если true файл не перезаписывать
        String newText = "\n<!-- text -->";
        byte [] newTextByte = newText.getBytes(); // Конфвертировать и передать текст в массив байтов
        outputStream.write(newTextByte); // Записать данные в файл
        outputStream.close();

        // Повторно вывести в консоль данные файла
        FileInputStream inputStream1 = new FileInputStream(file);
        int sizeInputStream1 = inputStream1.available();
        byte [] data1 = new byte[sizeInputStream1];
        inputStream1.read(data1);
        String text1 = new String(data1);
        System.out.println(text1);
    }
}