package leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiyao on 5/15/14.
 */
public class TwoSUm {
    public static int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> numIndiceMap = new HashMap<>();
        int smallIndice = 0;
        int bigIndice = 0;
        for (int i = 0; i < numbers.length; i++) {
            if(numIndiceMap.get(target-numbers[i]) == null) {
                numIndiceMap.put(numbers[i], i);
            }
            else {
                smallIndice = numIndiceMap.get(target-numbers[i])+1;
                bigIndice = i+1;
                break;
            }
        }
        return new int[]{smallIndice, bigIndice};
    }

    public static void main(String[] args) {
        int[] nums = new int[] {1, 3, 5, 7};
        int[] result = twoSum(nums, 8);
        System.out.println(result[0]+" "+result[1]);
    }
}
