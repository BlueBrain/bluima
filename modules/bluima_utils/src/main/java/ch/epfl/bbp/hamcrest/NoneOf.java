package ch.epfl.bbp.hamcrest;

import java.util.Arrays;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

/**
 * @author renaud.richardet@epfl.ch
 */
public final class NoneOf<T> extends BaseMatcher<T> {
    private final Iterable<Matcher<? extends T>> matchers;

    private NoneOf(final Iterable<Matcher<? extends T>> matchers) {
	this.matchers = matchers;
    }

    public boolean matches(final Object o) {
	for (final Matcher<? extends T> matcher : matchers) {
	    if (matcher.matches(o)) {
		return false;
	    }
	}
	return true;
    }

    public void describeTo(final Description description) {
	description.appendList("(not ", ", not ", ")", matchers);
    }

    @Factory
    public static <T> Matcher<T> noneOf(final Matcher<? extends T>... matchers) {
	return noneOf(Arrays.<Matcher<? extends T>> asList(matchers));
    }

    @Factory
    public static <T> Matcher<T> noneOf(
	    final Iterable<Matcher<? extends T>> matchers) {
	return new NoneOf<T>(matchers);
    }
}