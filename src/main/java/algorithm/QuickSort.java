package algorithm;

import org.stringtemplate.v4.compiler.STParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by shiyao on 5/18/14.
 */
public class QuickSort {
    public static <E extends Comparable<? super E>> void sort(ArrayList<E> arrays) {
        quicksort(arrays, 0, arrays.size()-1);
    }

    private static <E extends Comparable<? super E>> void quicksort(ArrayList<E> arrays, int start, int end) {
        if(start>=end)
            return;
        int i = end;
        int k = start+1;
        E seperatedElement = arrays.get(start);
        while(true) {
            while(i>=k&&arrays.get(i).compareTo(seperatedElement)>0)
                i--;
            if(i==start)
                break;
            while(k<i&&arrays.get(k).compareTo(seperatedElement)<0)
                k++;
            if(k>=i) {
                break;
            }
            else {
                swap(arrays, i--, k++);
            }
        }
        if(i!=start)
            swap(arrays, i, start);
        quicksort(arrays, start, i-1);
        quicksort(arrays, i+1, end);
    }

    private static <E extends Comparable<? super E>> void swap(ArrayList<E> arrays, int i, int j) {
        E tmp = arrays.get(i);
        arrays.set(i, arrays.get(j));
        arrays.set(j, tmp);
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++) {
            ArrayList<Integer> values = new ArrayList<>();
            Random r=new java.util.Random();
            for(int j=0;j<10;j++)
                values.add(r.nextInt(20));
            print(values);
            sort(values);
            print(values);
            Object[] expected_arrays = values.toArray();
            Arrays.sort(expected_arrays);
            print(expected_arrays);
        }
    }

    private static void print(ArrayList<Integer> values) {
        for(int i=0;i<values.size();i++) {
            System.out.print(values.get(i) + " ");
        }
        System.out.println();
    }

    private static void print(Object[] values) {
        for(int i=0;i<values.length;i++) {
           System.out.print(values[i] + " ");
        }
        System.out.println();
    }
}
