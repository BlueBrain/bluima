package ch.epfl.bbp.nlp.ie.proteinconc;

import java.util.regex.Pattern;

/**
 * Gathers static information concerning concentrations.
 * 
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 *
 */
public class ConcentrationContext {
	private final static String CONCENTRATION_REGEX = "^(?:molarity|(?:milli|micro|nano|µ|μ|m|n)?(?:molar|mole|M)|pp(?:m|b|t))$|^(?:k|d|m|M|µ|μ|n)?(?:g)/(?:k|d|m|M|µ|μ)?(?:l|L|m3)|copies|(?:mg protein/ml|mol/dm3|mol/kg|mol/mol|copies/(?:μ|μ)m\\d)$";
	private final static Pattern CONCENTRATION_PATTERN = Pattern.compile(CONCENTRATION_REGEX);
	
	public static boolean isConcentration(String unit) {
		return CONCENTRATION_PATTERN.matcher(unit).find();
	}
}
