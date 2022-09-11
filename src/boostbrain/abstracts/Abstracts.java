package boostbrain.abstracts;

import java.util.ArrayList;

public class Abstracts {
    public static void main(String[] args) {
        ArrayList<Shape> shapes = new ArrayList<>();
        Shape shape = new Circle(5);
        shapes.add(shape);
        shapes.add(new Rectangle(3,5));

        printShapes(shapes);
    }

    public static void printShapes(ArrayList<Shape> shapes) {
        for (Shape shape : shapes) {
            System.out.println("Name: " + shape.getName());
            System.out.println("Square: " + shape.getSquare());
            System.out.println("Color: " + shape.getColor());
        }
    }

}
