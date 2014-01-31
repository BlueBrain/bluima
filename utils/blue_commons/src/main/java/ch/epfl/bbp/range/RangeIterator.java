package ch.epfl.bbp.range;

import java.util.Iterator;

public class RangeIterator<T extends Comparable<T>> implements Iterator<T> {

	private Sequence<T> sequence = null;
	private T end = null;

	public RangeIterator(Sequence<T> sequence, T end) {
		this.sequence = sequence;
		this.end = end;
	}

	public boolean hasNext() {
		return sequence.value().compareTo(end) <= 0;
	}

	public T next() {
		T value = sequence.value();
		sequence = sequence.next();
		return value;
	}

	public void remove() {
		// not implemented
	}
}