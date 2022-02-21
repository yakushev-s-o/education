package devcolibri.oop;

import devcolibri.oop.cat.Cat;
import devcolibri.oop.people.People;
import devcolibri.oop.professions.Sportsman;

import java.lang.reflect.Array;

public class Main {

    static Cat cat;
    static People people;

    public static void main(String[] args) {

        cat = new Cat(2, "persian", "Murka");
//        cat.setName("Murka");
//        cat.setBreed("persian");
//        cat.setAge(2);

        people = new People("Gordon", "Freeman", 30, cat);
//        people.setName("Gordon");
//        people.setLastName("Freeman");
//        people.setAge(30);
//        people.setCat(cat);

        System.out.println("Name: " + cat.getName() + "\n" + "Breed: " + cat.getBreed() + "\n" + "Age: " + cat.getAge() + "\n" +
                cat + "\n" +
                "Name: " + people.getName() + "\n" + "Last name: " + people.getLastName() + "\n" + "Age: " + people.getAge() + "\n" +
                people + "\n" +
                people.getCat().getName());

        cat.say();
        people.getCat().say();

        Sportsman sportsman = new Sportsman("Alex", "Vens", 27, null);
        sportsman.setTypeSport("gymnast");
        System.out.println(sportsman.getName() + " " + sportsman.getLastName() + " " +
                sportsman.getAge() + " " + sportsman.getTypeSport());

        people.say();
        sportsman.say();

        cat.eat();

    }

}
