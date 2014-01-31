package ch.epfl.bbp.hamcrest;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * @author renaud.richardet@epfl.ch
 */
public class ShorterThan extends TypeSafeMatcher<String> {

    private int length;

    public ShorterThan(int length) {
	this.length = length;
    }

    @Override
    public boolean matchesSafely(String string) {
	return string == null || string.length() < length;
    }

    public void describeTo(Description description) {
	description.appendText("not a number");
    }

    @Factory
    public static <T> Matcher<String> shorterThan(int length) {
	return new ShorterThan(length);
    }

}