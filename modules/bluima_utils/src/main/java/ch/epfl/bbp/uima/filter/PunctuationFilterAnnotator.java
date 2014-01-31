package ch.epfl.bbp.uima.filter;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.ae.PunctuationAnnotator;
import ch.epfl.bbp.uima.types.Keep;

/**
 * Removes {@link Keep} annotations whose {@link Keep#getNormalizedText()} is a
 * punctuation.<br>
 * 
 * @see PunctuationAnnotator PunctuationAnnotator for the list
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PunctuationFilterAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Keep> keeps = select(jCas, Keep.class);
        Keep[] array = keeps.toArray(new Keep[keeps.size()]);

        for (int i = 0; i < array.length; i++) {
            // TODO use normalized or covered?
            String txt = array[i].getNormalizedText();

            if (PunctuationAnnotator.PUNCT.matcher(txt).matches()) {
                array[i].removeFromIndexes();
            }
        }
    }
}