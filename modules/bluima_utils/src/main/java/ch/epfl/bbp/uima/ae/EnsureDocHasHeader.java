package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static org.apache.uima.fit.util.JCasUtil.exists;

import java.util.Random;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Header;

/**
 * Ensures that all {@link CAS}es have a {@link Header} annotation, sets it to a
 * random value otherwise otherwise.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class EnsureDocHasHeader extends JCasAnnotator_ImplBase {

    Random rnd = new Random();

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (!exists(jCas, Header.class)) {
            Header header = new Header(jCas);
            header.setDocId(rnd.nextInt(10000000) + "");
            header.addToIndexes();
        }
    }
}