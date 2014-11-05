package ch.epfl.bbp.uima.utils;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.uima.conceptMapper.support.stemmer.Stemmer;

/**
 * Remove plurals of english words, using {@link javatools.parsers.PlingStemmer}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PlingStemmer implements Stemmer {

    @Override
    public String stem(String token) {
	return javatools.parsers.PlingStemmer.stem(token);
    }

    @Override
    public void initialize(String dictionary) throws FileNotFoundException,
	    ParseException {// nope
    }
}
