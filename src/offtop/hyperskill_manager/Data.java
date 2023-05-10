package offtop.hyperskill_manager;

import java.util.List;

public class Data {
    int id;
    String theory;
    List<String> stepList;

    public Data(int id, String theory, List<String> stepList) {
        this.id = id;
        this.theory = theory;
        this.stepList = stepList;
    }

    public int getId() {
        return id;
    }

    public String getTheory() {
        return theory;
    }

    public List<String> getStepList() {
        return stepList;
    }
}
