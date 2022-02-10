package devcolibri;

public class Loop {

    static int first = 9;
    static int second = 9;
    static int k = 1;
    static int l = 1;
    static int[] arr = {1,2,3,4,5,6,7,8,9};

    public static void main(String[] args) {

        /*
        While Loop
         */

        System.out.println("While Loop");
        while(k <= first) {
            while (l <= second) {
                System.out.print((k * l) + "\t");
                l++;
            }
            System.out.print("\n");
            k++;
            l = 1;
        }
        k = 1;

        /*
        Do..while Loop
         */

        System.out.println("Do..while Loop");
        do {
            do {
                System.out.print((k * l) + "\t");
                l++;
            } while (l <= second);
            System.out.print("\n");
            k++;
            l = 1;
        } while (k <= first);
        k = 1;

        /*
        For Loop
         */

        System.out.println("For Loop");
        for (int i = 1; i <= first; i++) {
            for (int j = 1; j <= second; j++) {
                System.out.print((i * j) + "\t");
            }
            System.out.print("\n");
        }

        /*
        Foreach Loop
         */

        System.out.println("Foreach Loop");
        for (int arr1 : arr) {
            if (k <= first) {
                for (int arr2 : arr) {
                    System.out.print((arr1 * arr2) + "\t");
                }
                System.out.print("\n");
            }
        }

    }
}
