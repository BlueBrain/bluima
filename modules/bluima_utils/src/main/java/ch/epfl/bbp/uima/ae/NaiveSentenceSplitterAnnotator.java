package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import de.julielab.jules.types.Sentence;

/**
 * Sentence splitter that outputs the content of the whole CAS text as one
 * single {@link Sentence}. Mostly useful for testing. Othewise, see OpenNLP
 * module.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { SENTENCE })
public class NaiveSentenceSplitterAnnotator extends JCasAnnotator_ImplBase {

    private static final String COMPONENT_ID = NaiveSentenceSplitterAnnotator.class
            .getName();

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        String text = jcas.getDocumentText();

        Sentence sent = new Sentence(jcas, 0, text.length());
        sent.setComponentId(COMPONENT_ID);
        sent.addToIndexes();
    }
}
