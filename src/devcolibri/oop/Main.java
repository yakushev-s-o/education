package devcolibri.oop;

public class Main {

    static Cat cat = new Cat();
    static People people = new People();

    public static void main(String[] args) {

        cat.name = "Murka";
        cat.breed = "persian";
        cat.age = 2;

        people.name = "Gordon";
        people.lastName = "Freeman";
        people.age = 30;
        people.cat = cat;

        System.out.println("Name: " + cat.name + "\n" + "Breed: " + cat.breed + "\n" + "Age: " + cat.age + "\n" +
                cat + "\n" +
                "Name: " + people.name + "\n" + "Last name: " + people.lastName + "\n" + "Age: " + people.age + "\n" +
                people + "\n" +
                people.cat.name);

        cat.say();
        people.cat.say();

    }

}
