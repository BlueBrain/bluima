package cc.mallet.pipe.tsf.transform;

/**
 * Class with static methods to get common TokenTransformer's within
 * the task of Named Entity Recognition (NER)
 * 
 * @author jmcejuela
 *
 */
public class CommonNerTokenTransformers {
	
	private static final TokenTransformer ToLowerCaseTransformer = new TokenTransformer() {		
		@Override
		public String transform(String token) {
			return token.toLowerCase();			
		}
	};
	/**
	 * Return TokenTransformer that transforms tokens to lower case.
	 * 
	 * All tokens are accepted.
	 * 
	 * @return
	 */
	public static TokenTransformer getLowerCaseTransformer() {
		return ToLowerCaseTransformer;
	}
	
	
	private static final TokenTransformer morphologyUpperLowerNumber = new TokenTransformer() {		
		@Override
		public String transform(String token) {
			return token.replaceAll("\\p{Lu}", "A").
						 replaceAll("\\p{Ll}", "a").
						 replaceAll("\\p{N}" , "1");
		}
	};
	/**
	 * Return TokenTransformer that transforms tokens to defined
	 * Upper-Lower-Number Morphology.
	 * 
	 * This morphology transforms all upper case letters to 'A',
	 * all lower case letters to 'a',
	 * all numbers to '1'
	 * All other symbols are not transformed.
	 * 
	 * All tokens are accepted.
	 * 
	 * Examples:
	 * <ul>
	 * <li>miau -> aaaa</li>
	 * <li>GnRH -> AaAA</li>
	 * <li>s2 -> a1</li>
	 * <li>rp47phox -> aa11aaaa</li>
	 * <li>U937 -> A111</li>
	 * <li>U23k9 -> A11a1</li>
	 * <li>. -> .</li>
	 * </ul>
	 * 
	 * @return 
	 */
	public static TokenTransformer getMorphologyUpperLowerNumberTransformer() {
		return morphologyUpperLowerNumber;
	}
	
	
	private static final String removeEqualContiguous(String in) {
		if (in.isEmpty())
			return in;
		StringBuilder sb = new StringBuilder();
		//Simple method to make sure we don't stupidly discard the first character
		//Still, given input strings typically should not contain whitespace characters
		//I find this simpler than splitting the first char and then using charAt or getChars 
		char previous = (in.charAt(0) == '\n') ? ' ' : '\n';
		for (char c : in.toCharArray()) {
			if (c != previous) {
				sb.append(c);
				previous = c;
			}
		}
		return sb.toString();
	}
	/**
	 * Return TokenTransformer that transforms tokens to defined
	 * Upper-Lower-Number-Non-Contiguous Morphology.
	 * 
	 * This morphology removes contiguous equal characters after 
	 * applying the transformation Upper-Lower-Number Morphology
	 * @see #getMorphologyUpperLowerNumberTransformer()
	 * 
	 * All tokens are accepted.
	 * 
	 * Examples:
	 * <ul>
	 * <li>miau -> a</li>
	 * <li>GnRH -> AaA</li>
	 * <li>s2 -> a1</li>
	 * <li>rp47phox -> a1a</li>
	 * <li>U937 -> A1</li>
	 * <li>U23k9 -> A1a1</li>
	 * <li>. -> .</li>
	 * </ul>
	 * 
	 * @return 
	 */
	private static final TokenTransformer morphologyUpperLowerNumberNonContiguous = new TokenTransformer() {		
		@Override
		public String transform(String token) {
			return removeEqualContiguous(morphologyUpperLowerNumber.transform(token));
		}
	};
	public static TokenTransformer getMorphologyUpperLowerNumberNonContiguousTransformer() {
		return morphologyUpperLowerNumberNonContiguous;
	}
	
	
	private static final TokenTransformer morphologyContiguousNumbersGroupedOrRejected = new TokenTransformer() {		
		@Override
		public String transform(String token) {
			if (!token.matches(".*?\\p{N}.*?"))
				return null;
			return token.replaceAll("\\p{N}+", "*").toLowerCase();		
		}
	};
	/**
	 * Return TokenTransformer that transforms tokens by grouping contiguous
	 * numbers as '*' and lowering the case to the rest of characters.
	 * If the token doesn't contain any number the token is rejected.
	 * 
	 * Examples:
	 * <ul>
	 * <li>miau -> null</li>
	 * <li>GnRH -> null</li>
	 * <li>s2 -> s*</li>
	 * <li>rp47phox -> rp*phox</li>
	 * <li>U937 -> u*</li>
	 * <li>U23k9 -> u*k*</li>
	 * <li>. -> null</li>
	 * </ul>
	 * 
	 * @return
	 */
	public static TokenTransformer getMorphologyContiguousNumbersGroupedOrRejectedTransformer() {
		return morphologyContiguousNumbersGroupedOrRejected;
	}
	
	
	private static final TokenTransformer englishVowelsTransformer = new TokenTransformer() {		
		@Override
		public String transform(String token) {
			return token.replaceAll("[^aeiouAEIOU]", "*");
		}
	};
	/**
	 * Return TokenTransformer that transforms tokens by writing vowels
	 * as they are (conserving the case) and transforming all other symbols
	 * to '*'.
	 * 
	 * All tokens are accepted.
	 * 
	 * Examples:
	 * <ul>
	 * <li>miau -> *iau</li>
	 * <li>GnRH -> ****</li>
	 * <li>s2 -> **</li>
	 * <li>rp47phox -> ******o*</li>
	 * <li>U937 -> U***</li>
	 * <li>U23k9 -> U**k*</li>
	 * <li>. -> *</li>
	 * </ul>
	 * 
	 * @return
	 */
	public static TokenTransformer getEnglishVowelsTransformer() {
		return englishVowelsTransformer;
	}	
	
}
