package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.nlp.ie.proteinconc.ConcentrationContext;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.types.Measure;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;

/**
 * Annotate annotation using a rule-based approach defined in
 * {@link ConcentrationContext}.
 */
@TypeCapability(inputs={MEASURE})
public class SimpleConcentrationAnnotator extends JCasAnnotator_ImplBase{

	@Override
	public void process(JCas aJCas) throws AnalysisEngineProcessException {
		
		Collection<Measure> allMeasureAnnotations = JCasUtil.select(aJCas, Measure.class);
		List<Measure> measuresToBeAdded = new LinkedList<Measure>();
		for (Measure measure : allMeasureAnnotations) {
			
			if ((measure != null) &&
				(measure.getUnit() != null) &&
				ConcentrationContext.isConcentration(measure.getUnit()))
			{
				Concentration concentrationAnnotation = new Concentration(aJCas);
				concentrationAnnotation.setBegin(measure.getBegin());
				concentrationAnnotation.setEnd(measure.getEnd());
				concentrationAnnotation.setUnit(measure.getUnit());
				concentrationAnnotation.setValue(measure.getValue());
                measuresToBeAdded.add(concentrationAnnotation);
			}
		}

        for (Measure measure : measuresToBeAdded) {
            measure.addToIndexes();
        }
	}

}
