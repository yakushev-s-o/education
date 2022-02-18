package devcolibri.oop;

public class Cat {

    int age;
    String breed;
    String name;

    void say() {
        System.out.println("meow");
    }

    @Override
    public String toString() {
        return "Cat{" +
                "age=" + age +
                ", breed='" + breed + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
