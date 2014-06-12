package algorithm;

import java.util.ArrayList;

/**
 * Created by shiyao on 5/18/14.
 */
public class GreatestSequenceSum {
    //below method is O(N^2);
    public static int getGreatestSequenceSum(ArrayList<Integer> arrays) {
        int max = 0;
        for (int i = 0; i < arrays.size(); i++) {
            int sum = 0;
            for(int j=i;j<arrays.size();j++) {
                sum+=arrays.get(j);
                if(sum > max)
                    max = sum;
            }
        }
        return max;
    }

    //below use "divide and conquer",最大值要么在左边，要么在右边，要么横跨中间
    //F(n)=2*F(n/2)+n;  => F(n)=O(n*lnn);
    public static int getGreatestSequenceSum2(ArrayList<Integer> arrasys, int left, int right) {
        if(left == right){
            if(arrasys.get(left) > 0 )
                return arrasys.get(left);
            else return 0;
        }
        int middle = (left+right)/2;
        int leftMax = getGreatestSequenceSum2(arrasys, left, middle);
        int rightMax = getGreatestSequenceSum2(arrasys, middle + 1, right);
        int leftBoarderMax = 0;
        int sum = 0;
        for (int i = middle; i >= left; i--) {
            sum+=arrasys.get(i);
            if(sum > leftBoarderMax) { leftBoarderMax = sum; }
        }
        int rightBoarderMax = 0;
        sum = 0;
        for (int i = middle+1; i < right; i++) {
            sum+=arrasys.get(i);
            if(sum>rightBoarderMax) { rightBoarderMax=sum; }
        }
        return getMax(leftMax, rightMax, leftBoarderMax+rightBoarderMax);
    }

    private static int getMax(int value1, int ...values) {
        int max = value1;
        for(int value: values) {
            if(value>max)
                max = value;
        }
        return max;
    }

    //O(N),仅仅需要常量空间，并以线性时间运行的联机算法几乎是完美的算法。(比如，在这里，数组可以按顺序读入，而不必存储)好好理解一下
    public static int getGreatestSequenceSum3(ArrayList<Integer> arrays) {
        int max = 0;
        int sum=0;
        for (int i = 0; i < arrays.size(); i++) {
            sum+=arrays.get(i);
            if(sum>max)
                max = sum;
            else {
                if(sum<0)
                    sum=0;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        ArrayList<Integer> values = new ArrayList<>();
        values.add(4);
        values.add(-3);
        values.add(5);
        values.add(-2);
        values.add(-1);
        values.add(2);
        values.add(6);
        values.add(-2);
        System.out.println("method1: max sequence value:" + getGreatestSequenceSum(values));
        System.out.println("method2: max sequence value:" + getGreatestSequenceSum2(values, 0, values.size() - 1));
        System.out.println("method3: max sequence value:" + getGreatestSequenceSum3(values));
    }
}
