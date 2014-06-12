package leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by shiyao on 5/17/14.
 */
public class IntegerToRoman {
    private static Map<String, String> mapping;
    public static String intToRoman(int num) {
        initialMappping();
        Stack<String> digits = new Stack<>();
        int k=1;
        while(num>0) {
            int tmp = num%10;
            if(tmp==0) {
                k*=10;
                num /= 10;
                continue;
            }
            if(tmp<=3) {
                digits.add(times(tmp, mapping.get(k+"S")));
            }
            else if(tmp<=5) {
                digits.add(times(5-tmp, mapping.get(k+"S"))+mapping.get(k+"M"));
            }
            else if(tmp<=8) {
                digits.add(mapping.get(k+"M")+times(tmp-5,mapping.get(k+"S")));
            }
            else {
                digits.add(times(10-tmp, mapping.get(k+"S"))+mapping.get(k*10+"S"));
            }
            k*=10;
            num /= 10;
        }
        StringBuffer sb = new StringBuffer();
        while(digits.size()>0) {
            sb.append(digits.pop());
        }
        return sb.toString();
    }

    private static String times(int i, String s) {
        if(i==0)
            return "";
        else {
            StringBuffer sb = new StringBuffer();
            while((i--) > 0)
                sb.append(s);
            return sb.toString();
        }
    }

    private static void initialMappping() {
        mapping = new HashMap<>();
        mapping.put("1S", "I");
        mapping.put("1M", "V");
        mapping.put("10S", "X");
        mapping.put("10M", "L");
        mapping.put("100S", "C");
        mapping.put("100M", "D");
        mapping.put("1000S", "M");
    }

    public static void main(String[] args) {
        int i = 6;
        System.out.println(intToRoman(i));
    }
}
