package leetcode;

import java.util.Stack;

/**
 * Created by shiyao on 5/15/14.
 */
public class ReversePolishNotation {
    public static int evalRPN(String[] tokens) {
        Stack<Integer> nums = new Stack<>();
        for(String token: tokens) {
            if(token.equals("+")) {
                Integer a = nums.pop();
                Integer b = nums.pop();
                nums.push(b+a);
            }
            else if(token.equals("-")) {
                Integer a = nums.pop();
                Integer b = nums.pop();
                nums.push(b-a);
            }
            else if(token.equals("*")) {
                Integer a = nums.pop();
                Integer b = nums.pop();
                nums.push(b*a);
            }
            else if(token.equals("/")) {
                Integer a = nums.pop();
                Integer b = nums.pop();
                nums.push(b/a);
            }
            else {
                nums.push(Integer.valueOf(token));
            }
        }
        return nums.pop();
    }

    public static void main(String[] args) {
        String[] case1 = new String[]{"2", "1", "+", "3", "*"};
        System.out.println(evalRPN(case1));
        case1 = new String[]{"4", "13", "5", "/", "+"};
        System.out.println(evalRPN(case1));
    }
}
