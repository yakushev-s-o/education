package devcolibri.oop;

import devcolibri.oop.cat.Cat;
import devcolibri.oop.people.People;

public class Main {

    static Cat cat = new Cat();
    static People people = new People();

    public static void main(String[] args) {

        cat.setName("Murka");
        cat.setBreed("persian");
        cat.setAge(2);

        people.setName("Gordon");
        people.setLastName("Freeman");
        people.setAge(30);
        people.setCat(cat);

        System.out.println("Name: " + cat.getName() + "\n" + "Breed: " + cat.getBreed() + "\n" + "Age: " + cat.getAge() + "\n" +
                cat + "\n" +
                "Name: " + people.getName() + "\n" + "Last name: " + people.getLastName() + "\n" + "Age: " + people.getAge() + "\n" +
                people + "\n" +
                people.getCat().getName());

        cat.say();
        people.getCat().say();

    }

}
