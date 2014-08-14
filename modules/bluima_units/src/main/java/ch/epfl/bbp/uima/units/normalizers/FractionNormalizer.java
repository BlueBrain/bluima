package ch.epfl.bbp.uima.units.normalizers;

import static java.lang.Float.parseFloat;

import org.apache.uima.annotator.regex.extension.Normalization;

/**
 * Parses fractions, like 9/10 or 1.5/1000
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FractionNormalizer implements Normalization {

    public String normalize(String input, String ruleId) {
        try {
            return normalizeFraction(input, ruleId);
        } catch (Exception e) {
            System.err.println(e);
        }
        return input;
    }

    public static String normalizeFraction(String input, String ruleId) {
        String[] split = input.split("/");
        return "" + (parseFloat(split[0]) / parseFloat(split[1]));
    }
}