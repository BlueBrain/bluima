package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Measure;

/**
 * Normalizes {@link Keep#getNormalizedText()} that cover a {@link Measure} by
 * removing the numeric part and leaving only the unit. E.g. "28 mM" becomes
 * "MEASURE_mM". Useful e.g. for LDA, when the numeric part is irrelevant/confusing,
 * but the unit is.
 *
 * @author renaud.richardet@epfl.ch
 */
// TODO leave only the unit category/class
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class MeasureNormalizerAnnotator extends JCasAnnotator_ImplBase {

    /** Mask (that replace the {@link Measure}'s value */
    public static final String MEASURE_MASK = "MEASURE_";
    
    @ConfigurationParameter(name = "removeSimpleMeasure", defaultValue = "false",//
    description = "Remove measures which are single numbers (MEASURE_)")
    private boolean removeMeasure_;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
    	String unit = null;
    	Keep k =  null;
    	
    	// An array is needed because when a Keep is removed, the collection fails
        Keep[] keeps = select(jCas, Keep.class).toArray(new Keep[0]);
        
        for(int i = 0; i < keeps.length ; i++){
        	k = keeps[i];
            Annotation a = k.getEnclosedAnnot();
            
            if (a instanceof Measure) {
            	unit = ((Measure) a).getUnit();
            	if(removeMeasure_){
            		if(unit == null){
            			k.removeFromIndexes(jCas);
            		}
            		else if (unit.isEmpty()){
            			k.removeFromIndexes(jCas);
            		}
            	}
            	else{
            		k.setNormalizedText(MEASURE_MASK + unit);
            	}
            }
        }
    }
}
