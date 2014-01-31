package ch.epfl.bbp.nlp.ie.proteinconc.normalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A unit normalizer specialized to normalize concentration.
 *
 * It normalizes unit of molarity as well as unit confronting
 * weight and volumes (kg/m3, g/L, ...)
 *
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 */
public class ConcentrationNormalizer implements UnitNormalizer {

    //The different families of concentration unit
    //this normalizer is able to normalize.
    private static final String MOLAR_UNIT_CLASS_REGEX = "(milli|micro|nano|µ|μ|m|n)?(molar|mole|M)";
    private static final String MOLAR_NORMALIZED_UNIT = "molar";

    private static final String MASS_UNIT_CLASS_REGEX = "(k|d|m|M|µ|μ|n)?(g)/(k|d|m|M|µ|μ)?(l|L|m3)";
    private static final String MASS_NORMALIZED_UNIT = "kg/m3";

    //used to compile the regex above only once
    private final Pattern mMolarUnitPattern;
    private final Pattern mMassUnitPatter;

    //contains the power of ten corresponding to a given SI prefix
    private final Map<String, Double> mSIPrefixes;


    public ConcentrationNormalizer() {
        mSIPrefixes = new HashMap<String, Double>();

        mSIPrefixes.put("mega", 1000000.0);
        mSIPrefixes.put("M", mSIPrefixes.get("mega"));

        mSIPrefixes.put("kilo", 1000.0);
        mSIPrefixes.put("k", mSIPrefixes.get("kilo"));

        mSIPrefixes.put("deci", 0.1);
        mSIPrefixes.put("d", mSIPrefixes.get("deci"));

        mSIPrefixes.put("milli", 0.001);
        mSIPrefixes.put("m", mSIPrefixes.get("milli"));

        mSIPrefixes.put("micro", 0.000001);
        mSIPrefixes.put("µ", mSIPrefixes.get("micro"));
        mSIPrefixes.put("μ", mSIPrefixes.get("micro"));

        mSIPrefixes.put("nano", 0.000000001);
        mSIPrefixes.put("n", mSIPrefixes.get("nano"));

        mMolarUnitPattern = Pattern.compile(MOLAR_UNIT_CLASS_REGEX);
        mMassUnitPatter = Pattern.compile(MASS_UNIT_CLASS_REGEX);
    }

    @Override
    public ValueUnitWrapper normalize(final double value, final String unit)
            throws UnknownUnitException {
        if (unit.matches(MOLAR_UNIT_CLASS_REGEX)) {
            return normalizeMolarUnit(value, unit);
        } else if (unit.matches(MASS_UNIT_CLASS_REGEX)) {
            return normalizeMassUnit(value, unit);
        } else {
            return normalizeSpecialCases(value, unit);
        }
    }

    //deals with the normalization of special cases
    private ValueUnitWrapper normalizeSpecialCases(double value, String unit)
            throws UnknownUnitException {
        if (unit.equals("mol/dm3")) {
            return new ValueUnitWrapper(value, MOLAR_NORMALIZED_UNIT);
        } else {
            throw new UnknownUnitException(unit);
        }
    }

    //deals with the normalization of unit confronting weight and volume
    private ValueUnitWrapper normalizeMassUnit(final double value, final String unit)
            throws UnknownUnitException {
        Matcher matcher = mMassUnitPatter.matcher(unit);
        if (matcher.find()) {

            double normalizationFactor = 1.0;

            String numeratorSIPrefix = matcher.group(1);
            Double numeratorFactor = getSIFactor(numeratorSIPrefix);
            if (numeratorFactor != null) {
                normalizationFactor *= numeratorFactor;
            }

            String denominatorSIPrefix = matcher.group(3);
            String denominatorUnit = matcher.group(4);
            Double denominatorFactor = getSIFactor(denominatorSIPrefix);
            if (denominatorFactor != null) {
                int power = (denominatorUnit.endsWith("3")) ? 3 : 1;
                normalizationFactor /= Math.pow(denominatorFactor, power);
            }

            if (denominatorUnit.equals("l") || denominatorUnit.equals("L")) {
                normalizationFactor *= 1000;
            }

            assert mSIPrefixes.get("kilo") != null : "kilo seems not to be in the table !";
            double normalizedValue = (normalizationFactor * value) / mSIPrefixes.get("kilo");
            return new ValueUnitWrapper(normalizedValue, MASS_NORMALIZED_UNIT);

        } else {
            throw new UnknownUnitException(unit);
        }
    }

    //deals with normalization of measure of molarity
    private ValueUnitWrapper normalizeMolarUnit(final double value, final String unit)
            throws UnknownUnitException {
        Matcher matcher = mMolarUnitPattern.matcher(unit);

        if (matcher.find()) {
            String siPrefix = matcher.group(1);
            Double convFactor = getSIFactor(siPrefix);

            if (convFactor == null) {
                throw new UnknownUnitException(unit);
            } else {
                double normalizedValue = convFactor * value;
                return new ValueUnitWrapper(normalizedValue, MOLAR_NORMALIZED_UNIT);
            }
        } else {
            throw new UnknownUnitException(unit);
        }
    }

    //helper to avoid testing for null after having extraction a SI prefix
    //when one want to have the corresponding factor
    private Double getSIFactor(String siPrefix) {
        return (siPrefix == null) ? 1 : mSIPrefixes.get(siPrefix);
    }

}
