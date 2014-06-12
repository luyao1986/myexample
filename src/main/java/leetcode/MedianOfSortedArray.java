package leetcode;

/**
 * Created by shiyao on 5/15/14.
 */
public class MedianOfSortedArray {
    public static double findMedianSortedArrays(int A[], int B[]) {
        double a = getMedian(A, 0, A.length-1, B, 0, B.length-1, (A.length+B.length+1)/2);
        double b = getMedian(A, 0, A.length-1, B, 0, B.length-1, (A.length+B.length+2)/2);
        return (a+b)/2;
    }

    private static double getMedian(int A[], int startA, int endA, int B[], int startB, int endB, int nth) {
        int numA = endA - startA + 1;
        int numB = endB - startB + 1;
        int middleA = (startA+endA)/2;
        int middleB = (startB+endB)/2;
        int a = A[middleA];
        int b = B[middleB];
        if((nth > (numA + numB)/2) && (a>b) || (nth<(numA + numB)/2)&&(a<b)) {
            return getMedian(A, startA, endA, B, middleB+1, endB, nth-(middleB-startB+1));
        }
        else {
            return getMedian(A, startA, middleA-1, B, startB, endB, nth-(middleA-startA+1));
        }
    }

    public static void main(String[] args) {
        int A[] = new int[]{1,3,5};
        int B[] = new int[]{2,4};
        double median = findMedianSortedArrays(A,B);
        System.out.println(median);
    }
}
