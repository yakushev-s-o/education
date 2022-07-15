package devcolibri.exceptions;

public class Exceptions {
    public static void main(String[] args) {
        String[] str = new String[2];
        try{
            System.out.println(str[5]);
        }catch (ArrayIndexOutOfBoundsException ex) {
//            ex.printStackTrace();
            System.out.println("Элемент массива по данному индексу не существует!");
        }

        try{
            System.out.println(str[5]);
        }catch (Exception ex) {
//            ex.printStackTrace();
            System.out.println("Exception");
        }

        try{
            System.out.println(str[5]);
        }catch (Throwable ex) {
//            ex.printStackTrace();
            System.out.println("Throwable");
        }
    }
}
