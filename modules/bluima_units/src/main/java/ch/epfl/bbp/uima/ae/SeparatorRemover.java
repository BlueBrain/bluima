package ch.epfl.bbp.uima.ae;

/**
 * Removes TOKEN_SEPARATOR in textValue. Used by RegexAnnotator
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SeparatorRemover implements
		org.apache.uima.annotator.regex.extension.Normalization {

	public String normalize(String input, String ruleId) {
		try {
			return input.replaceAll(RegExAnnotator.TOKEN_SEPARATOR, " ");
		} catch (Exception e) {// nope
		}
		return input;
	}
}