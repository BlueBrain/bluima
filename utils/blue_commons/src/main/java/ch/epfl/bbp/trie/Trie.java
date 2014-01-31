package ch.epfl.bbp.trie;

/**
 * Trie implementation to store strings (multiple times) and retrieve the
 * frequency (count) of a word.
 * 
 * @see ch.epfl.bbp.triechar.Trie instead
 * @author renaud.richardet@epfl.ch
 */
@Deprecated
class Trie {

    private TrieNode root;
    private boolean caseSensitive;

    public Trie() {
        this(false);
    }

    public Trie(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        root = new TrieNode();
    }

    public void addWord(String word) {
        if (!caseSensitive)
            word = word.toLowerCase();
        root.addWord(word);
    }

    public int getWord(String word) {
        if (!caseSensitive)
            word = word.toLowerCase();
        return root.getWord(word);
    }
}