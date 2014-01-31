package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;

/**
 * Normalizes {@link Keep#getNormalizedText()} that cover a {@link Measure} by
 * removing the numeric part and leaving only the unit. E.g. "28 mM" becomes
 * "MEASURE_mM". Useful e.g. for LDA, when the numeric part is
 * irrelevant/confusing, but the unit is.
 * 
 * @author renaud.richardet@epfl.ch
 */
// TODO leave only the unit category/class
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class MeasureNormalizerAnnotator extends JCasAnnotator_ImplBase {

    /** Mask (that replace the {@link Measure}'s value */
    public static final String MEASURE_MASK = "MEASURE_";

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Keep k : select(jCas, Keep.class)) {

            Annotation a = k.getEnclosedAnnot();
            if (a instanceof Measure) {
                k.setNormalizedText(MEASURE_MASK + ((Measure) a).getUnit());
            }
            // Prin.t(k);
        }
    }
}
