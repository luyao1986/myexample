package leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by shiyao on 5/15/14.
 */
public class WordBreak {
    private static Map<String, Boolean> wordCalculated = new HashMap<>();

    public static boolean wordBreak(String s, Set<String> dict) {
        HashSet<String> words = new HashSet<>();
        for(String word: dict)
            words.add(word);
        return findWordBreak(s, words);
    }

    private static boolean findWordBreak(String s, HashSet<String> dict) {
        if(wordCalculated.get(s) != null) {
            return wordCalculated.get(s);
        }
        if(dict.contains(s)) {
            wordCalculated.put(s, true);
            return true;
        }
        else {
            for(int i=1;i<s.length();i++) {
                String pre = s.substring(0, i);
                String suffix = s.substring(i,s.length());
                if(findWordBreak(pre, dict)&&findWordBreak(suffix, dict)) {
                    wordCalculated.put(s, true);
                    return true;
                }
            }
            wordCalculated.put(s, false);
            return false;
        }
    }

    public static void main(String[] args) {
        String s = "shiyao";
        Set<String> dict = new HashSet<>();
        dict.add("shi");
        dict.add("yao");
        System.out.println(wordBreak(s, dict));
    }
}
