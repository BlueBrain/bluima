package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static org.apache.uima.fit.util.JCasUtil.exists;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Sentence;

/**
 * Ensures that the jCas has at lease one {@link Sentence} annotation, sets it
 * to the whole text otherwise.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { SENTENCE })
public class EnsureDocHasOneSentence extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (!exists(jCas, Sentence.class)) {
            new Sentence(jCas, 0, jCas.getDocumentText().length())
                    .addToIndexes();
        }
    }
}