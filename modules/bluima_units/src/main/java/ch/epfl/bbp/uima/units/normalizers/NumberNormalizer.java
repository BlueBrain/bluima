package ch.epfl.bbp.uima.units.normalizers;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class NumberNormalizer implements
		org.apache.uima.annotator.regex.extension.Normalization {

	private WrittenNumbersNormalizer writtenNumbersNormalizer;

	public NumberNormalizer() {
		this.writtenNumbersNormalizer = new WrittenNumbersNormalizer();
	}

	public String normalize(String input, String ruleId) {
		try {
			return normalizeExponents(writtenNumbersNormalizer.normalize(
					normalizeComma(input), ruleId));
		} catch (Exception e) {
			System.err.println(e);
		}
		return input;
	}

	private String normalizeComma(String input) {
		return input.replaceAll(",", "\\.");
	}

	private static final Pattern eExp = Pattern
			.compile("(.*?)[eE]([+-])?(\\d+)");
	private static final Pattern xExp = Pattern
			.compile("(.*?)[xX] ?10 ?\\(([+-])?(\\d+)(?:⊂⊃)?\\)");

	/**
	 * normalizes numbers with exponents, like 5E-5 or 5x10(-5)
	 */
	public static String normalizeExponents(String input) {
		Matcher m = eExp.matcher(input);
		if (m.find()) {

			Float number = Float.parseFloat(m.group(1));
			int exp = Integer.parseInt(m.group(3));
			boolean negative = m.group(2) != null && m.group(2).equals("-");
			if (negative)
				exp *= -1;

			return new DecimalFormat("#.##############################")
					.format(number * Math.pow(10, exp));
		}

		// TODO refactor into 1
		m = xExp.matcher(input);
		if (m.find()) {

			Float number = Float.parseFloat(m.group(1));
			int exp = Integer.parseInt(m.group(3));
			boolean negative = m.group(2) != null && m.group(2).equals("-");
			if (negative)
				exp *= -1;

			String s = new DecimalFormat("#.##############################")
					.format(number * Math.pow(10, exp));
			return s;
		}

		return input;
	}
}