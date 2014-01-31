package ch.epfl.bbp;

import java.util.List;
import java.util.ListIterator;

/**
 * A more natural way to perform iteration <strong>back</strong> and forward.
 * 
 * @author http://stackoverflow.com/a/13483488/125617
 * @author renaud.richardet@epfl.ch
 */
public class IteratorWithPrevious<T> {

    private final ListIterator<T> listIterator;

    private boolean nextWasCalled = false;
    private boolean previousWasCalled = false;

    public IteratorWithPrevious(ListIterator<T> listIterator) {
        this.listIterator = listIterator;
    }
    public IteratorWithPrevious(List<T> list) {
        this.listIterator = list.listIterator();
    }

    public T next() {
        nextWasCalled = true;
        if (previousWasCalled) {
            previousWasCalled = false;
            listIterator.next();
        }
        return listIterator.next();
    }

    public T previous() {
        if (nextWasCalled) {
            listIterator.previous();
            nextWasCalled = false;
        }
        previousWasCalled = true;
        return listIterator.previous();
    }

    public boolean hasNext() {
        return listIterator.hasNext();
    }
}