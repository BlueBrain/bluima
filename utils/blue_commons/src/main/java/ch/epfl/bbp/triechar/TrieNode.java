package ch.epfl.bbp.triechar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @see Trie
 * @author renaud.richardet@epfl.ch
 */
class TrieNode {

    int cnt = 0;
    private char character;
    protected Map<Character, TrieNode> children = new HashMap<Character, TrieNode>();

    public TrieNode() {
    }

    public TrieNode(char ch) {
        this.character = ch;
    }

    public void addWord(char[] word) {

        if (word.length == 0) {
            cnt++;
            return;

        } else {
            // represent the Child Node;
            char firstChar = word[0];
            TrieNode child = children.get(firstChar);
            if (child == null) {
                child = new TrieNode(firstChar);
                children.put(firstChar, child);
            }

            // add remaining characters
            child.addWord(Arrays.copyOfRange(word, 1, word.length)); // recursive
        }
    }

    public int getWord(char[] word) {
        if (word.length == 0) {
            // we are at the leaf
            return cnt;

        } else { // walk down the tree
            char firstChar = word[0];
            TrieNode child = children.get(firstChar);
            if (child == null) {
                // no entry for that word
                return 0;
            } else {
                return child.getWord(Arrays.copyOfRange(word, 1, word.length)); // recursive
            }
        }
    }

    @Override
    public String toString() {
        return character + "";
    }

    public void writeFrequencies(BufferedWriter writer, char[] prefix)
            throws IOException {

        if (cnt > 0) {
            for (char c : prefix) {
                writer.write(c);
            }
            writer.write(character);
            writer.write("\t" + cnt);
            writer.newLine();
        }

        for (TrieNode child : children.values()) {
            char[] childPrefix = Arrays.copyOf(prefix, prefix.length + 1);
            childPrefix[childPrefix.length - 1] = character;
            child.writeFrequencies(writer, childPrefix);// recursive
        }
    }

}