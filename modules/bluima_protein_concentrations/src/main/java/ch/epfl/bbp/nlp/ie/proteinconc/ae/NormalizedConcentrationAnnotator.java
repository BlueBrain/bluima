package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.nlp.ie.proteinconc.normalizer.ConcentrationNormalizer;
import ch.epfl.bbp.nlp.ie.proteinconc.normalizer.UnitNormalizer.UnknownUnitException;
import ch.epfl.bbp.nlp.ie.proteinconc.normalizer.UnitNormalizer.ValueUnitWrapper;
import ch.epfl.bbp.uima.types.Concentration;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.MEASURE;

/**
 * Annotates the concentration as {@link SimpleConcentrationAnnotator} does
 * and normalizes them using the {@link ConcentrationNormalizer}.
 */
@TypeCapability(inputs={MEASURE})
public class NormalizedConcentrationAnnotator 
	extends SimpleConcentrationAnnotator
{

	private ConcentrationNormalizer mConcentrationNormalizer;
	private final static Logger LOG = LoggerFactory.getLogger(NormalizedConcentrationAnnotator.class);
	
	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException 
	{
		super.initialize(context);
		mConcentrationNormalizer = new ConcentrationNormalizer();
	}

	@Override
	public void process(JCas context) 
			throws AnalysisEngineProcessException 
	{
		if (mConcentrationNormalizer == null) {
            throw new AnalysisEngineProcessException(
                    new Exception("NormalizedConcentrationAnnotator seems to have been badly initialized."));
        }
		
		super.process(context);
		
		Collection<Concentration> allConcentrationAnnotations = JCasUtil.select(context, Concentration.class);
		
		for (Concentration concentrationAnnotation: allConcentrationAnnotations) {
			
			ValueUnitWrapper wrapper;
			try {
				wrapper = mConcentrationNormalizer.normalize(concentrationAnnotation.getValue(), concentrationAnnotation.getUnit());
				concentrationAnnotation.setNormalizedUnit(wrapper.getUnit());
				concentrationAnnotation.setNormalizedValue((float) wrapper.getValue());
			} catch (UnknownUnitException e) {
				LOG.warn("[UnknownUnitException] : " + e.getMessage());
			}	
		}
	}

}
