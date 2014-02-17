package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Cooccurrence;

/**
 * Filters {@link Cooccurrence}s with {@link Cooccurrence#getHasInteraction()} =
 * false
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { COOCCURRENCE }, outputs = { COOCCURRENCE })
public class FilterInactiveCooccurrencesAnnotator extends
		JCasAnnotator_ImplBase {

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {
		Collection<Cooccurrence> coocs = select(jCas, Cooccurrence.class);
		Cooccurrence[] array = coocs.toArray(new Cooccurrence[coocs.size()]);

		for (int i = 0; i < array.length; i++) {
			if (!array[i].getHasInteraction()) {
				array[i].removeFromIndexes();
			}
		}
	}

}
