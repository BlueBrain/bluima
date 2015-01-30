package ch.epfl.bbp.triechar;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;

/**
 * Trie implementation to store strings (multiple occurences allowed) and
 * retrieve the frequency (count) of a word.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TrieCnt implements Serializable {
    private static final long serialVersionUID = 1L;

    private TrieNodeCnt root;
    private boolean caseSensitive;

    public TrieCnt() {
        this(false);
    }

    public TrieCnt(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        root = new TrieNodeCnt();
    }

    public void addWord(String word) {
        if (!caseSensitive)
            word = word.toLowerCase();
        root.addWord(word.toCharArray());
    }

    /** @return the frequency of that word in the trie */
    public int getWord(String word) {
        if (!caseSensitive)
            word = word.toLowerCase();
        return root.getWord(word.toCharArray());
    }

    /** one entry per line. word first, then tab, then frequency */
    public void toFrequencyFile(String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8"));
        for (TrieNodeCnt child : root.children.values()) {
            child.writeFrequencies(writer, new char[0]);// recursive
        }
        writer.close();
    }
}