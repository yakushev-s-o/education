package boostbrain.abstracts;

public class Circle extends Shape{
    private double radius;

    public Circle(double radius) {
        super("Red");
        this.radius = radius;
    }

    @Override
    public String getName() {
        return "Circle";
    }

    @Override
    public double getSquare() {
        return 3.1415926 * radius * radius;
    }
}
