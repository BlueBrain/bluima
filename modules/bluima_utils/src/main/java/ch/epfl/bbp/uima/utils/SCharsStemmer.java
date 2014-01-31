package ch.epfl.bbp.uima.utils;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.uima.conceptMapper.support.stemmer.Stemmer;

/**
 * Removes ending s of words longer than 3 chars
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SCharsStemmer implements Stemmer {

	/** Removes ending s of words longer than 3 chars */
	@Override
	public String stem(String token) {
		if (token != null && token.length() > 3 && token.endsWith("s")) {
			return token.substring(0, token.length() - 1);
		}
		return token;
	}

	@Override
	public void initialize(String dictionary) throws FileNotFoundException,
			ParseException {// nope
	}
}
