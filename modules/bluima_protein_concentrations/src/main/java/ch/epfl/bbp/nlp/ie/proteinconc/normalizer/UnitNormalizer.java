package ch.epfl.bbp.nlp.ie.proteinconc.normalizer;

/**
 * A generic interface for unit normalization.
 *
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 */
public interface UnitNormalizer {
    /**
     * The generic result format of a unit normalizer.
     *
     * @author Philémon Favrod (philemon.favrod@epfl.ch)
     */
	public class ValueUnitWrapper {
		private double mValue;
		private String mUnit;
		
		public ValueUnitWrapper(final double value, final String unit) {
			mValue = value;
			mUnit = unit;
		}

		public final double getValue() {
			return mValue;
		}

		public final String getUnit() {
			return mUnit;
		}
		
	}

    /**
     * Exception thrown when a unit normalizer does not
     * know how to normalize given unit.
     */
	@SuppressWarnings("serial")
	public class UnknownUnitException extends Exception {
		public UnknownUnitException(String unit) {
			super("Unkown unit exception: "+unit);
		}
	}

    /**
     * Normalizes the measure given by value and unit
     * @param value - the value of the not normalized measure
     * @param unit - the unit of the not normalized measure
     * @return a wrapper which encapsulates the normalized unit
     * @throws UnknownUnitException when unit is not known
     */
	public ValueUnitWrapper normalize(final double value, final String unit)
		throws UnknownUnitException;

}
