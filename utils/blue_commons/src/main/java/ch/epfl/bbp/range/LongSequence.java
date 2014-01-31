package ch.epfl.bbp.range;

public class LongSequence implements Sequence<Long> {

	Long value = null;

	public LongSequence(Long value) {
		this.value = value;
	}

	public Long value() {
		return value;
	}

	public Sequence<Long> next() {
		return new LongSequence(value + 1);
	}

	public Sequence<Long> previous() {
		return new LongSequence(value - 1);
	}
}