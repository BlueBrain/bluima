package ch.epfl.bbp.triechar;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @see TrieCnt
 * @author renaud.richardet@epfl.ch
 */
/* package */class TrieNodeCnt implements Serializable {
    private static final long serialVersionUID = 1L;

    int cnt = 0;
    private char character;
    protected Map<Character, TrieNodeCnt> children = new HashMap<Character, TrieNodeCnt>();

    public TrieNodeCnt() {
    }

    public TrieNodeCnt(char ch) {
        this.character = ch;
    }

    public void addWord(char[] word) {

        if (word.length == 0) {
            cnt++;
            return;

        } else {
            // represent the Child Node;
            char firstChar = word[0];
            TrieNodeCnt child = children.get(firstChar);
            if (child == null) {
                child = new TrieNodeCnt(firstChar);
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
            TrieNodeCnt child = children.get(firstChar);
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

        for (TrieNodeCnt child : children.values()) {
            char[] childPrefix = Arrays.copyOf(prefix, prefix.length + 1);
            childPrefix[childPrefix.length - 1] = character;
            child.writeFrequencies(writer, childPrefix);// recursive
        }
    }

}