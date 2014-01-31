package ch.epfl.bbp.uima.ae;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

/**
 * Ensures that the text of a CAS is not null, sets it to "" otherwise
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability()
public class EnsureDocTextNotNullAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        if (jcas.getDocumentText() == null) {
            jcas.setDocumentText("");
        }
    }
}