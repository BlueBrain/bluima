package ch.epfl.bbp.uima.units.normalizers;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * <code>
 * <setFeature name="normalizedFeature" type="String" 
 * 	normalization="Custom" class="ch.epfl.bbp.uima.ae.WrittenNumbersNormalizer">
 * \$0
 * </setFeature>
 * </code>
 * 
 * @see http://uima.apache.org/downloads/sandbox/RegexAnnotatorUserGuide/
 *      RegexAnnotatorUserGuide
 *      .html#sandbox.regexAnnotator.conceptsFile.annotationCreation.features
 * 
 * @author renaud.richardet@epfl.ch
 * @author https://github.com/admackin/digify
 */
public class WrittenNumbersNormalizer implements
		org.apache.uima.annotator.regex.extension.Normalization {

	public String normalize(String input, String ruleId) {
		try {
			return text2num(input) + "";
		} catch (Exception e) {// nope
		}
		return input;
	}

	static Map<String, Integer> small = new HashMap<String, Integer>();
	static Map<String, Long> magnitude = new HashMap<String, Long>();
	static {
		small.put("zero", 0);
		small.put("one", 1);
		small.put("two", 2);
		small.put("three", 3);
		small.put("four", 4);
		small.put("five", 5);
		small.put("six", 6);
		small.put("seven", 7);
		small.put("eight", 8);
		small.put("nine", 9);
		small.put("ten", 10);
		small.put("eleven", 11);
		small.put("twelve", 12);
		small.put("thirteen", 13);
		small.put("fourteen", 14);
		small.put("fifteen", 15);
		small.put("sixteen", 16);
		small.put("seventeen", 17);
		small.put("eighteen", 18);
		small.put("nineteen", 19);
		small.put("twenty", 20);
		small.put("thirty", 30);
		small.put("forty", 40);
		small.put("fifty", 50);
		small.put("sixty", 60);
		small.put("seventy", 70);
		small.put("eighty", 80);
		small.put("ninety", 90);

		magnitude.put("thousand", 1000l);
		magnitude.put("thousands", 1000l);
		magnitude.put("million", 1000000l);
		magnitude.put("millions", 1000000l);
		magnitude.put("billion", 1000000000l);
		magnitude.put("billions", 1000000000l);
		magnitude.put("trillion", 1000000000000l);
		magnitude.put("trillions", 1000000000000l);
		magnitude.put("quadrillion", 1000000000000000l);
		magnitude.put("quadrillions", 1000000000000000l);
		magnitude.put("quintillion", 1000000000000000000l);
		// magnitude.put("sextillion", 1000000000000000000000l);
		// magnitude.put("septillion", 1000000000000000000000000l);
		// magnitude.put("octillion", 1000000000000000000000000000l);
		// magnitude.put("nonillion", 1000000000000000000000000000000l);
		// magnitude.put("decillion", 1000000000000000000000000000000000l);
	}

	/**
	 * @param inputString
	 *            e.g. twelve thousand three hundred four
	 * @return e.g. 12304l
	 * @throws NumberFormatException
	 *             if it cannot be parsed
	 */
	public static Long text2num(String s) {

		String[] a = s.split("\\s|-");
		if (a.length == 0)
			throw new NumberFormatException("cannot parse " + s);

		long n = 0l, g = 0l;
		for (String w : a) {
			if (small.containsKey(w)) {
				g += small.get(w);
			} else if (w.equals("hundred")) {
				g *= 100;
			} else if (w.equals("and")) {
				// just continue
			} else if (magnitude.containsKey(w)) {
				n += g * magnitude.get(w);
				g = 0;
			} else {
				throw new NumberFormatException("cannot parse " + s);
			}
		}
		return n + g;
	}
}