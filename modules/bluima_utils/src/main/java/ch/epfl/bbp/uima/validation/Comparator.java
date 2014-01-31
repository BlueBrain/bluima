package ch.epfl.bbp.uima.validation;

/**
 * @author renaud.richardet@epfl.ch
 * 
 * @param <T>
 *            expected
 * @param <U>
 *            actual
 */
public interface Comparator<T, U> {

    public boolean areTheSame(T expected, U actual);
}
