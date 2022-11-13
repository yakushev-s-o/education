package thinkingInJava.c6_access_control;

class Test {
    public int numPublic;
    int numPackage;
    protected int numProtected;
    private int numPrivate;

    public String metPublic(){return "metPublic";}
    String metPackage(){return "metPackage";}
    protected String metProtected(){return "metProtected";}
    private String metPrivate(){return "metPublic";}

}

public class E5_Test_Access {
    public static void main(String[] args) {
        Test test = new Test();
        test.numPublic = 1;
        test.numPackage = 2;
        test.numProtected = 3;
//        test.numPrivate = 4;

        test.metPublic();
        test.metPackage();
        test.metProtected();
//        test.metPrivate();
    }
}
