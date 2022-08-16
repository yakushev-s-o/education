package thinkingInJava.operators;

class Dog {
    String name;
    String says;

    Dog(String name, String says){
        this.name = name;
        this.says = says;
    }

    public boolean equals(Object o) {
        Dog other = (Dog)o;
        return this.name == other.name
                && this.says == other.says;
    }
}

public class EqualsMethod {
    public static void main(String[] args) {
        Dog dog1 = new Dog("Spot", "Woof");
        Dog dog2 = new Dog("Scruffy", "WoffWoff");
        Dog dog3 = dog1;
        Dog dog4 = new Dog("Spot", "Woof");

        System.out.println("dog1 == dog2 -> " + (dog1 == dog2));
        System.out.println("dog2 == dog3 -> " + (dog2 == dog3));
        System.out.println("dog1 == dog3 -> " + (dog1 == dog3));
        System.out.println("dog4 == dog1 -> " + (dog4 == dog1));
        System.out.println("dog1.equals(dog2) -> " + (dog1.equals(dog2)));
        System.out.println("dog2.equals(dog3) -> " + (dog2.equals(dog3)));
        System.out.println("dog1.equals(dog3) -> " + (dog1.equals(dog3)));
        System.out.println("dog4.equals(dog1) -> " + (dog4.equals(dog1)));
    }
}
