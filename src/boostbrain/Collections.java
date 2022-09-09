package boostbrain;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Collections {
    public static void main(String[] args) throws IOException {
        // Считать данные csv файла и поместить в String
        FileInputStream stream = new FileInputStream("src/files/database.csv");
        int length = stream.available();
        byte [] data = new byte[length];
        stream.read(data);
        String text = new String(data);
//        System.out.println(text);

        // Разделить строки поместив в ArrayList, затем разделить строки на слова поместив в String []
        ArrayList <String []> lineWords = new ArrayList<>();
        String [] lines = text.split("\n");
        for(String line : lines){
//            System.out.println(line);
            String [] words = line.split(",");
            lineWords.add(words);
        }

        // Вывести в консоль форматированные строки по ключевому слову
        for(String [] words: lineWords){
            if(words[5].equals("бар")){
                for(String word : words){
                    System.out.print(word);
                    System.out.print(" | ");
                }
                System.out.println("\n==============================");
            }
        }

        // Вывести в консоль коливество строк по ключевому слову
        TreeMap<String, Integer> map = new TreeMap<>(); // HashMap без сортировки
        for (String [] words: lineWords) {
            if (map.containsKey(words[5])){
                Integer count = map.get(words[5]);
                count++;
                map.put(words[5], count);
            } else {
                map.put(words[5], 1);
            }
        }
//        System.out.println("Итого: " + map.get("бар"));

        // Вывести в консоль количество для каждого ключевого слова
        for (String key : map.keySet()) {
            System.out.println("Тип: " + key + " -> Количество: " + map.get(key));
        }
    }
}
