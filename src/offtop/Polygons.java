package offtop;

public class Polygons {
    public static double getArea(double[][] polygon) {
        int size = polygon.length;
        double sum = 0;
        for (int i = 0; i < size; ++i) {
            int j = (i + 1) % size;
            sum += det(polygon[i][0], polygon[i][1], polygon[j][0], polygon[j][1]);
        }
        return Math.abs(sum / 2);
    }

    private static double det(double x1, double y1, double x2, double y2) {
        return x1 * y2 - x2 * y1;
    }

    public static void main(String[] args) {
        double[][] polygon = new double[][]{{1, 1}, {1, 2}, {2, 2}, {2, 1}};
        System.out.printf("Polygon area = %1.3f\n", getArea(polygon));
        int a = 2;
        System.out.println(Integer.reverse(a));
    }
}