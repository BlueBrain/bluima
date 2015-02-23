package ch.epfl.bbp.triechar;

import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import ch.epfl.bbp.io.LineReader;

/**
 * Trie implementation to store strings (multiple occurences allowed) and
 * retrieve a unique id of a word.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TrieId implements Serializable {
    protected static final long serialVersionUID = 1L;

    protected TrieData<Integer> trie;
    protected int maxId = 0;

    public TrieId() {
        this(false);
    }

    public TrieId(boolean caseSensitive) {
        trie = new TrieData<Integer>(caseSensitive);
    }

    public int getSize() {
        return maxId;
    }

    public int addWord(String word) {
        if (word == null || word.length() == 0)
            throw new IllegalArgumentException("word cannot be null or empty");
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

    @SuppressWarnings("resource")
    public static TrieId fromIdsFile(InputStream is) throws IOException {
        TrieId t = new TrieId();

        for (String line : new LineReader(is)) {
            String[] split = line.split("\t");
            if (split.length < 2) {
                throw new IllegalArgumentException(
                        "line '"
                                + line
                                + "' does not contain a tab. Format must be 'word{tab}id'");
            } else if (split.length == 2) {
                t.trie.addWord(split[0], parseInt(split[1]));
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append(split[0]);
                for (int i = 1; i < split.length - 1; i++) {
                    sb.append('\t');
                    sb.append(split[i]);
                }
                t.trie.addWord(sb.toString(), parseInt(split[split.length - 1]));
            }
        }
        return t;
    }
}