package ch.epfl.bbp.uima.units.normalizers;

import static org.junit.Assert.*;

import org.junit.Test;

public class NumberNormalizerTest {

	@Test
	public void test() {
		assertEquals("500", NumberNormalizer.normalizeExponents("5E2"));
		assertEquals("0.05", NumberNormalizer.normalizeExponents("5E-2"));
		assertEquals("1200", NumberNormalizer.normalizeExponents("12x10(2)"));
		assertEquals("0.0005", NumberNormalizer.normalizeExponents("5x10(-4)"));
		assertEquals("0.0005", NumberNormalizer.normalizeExponents("5x10(-4⊂⊃)"));
	}

}
