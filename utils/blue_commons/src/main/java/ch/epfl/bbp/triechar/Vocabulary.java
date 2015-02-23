package ch.epfl.bbp.triechar;

import java.util.Iterator;

import ch.epfl.bbp.triechar.TrieId;
import ch.epfl.bbp.triechar.TrieNodeData;

/**
 * A vocabulary, backed by a {@link TrieId} trie.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Vocabulary extends TrieId {
    private static final long serialVersionUID = 1L;

    TrieNodeData<Integer>[] nodes; // for backtracking

    public Vocabulary(boolean caseSensitive) {
        super(caseSensitive);
    }

    @SuppressWarnings("unchecked")
    public void updateBacktracking() {

        nodes = new TrieNodeData[getSize()];

        Iterator<TrieNodeData<Integer>> it = trie.iterator();
        it.next();// skip root

        while (it.hasNext()) {
            TrieNodeData<Integer> node = it.next();
            Integer id = node.getData();
            if (id != null) {
                nodes[id] = node;
            }
        }
    }

    public String getWord(int id) {
        if (nodes == null)
            updateBacktracking();
        TrieNodeData<Integer> node = nodes[id];

        StringBuilder sb = new StringBuilder();
        node.getWord(sb);

        return sb.toString();
    }

}
