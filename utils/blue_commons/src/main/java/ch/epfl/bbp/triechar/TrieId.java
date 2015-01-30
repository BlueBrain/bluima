package ch.epfl.bbp.triechar;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * Trie implementation to store strings (multiple occurences allowed) and
 * retrieve a unique id of a word.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TrieId implements Serializable {
    private static final long serialVersionUID = 1L;

    private TrieData<Integer> trie;
    private int maxId = 0;

    public TrieId() {
        this(false);
    }

    public TrieId(boolean caseSensitive) {
        trie = new TrieData<Integer>(caseSensitive);
    }

    public int addWord(String word) {
        Integer id = trie.getWordData(word);
        if (id == null) {
            id = maxId;
            trie.addWord(word, id);
            maxId++;
            return id;
        } else {
            return id;
        }
    }

    /**
     * @return the id of that word in the trie, or -1 if this word is not in the
     *         trie
     */
    public int getWordId(String word) {
        try {
            return trie.getWordData(word);
        } catch (Exception e) {
            return -1;
        }
    }

    public void toIdsFile(OutputStream os) throws IOException {
        trie.toIdFile(os);
    }
}