package thinkingInJava.c6_access_control;

class ProtectedData{
    protected int a = 5;
}

class DataManipulator{
    public int changeData(ProtectedData data){
        return ++data.a;
    }
}

public class E6_ProtectedTest {
    public static void main(String[] args) {
        System.out.println(new ProtectedData().a);
        System.out.println(new DataManipulator().changeData(new ProtectedData()));
    }
}
