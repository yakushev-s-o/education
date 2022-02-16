package devcolibri;

public class Arrays {
    public static void main(String[] args) {

        int[] arr = new int[3];
        arr[0] = 255;
        arr[1] = 5;
        int[] arrSort = {128, 8 , 256};
        String[] arrString = new String[] {"!", "World", "Hello"};
        Object[] arrObject = {10, "hello", 'c'};

        int[][] arrTwoDimensional = new int[3][4];
        for (int i = 0; i < arrTwoDimensional.length; i++) {
            for (int j = 0; j < arrTwoDimensional[0].length; j++) {
                arrTwoDimensional[i][j] = i + j;
            }
        }

        java.util.Arrays.sort(arrSort, 0, 3);

        System.out.println(arr + "\n" + arr[0] +  " " + arr[1] + "\n" +
                java.util.Arrays.toString(arr) + "\n" +
                java.util.Arrays.toString(arrSort) + "\n" +
                arrObject[0] + " " + arrObject[1] + " " + arrObject[2] + " " + arrObject.length + "\n" +
                arrString[2] + " " +arrString[1] + arrString[0] + "\n" +
                arrTwoDimensional.length + " " + arrTwoDimensional[0].length + " " + arrTwoDimensional[0][1] + "\n" +
                java.util.Arrays.deepToString(arrTwoDimensional));

    }

}