package ch.epfl.bbp.range;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Range<T extends Comparable<T>> implements Iterable<T> {

	String packg = Range.class.getPackage().getName();

	private T from;
	private T to;

	public Range(T from, T to) {
		this.from = from;
		this.to = to;
	}

	@SuppressWarnings("unchecked")
	public Iterator<T> iterator() {

		Sequence<T> sequence = null;

		String className = packg + "." + from.getClass().getSimpleName()
				+ "Sequence";
		try {
			Class<?> clazz = Class.forName(className);

			sequence = (Sequence<T>) clazz.getDeclaredConstructor(
					from.getClass()).newInstance(from);

		} catch (Exception e) {
			throw new RuntimeException("No Sequence found for type "
					+ from.getClass());
		}

		return new RangeIterator<T>(sequence, to);
	}

	public List<T> asList() {
		List<T> list = new ArrayList<T>();
		for (T t : this) {
			list.add(t);
		}
		return list;
	}
}
