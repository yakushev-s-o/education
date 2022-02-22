package devcolibri.oop.modStatic;

public class Main {

    public static void main(String[] args) {

        ExamStatic examStatic = new ExamStatic(); // wrong
        System.out.println(examStatic.a); // wrong

        System.out.println(ExamStatic.a);
        System.out.println(ExamStatic.print());

        ExamStatic.iteration();
        ExamStatic.iteration();
        ExamStatic.iteration();

        System.out.println(ExamStatic.iteration());

    }

}
