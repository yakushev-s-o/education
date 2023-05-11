package offtop.hyperskill_manager.data;

import java.util.List;

public class Data {
    Topic topic_relations;
    List<String> projects;
    List<Step> steps;

    public Data(Topic topic, List<String> projects, List<Step> steps) {
        this.topic_relations = topic;
        this.projects = projects;
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
