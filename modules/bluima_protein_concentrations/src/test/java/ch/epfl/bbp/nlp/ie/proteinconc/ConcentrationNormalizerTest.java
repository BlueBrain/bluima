package ch.epfl.bbp.nlp.ie.proteinconc;

import ch.epfl.bbp.nlp.ie.proteinconc.normalizer.ConcentrationNormalizer;
import ch.epfl.bbp.nlp.ie.proteinconc.normalizer.UnitNormalizer.ValueUnitWrapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ConcentrationNormalizerTest {

	private ConcentrationNormalizer normalizer;
	private final static String CONVERSION_ERROR = "Wrong conversion";

	@Before
	public void setUp() throws Exception {
		normalizer = new ConcentrationNormalizer();
	}

	@Test
	public void testMolarity() throws Exception {

		ValueUnitWrapper wrapper = normalizer.normalize(10, "millimolar");
		assertEquals(CONVERSION_ERROR, 0.01, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "molar");
		assertEquals(CONVERSION_ERROR, 10, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "nmolar");
		assertEquals(CONVERSION_ERROR, 1e-8, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "microM");
		assertEquals(CONVERSION_ERROR, 0.00001, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "M");
		assertEquals(CONVERSION_ERROR, 10, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "nM");
		assertEquals(CONVERSION_ERROR, 1e-8, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());

		wrapper = normalizer.normalize(10, "mol/dm3");
		assertEquals(CONVERSION_ERROR, 10, wrapper.getValue());
		Assert.assertEquals("molar", wrapper.getUnit());
	}

	@Test
	public void testMass() throws Exception {
		ValueUnitWrapper wrapper = normalizer.normalize(163, "mg/km3");
		assertEquals(CONVERSION_ERROR, 1.63e-13, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "mg/m3");
		assertEquals(CONVERSION_ERROR, 0.000163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "g/m3");
		assertEquals(CONVERSION_ERROR, 0.163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "mg/mm3");
		assertEquals(CONVERSION_ERROR, 163000, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "g/l");
		assertEquals(CONVERSION_ERROR, 163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "g/L");
		assertEquals(CONVERSION_ERROR, 163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "g/mL");
		assertEquals(CONVERSION_ERROR, 163000, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "µg/ml");
		assertEquals(CONVERSION_ERROR, 0.163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "µg/µL");
		assertEquals(CONVERSION_ERROR, 163, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(163, "dg/µL");
		assertEquals(CONVERSION_ERROR, 16300000, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());

		wrapper = normalizer.normalize(245.6, "ng/ml");
		assertEquals(CONVERSION_ERROR, 0.0002456, wrapper.getValue());
		Assert.assertEquals("kg/m3", wrapper.getUnit());
	}

	private void assertEquals(String m, double a, double b) {
		Assert.assertEquals(a, b, 0.0000001);
	}
}
