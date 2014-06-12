package datastructure;

import org.junit.Test;

/**
 * Created by shiyao on 5/18/14.
 */
public class TrieTreeTest {
    @Test
    public void case1() throws Exception {
        TrieTree trie = new TrieTree();
        trie.insert("shiyao");
        trie.insert("shiyaohao");
        trie.insert("shi");
        System.out.println(trie.find("shiyao"));
        System.out.println(trie.find("shi"));
        System.out.println(trie.find("shiy"));
        System.out.println(trie.find("shiyaohao"));

        trie.delete("shiyao");
        System.out.println(trie.find("shiyao"));
        System.out.println(trie.find("shi"));
        System.out.println(trie.find("shiy"));
        System.out.println(trie.find("shiyaohao"));
    }
}
