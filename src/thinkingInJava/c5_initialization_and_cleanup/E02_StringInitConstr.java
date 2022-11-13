package thinkingInJava.c5_initialization_and_cleanup;

import static thinkingInJava.util.Print.*;

public class E02_StringInitConstr {

    String strDef = "def.";
    String strConstr;

    E02_StringInitConstr() {}

    E02_StringInitConstr(String s) {
        print(strConstr + " -> Значение переменной во время вызова конструктора до присваивания значения");
        strConstr = s;
    }

    public static void main(String[] args) {
        print(new E02_StringInitConstr().strDef + " -> Значение переменной присваивается перед вызовом конструктора");
        print(new E02_StringInitConstr("constr.").strConstr + " -> Значание переменной присваивается после вызова конструктора со значением переменной null");
    }
}
