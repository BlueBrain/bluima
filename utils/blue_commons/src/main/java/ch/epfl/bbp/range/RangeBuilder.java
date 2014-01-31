package ch.epfl.bbp.range;


public class RangeBuilder<T extends Comparable<T>> {
	private T start;

	public RangeBuilder(T start) {
		this.start = start;
	}

	public static <T extends Comparable<T>> RangeBuilder<T> from(T start) {
		return new RangeBuilder<T>(start);
	}

	public Range<T> upto(T end) {
		return new Range<T>(start, end);
	}

}