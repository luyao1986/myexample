package datastructure;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shiyao on 5/17/14.
 */
public class TrieTree {
    public TrieNode root;
    TrieTree() {
       root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        word = word.trim();
        for (int i = 0; i < word.length(); i++) {
            if (!(node.children.containsKey(word.charAt(i)))) {
                node.children.put(word.charAt(i), new TrieNode());
            }
            else {
                node.children.get(word.charAt(i)).prefixNum++;
            }
            node = node.children.get(word.charAt(i));
//            node.prefixNum++;
        }
        node.terminable = true;
    }

    public boolean find(String word) {
        TrieNode node = root;
        for(int i = 0; i < word.length(); i++) {
            if(!(node.children.containsKey(word.charAt(i)))) {
                return false;
            } else {
                node = node.children.get(word.charAt(i));
            }
        }
        return node.terminable;  // 即便该字符串在Trie路径中，也不能说明该单词已存在，因为它有可能是某个子串
    }

    public void delete(String word) {
        if(!find(word)) {
            System.out.println("no this word.");
        }
        else {
            TrieNode node = root;
            for(int i = 0; i < word.length(); i++) {
                node = node.children.get(word.charAt(i));
                if(node.prefixNum == 1) {
                    node = null;
                }
                else {
                    node.prefixNum--;
                    if(i==word.length()-1) {
                        node.terminable = false;
                    }
                }
            }
        }
    }

}

class TrieNode {
    boolean terminable;  // 是不是单词结尾,we can add any field which is useful if we like
    int prefixNum;  // 前缀出现次数,在我们删除一个单词的时候用到(从根结点到该单词的prefixNum==1则可以删除，不然prefixNum--)；
    Map<Character, TrieNode> children = null;    //如果确定处理的只涉及26个英文字母，用一个26个大小的数组来存储子结点是个不错的选择，且比较简单。

    TrieNode() {
        terminable = false;
        prefixNum = 1;
        children = new HashMap<>();
    }
}
