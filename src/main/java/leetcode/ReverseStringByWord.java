package leetcode;

import java.util.Stack;

/**
 * Created by shiyao on 5/15/14.
 */
public class ReverseStringByWord {

    public static String reverseWords(String s) {
        StringBuilder sb = new StringBuilder();
        String[] words = s.split(" ");
        boolean first = true;
        for(int i=words.length-1;i>=0;i--) {
            if(!words[i].equals("")) {
                if(first) {
                    sb.append(words[i]);
                    first = false;
                }
                else {
                    sb.append(" " + words[i]);
                }
            }
        }
        return sb.toString();
    }

    public static String reverseWords2(String s) {
        StringBuilder sb = new StringBuilder();
        Stack<String> words = new Stack<>();
        for(String word: s.split(" ")) {
            if(!word.equals(""))
                words.push(word);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        String a = " i am    shiyao  ";
        System.out.println(reverseWords(a));
    }
}



