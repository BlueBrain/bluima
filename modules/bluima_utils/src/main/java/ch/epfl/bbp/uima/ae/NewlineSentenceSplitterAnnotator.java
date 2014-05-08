package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Sentence;

/**
 * Splits an input text into {@link Sentence}s at each new line.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { SENTENCE })
public class NewlineSentenceSplitterAnnotator extends JCasAnnotator_ImplBase {

    private static final String COMPONENT_ID = NewlineSentenceSplitterAnnotator.class
            .getSimpleName();

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        int idx = 0;
        for (String line : jcas.getDocumentText().split("\\r?\\n")) {
            Sentence sentence = new Sentence(jcas, idx, idx + line.length());
            sentence.setComponentId(COMPONENT_ID);
            sentence.addToIndexes();
            idx += line.length() + 1;
        }
    }
}