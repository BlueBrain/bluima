package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

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
    public static final String MEASURE_MASK = "MEASURE___";
    private static final Object MEASURE_DATE = "Date";

    public static final String PARAM_REMOVE_SIMPLE_MEASURE = "removeSimpleMeasure";
    @ConfigurationParameter(name = PARAM_REMOVE_SIMPLE_MEASURE, defaultValue = "false",//
    description = "Remove measures which are single numbers (MEASURE_)")
    private boolean removeMeasure;

    public static final String PARAM_REMOVE_DATE_MEASURE = "removeDateMeasure";
    @ConfigurationParameter(name = PARAM_REMOVE_DATE_MEASURE, defaultValue = "false",//
    description = "Remove measures which are single numbers (MEASURE_Date)")
    private boolean removeDates;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // An array is needed because when a Keep is removed, the collection
        // fails
        Keep[] keeps = select(jCas, Keep.class).toArray(new Keep[0]);

        for (int i = 0; i < keeps.length; i++) {
            Keep k = keeps[i];
            Annotation a = k.getEnclosedAnnot();

            if (a instanceof Measure) {
                String unit = ((Measure) a).getUnit();
                if (unit == null) {
                    if (removeMeasure) {
                        k.removeFromIndexes(jCas);
                    } else {
                        k.setNormalizedText(MEASURE_MASK + "");
                    }

                } else { // Not null
                    if (unit.isEmpty() && removeMeasure) {
                        k.removeFromIndexes(jCas);
                    } else if (unit.equals(MEASURE_DATE) && removeDates) {
                        k.removeFromIndexes(jCas);

                    } else {
                        k.setNormalizedText(MEASURE_MASK + unit);
                    }
                }
            }
        }
    }
}
