package ch.epfl.bbp.triechar;

import static java.util.Arrays.asList;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Trie implementation to store data (a payload) and retrieve it.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class TrieData<T> implements Iterable<TrieNodeData<T>>, Serializable {
    private static final long serialVersionUID = 1L;

    private TrieNodeData<T> root;
    private boolean caseSensitive;

    public TrieData() {
        this(false);
    }

    public TrieData(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        root = new TrieNodeData<T>();
    }

    public void addWord(String word, T data) {
        if (!caseSensitive)
            word = word.toLowerCase();
        root.addWord(root, word.toCharArray(), data);
    }

    /** @return the data of that word in the trie */
    public T getWordData(String word) {
        if (!caseSensitive)
            word = word.toLowerCase();
        return root.getWordData(word.toCharArray());
    }

    /** one entry per line. word first, then tab, then data */
    public void toIdFile(OutputStream os) throws IOException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os));
        for (TrieNodeData<T> child : root.children.values()) {
            child.writeIds(writer, new char[0]);// recursive
        }
        writer.close();
    }

    @Override
    public Iterator<TrieNodeData<T>> iterator() {

        return new Iterator<TrieNodeData<T>>() {

            Queue<TrieNodeData<T>> queue = new LinkedList<TrieNodeData<T>>(
                    asList(root));

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public TrieNodeData<T> next() {
                TrieNodeData<T> head = queue.poll();
                queue.addAll(head.children.values());
                return head;
            }

            @Override
            public void remove() {
                throw new IllegalArgumentException();
            }
        };
    }
}