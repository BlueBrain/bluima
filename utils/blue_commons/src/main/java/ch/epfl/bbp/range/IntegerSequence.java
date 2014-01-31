package ch.epfl.bbp.range;

public class IntegerSequence implements Sequence<Integer> {

	Integer value = null;

	public IntegerSequence(Integer value) {
		this.value = value;
	}

	public Integer value() {
		return value;
	}

	public Sequence<Integer> next() {
		return new IntegerSequence(value + 1);
	}

	public Sequence<Integer> previous() {
		return new IntegerSequence(value - 1);
	}
}