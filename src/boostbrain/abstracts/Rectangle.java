package boostbrain.abstracts;

public class Rectangle extends Shape{
    private double height;
    private double width;

    public Rectangle(double height, double width) {
        super("Blue");
        this.height = height;
        this.width = width;
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    @Override
    public double getSquare() {
        return height * width;
    }
}
