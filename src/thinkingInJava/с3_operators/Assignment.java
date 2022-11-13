package thinkingInJava.с3_operators;

import static thinkingInJava.util.Print.*;

class Tank {
    float level;
}

public class Assignment {
    public static void main(String[] args) {
        Tank t1 = new Tank();
        Tank t2 = new Tank();
        t1.level = 9.5f;
        t2.level = 47.7f;
        print("1: t1.level: " + t1.level + ", t2.level: " + t2.level);
        t1 = t2;
        print("2: t1.level: " + t1.level + ", t2.level: " + t2.level);
        t1.level = 25.5f;
        print("3: t1.level: " + t1.level + ", t2.level: " + t2.level);
    }
}
