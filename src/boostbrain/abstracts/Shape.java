package boostbrain.abstracts;

public abstract class Shape {
    private String color;

    public Shape(String color) {
        this.color = color;
    }

    abstract String getName();
    abstract double getSquare();

    public String getColor() {
        return color;
    }
}
