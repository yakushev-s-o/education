package thinkingInJava.c5_initialization_and_cleanup;

public class E12_Tank {
    boolean level;

    E12_Tank(boolean b){
        level = b;
    }

    public void empty(){
        level = false;
    }

    public void finalize(){
        if(level) {
            System.out.println("Tank full");
        } else {
            System.out.println("Tank empty");
        }
    }

    public static void main(String[] args) {
        E12_Tank tank = new E12_Tank(true);
        tank.empty();
        System.gc();
        new E12_Tank(false);
        System.gc();
        new E12_Tank(true);
        System.gc();
    }
}
