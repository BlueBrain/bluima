package ch.epfl.bbp.range;

public interface Sequence<T> {

	public T value();

	public Sequence<T> next();

	public Sequence<T> previous();
}
