package stepik.java_basic;

public class E2S4_MergeArrays {
    /**
     * Merges two given sorted arrays into one
     *
     * @param a1 first sorted array
     * @param a2 second sorted array
     * @return new array containing all elements from a1 and a2, sorted
     */
    public static int[] mergeArrays(int[] a1, int[] a2) {
        int[] resArr = new int[a1.length + a2.length];
        int count1 = 0;
        int count2 = 0;

        while (count1 < a1.length || count2 < a2.length) {
            if (count1 < a1.length && count2 < a2.length) {
                resArr[count1 + count2] = a1[count1] < a2[count2] ? a1[count1++] : a2[count2++];
            } else  {
                resArr[count1 + count2] = count1 < a1.length ? a1[count1++] : a2[count2++];
            }
        }

        return resArr;
    }
}