package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.MULTIPLE_PROTEINS;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.MultipleProteins;
import ch.epfl.bbp.uima.types.Protein;

/**
 * Adds a {@link MultipleProteins} annotations if there is more that one
 * {@link Protein} in this document
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { MULTIPLE_PROTEINS })
public class MultipleProteinsAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        Collection<Protein> select = select(jcas, Protein.class);
        if (select.size() > 1) {
            MultipleProteins mp = new MultipleProteins(jcas);
            mp.setPresent(1);
            mp.addToIndexes();
        }
    }
}