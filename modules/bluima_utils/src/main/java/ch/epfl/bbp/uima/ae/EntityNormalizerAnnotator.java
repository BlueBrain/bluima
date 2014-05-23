package ch.epfl.bbp.uima.ae;



import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Keep;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

/**
 * Sets {@link Keep#setNormalizedText()} for each {@link Keep} which encloses an
 * entity (recognized by a ConceptMapper) to the canonical form of the entity.
 *  
 * @author erick.cobos@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class EntityNormalizerAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) {

        for (Keep keep : select(jCas, Keep.class)) { // Each Keep in the jCas
            Annotation annotation = keep.getEnclosedAnnot();   
            
            if(annotation instanceof DictTerm){
            	DictTerm entity = (DictTerm) annotation;
            	String normalizedText = entity.getDictCanon();
            	
            	keep.setNormalizedText(normalizedText);
            }
        }   
    }

}
