package devcolibri.oop.professions;

import devcolibri.oop.cat.Cat;
import devcolibri.oop.people.People;

public class Sportsman extends People {

    private String typeSport = "pidr";

    public Sportsman(String name, String lastName, int age, Cat cat) {

        super(name, lastName, age, cat);

    }

    @Override
    public void say() {
        System.out.print("Sportsman: ");
        super.say();
    }

    public String getTypeSport() {
        return typeSport;
    }

    public void setTypeSport(String typeSport) {
        this.typeSport = typeSport;
    }
}
