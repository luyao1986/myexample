package algorithm;

import java.util.ArrayList;

/**
 * Created by shiyao on 5/18/14.
 */
public class BinarySearch {
    private static final int NOTFOUND = -1;
    public static <E extends Comparable<? super E>> int binarySearch(ArrayList<E> arrays, E data) {
        int low = 0;
        int high = arrays.size()-1;
        while(low <= high) {
            int middle = low+(high-low)/2;
            if(arrays.get(middle).compareTo(data) == 0) {
                return middle;
            }
            else if(arrays.get(middle).compareTo(data) < 0) {
                low = middle+1;
            }
            else {
                high = middle-1;
            }
        }
        return NOTFOUND;
    }

    public static void main(String[] args) {
        ArrayList<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(3);
        System.out.println(binarySearch(values, 2));
        System.out.println(binarySearch(values, 1));
        System.out.println(binarySearch(values, 3));
    }
}
