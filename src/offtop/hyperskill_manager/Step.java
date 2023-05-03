package offtop.hyperskill_manager;

import java.util.List;

public class Step {
    int id;
    String theory;
    List<String> stepList;

    public Step(int id, String theory, List<String> stepList) {
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
