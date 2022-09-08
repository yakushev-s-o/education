package boostbrain;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Collections {
    public static void main(String[] args) throws IOException {
        FileInputStream stream = new FileInputStream("src/files/database.csv");
        int length = stream.available();
        byte [] data = new byte[length];
        stream.read(data);
        String text = new String(data);
//        System.out.println(text);

        ArrayList <String []> lineWords = new ArrayList<>();
        String [] lines = text.split("\n");
        for(String line : lines){
//            System.out.println(line);
            String [] words = line.split(",");
            lineWords.add(words);
        }

        for(String [] words: lineWords){
            if(words[5].equals("бар")){
                for(String word : words){
                    System.out.print(word);
                    System.out.print(" | ");
                }
                System.out.println("\n==============================");
            }
        }

        HashMap<String, Integer> map = new HashMap<>();
        for (String [] words: lineWords) {
            if (map.containsKey(words[5])){
                Integer count = map.get(words[5]);
                count++;
                map.put(words[5], count);
            } else {
                map.put(words[5], 1);
            }
        }
        System.out.println("Итого: " + map.get("бар"));
    }
}
