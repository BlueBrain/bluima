package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static java.lang.Math.min;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import de.julielab.jules.types.Sentence;

/**
 * Splits an input text into {@link Sentence}s at each dot.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { SENTENCE })
public class DotSentenceSplitterAnnotator extends JCasAnnotator_ImplBase {

    private static final String COMPONENT_ID = DotSentenceSplitterAnnotator.class
            .getSimpleName();

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        String text = jcas.getDocumentText();

        if (text.indexOf('.') == -1) {// no dots, return whole sentence
            Sentence sentence = new Sentence(jcas, 0, text.length());
            sentence.setComponentId(COMPONENT_ID);
            sentence.addToIndexes();

        } else {
            int i = 0;
            for (String dotSpaceSplit : text.split("\\. ")) {
                for (String sentenceText : dotSpaceSplit.split("\\.")) {
                    Sentence sentence = new Sentence(jcas, i, //
                            // to account for last sentence not having a dot
                            min(text.length(), i + sentenceText.length() + 1));
                    sentence.setComponentId(COMPONENT_ID);
                    sentence.addToIndexes();
                    i += sentenceText.length() + 1;
                }
                i++;// to account for the space after the dot
            }
        }
    }
}