package ch.epfl.bbp.uima.utils;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.uima.conceptMapper.support.stemmer.Stemmer;
import org.tartarus.snowball.ext.englishStemmer;

/**
 * Stems strings, using Snowball/Porter's algorithm.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SnowballStemmer implements Stemmer {

    private englishStemmer stemmer;

    public SnowballStemmer() {
        stemmer = new englishStemmer();
    }

    @Override
    public String stem(String token) {

        // set annotation content and call stemmer
        try {
            stemmer.setCurrent(token);
            stemmer.stem();
        } catch (Throwable t) {
            return token;
        }
        return stemmer.getCurrent();
    }

    @Override
    public void initialize(String dictionary) throws FileNotFoundException,
            ParseException {// nothing to do
    }
}
