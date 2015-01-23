package ch.epfl.bbp.triechar;

import static java.util.Arrays.copyOf;
import static java.util.Arrays.copyOfRange;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @see TrieData
 * @author renaud.richardet@epfl.ch
 */
/* package */class TrieNodeData<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	T data = null;
	private char character;
	protected Map<Character, TrieNodeData<T>> children = new HashMap<Character, TrieNodeData<T>>();

	public TrieNodeData() {
	}

	public TrieNodeData(char ch) {
		this.character = ch;
	}

	public void addWord(char[] word, T data) {

		if (word.length == 0) {
			this.data = data;
			return;

		} else {
			// represent the Child Node;
			char firstChar = word[0];
			TrieNodeData<T> child = children.get(firstChar);
			if (child == null) {
				child = new TrieNodeData<T>(firstChar);
				children.put(firstChar, child);
			}

			// add remaining characters
			child.addWord(copyOfRange(word, 1, word.length), data); // recursive
		}
	}

	public T getWordData(char[] word) {
		if (word.length == 0) {
			// we are at the leaf
			return data;

		} else { // walk down the tree
			char firstChar = word[0];
			TrieNodeData<T> child = children.get(firstChar);
			if (child == null) {
				// no entry for that word
				return null;
			} else {
				return child.getWordData(copyOfRange(word, 1, word.length)); // recursive
			}
		}
	}

	@Override
	public String toString() {
		return character + ":" + data;
	}

	public void writeIds(BufferedWriter writer, char[] prefix)
			throws IOException {

		if (data != null) {
			for (char c : prefix) {
				writer.write(c);
			}
			writer.write(character);
			writer.write("\t" + data.toString());
			writer.newLine();
		}

		for (TrieNodeData<T> child : children.values()) {
			char[] childPrefix = copyOf(prefix, prefix.length + 1);
			childPrefix[childPrefix.length - 1] = character;
			child.writeIds(writer, childPrefix); // recursive
		}
	}
}