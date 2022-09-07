package boostbrain;

import java.io.FileInputStream;
import java.io.IOException;

public class Collections {
    public static void main(String[] args) throws IOException {
        FileInputStream stream = new FileInputStream("database.csv");
        int length = stream.available();
        byte [] data = new byte[length];
        stream.read(data);
        String text = new String(data);
//        System.out.println(text);

        String [] lines = text.split("\n");
        for(String line : lines){
            System.out.println(line);
            System.out.println("====================================");
        }
    }
}
