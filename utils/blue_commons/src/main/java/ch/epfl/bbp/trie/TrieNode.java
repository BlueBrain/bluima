package ch.epfl.bbp.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @see Trie
 * @author renaud.richardet@epfl.ch
 */
@Deprecated
class TrieNode {

    int cnt = 0;
    private char character;
    private Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();

    public TrieNode() {
    }

    public TrieNode(char ch) {
        this.character = ch;
    }

    public void addWord(String word) {

        if (word.length() == 0) {
            cnt++;
            return;

        } else {
            // represent the Child Node;
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null) {
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }

            // add remaining characters
            child.addWord(word.substring(1)); // recursive
        }
    }

    public int getWord(String word) {
        if (word.length() == 0) {
            // we are at the leaf
            return cnt;

        } else { // walk down the tree
            char firstChar = word.charAt(0);
            TrieNode child = children.get(firstChar);
            if (child == null) {
                // no entry for that word
                return 0;
            } else {
                return child.getWord(word.substring(1)); // recursive
            }
        }
    }

    @Override
    public String toString() {
        return character + "";
    }
}