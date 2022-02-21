package devcolibri.oop.impl;

import devcolibri.oop.people.People;

public class DriverBus implements Driver, Transport{

    private People people;
    private String category;
    private int speed = 60;

    @Override
    public void setCategory(String c) {
        this.category = c;
        people = new People("Bob", "Kelso", 45 ,null);
    }

    @Override
    public void go() {
        if(people != null) {
            System.out.println("Go!");
        } else {
            System.out.println("STOP!");
        }
    }

    @Override
    public int getSpeed() {
        return this.speed;
    }
}
