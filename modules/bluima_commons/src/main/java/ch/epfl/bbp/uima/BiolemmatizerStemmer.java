package ch.epfl.bbp.uima;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.apache.uima.conceptMapper.support.stemmer.Stemmer;

import ch.epfl.bbp.uima.ae.BlueBioLemmatizer;

public class BiolemmatizerStemmer implements Stemmer {

	@Override
	public String stem(String token) {
		String lemma = BlueBioLemmatizer.lemmatize(token, null);
		if (lemma != null)
			return lemma;
		return token;
	}

	@Override
	public void initialize(String dictionary) throws FileNotFoundException,
			ParseException {// nope
	}
}
