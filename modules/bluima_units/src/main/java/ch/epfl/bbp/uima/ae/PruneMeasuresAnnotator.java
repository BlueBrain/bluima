package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.types.Measure;

/**
 * Prunes/dedupes overlapping {@link Measure}s. Necessary because the
 * {@link RegExAnnotator} will create for '128 ± 12 copies' 2 different
 * {@link Measure} annotations ('128 ± 12 copies' and '12 copies'), so we need
 * to remove the latter.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { MEASURE }, inputs = { MEASURE })
public class PruneMeasuresAnnotator extends JCasAnnotator_ImplBase {

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

		List<Measure> measures = new ArrayList<Measure>(select(jcas,
				Measure.class));

		for (int i = 0; i < measures.size(); i++) {

			Measure outer = measures.get(i);
			//System.out.println("outer : " + outer.getCoveredText());

			List<Measure> toPrunes = JCasUtil.selectCovered(Measure.class,
					outer);

			if (toPrunes.size() > 0) {
				for (Measure toPrune : toPrunes) {
					toPrune.removeFromIndexes(jcas);
				}
			}
		}
	}
}