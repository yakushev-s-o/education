package devcolibri.exceptions;

import java.io.PrintStream;

public class MyException extends Exception{
    @Override
    public void printStackTrace(PrintStream s) {
        System.err.println("MyException");
    }
}
