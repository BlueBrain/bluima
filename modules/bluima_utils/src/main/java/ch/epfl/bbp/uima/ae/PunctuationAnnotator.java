package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.PUNCTUATION;
import static java.util.regex.Pattern.compile;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Punctuation;
import de.julielab.jules.types.Token;

/**
 * Annotates Tokens consisting (exclusively) of punctuation chars. Extensive
 * (agressive) matching (see {@link PunctuationAnnotator#PUNCT})
 * 
 * @input {@link Punctuation}
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { PUNCTUATION })
public class PunctuationAnnotator extends JCasAnnotator_ImplBase {

    // String PUNCT = "`~!@#$%^&*()-=_+\\[\\]\\\\{}|;\':\\\",./<>?";
    public final static Pattern PUNCT = compile("^[\\p{Punct}¡¢£¥©®°±·×à̧́̈–—“”⁎●]+$");

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Token t : select(jCas, Token.class)) {
            String text = t.getCoveredText().trim();

            if (PUNCT.matcher(text).matches()) {
                new Punctuation(jCas, t.getBegin(), t.getEnd()).addToIndexes();
            }
        }
    }
}