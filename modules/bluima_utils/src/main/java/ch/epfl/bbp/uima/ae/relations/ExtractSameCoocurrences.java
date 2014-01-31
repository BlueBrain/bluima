package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static com.google.common.base.Preconditions.checkArgument;

import ch.epfl.bbp.uima.types.Cooccurrence;
import org.apache.uima.UimaContext;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Extracts cooccurrences of the same {@link Annotation} class.
 * 
 * @see WriteCoocurrencesToLoadfile WriteCoocurrencesToLoadfile to write these
 *      cooccurrences to a file
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = COOCCURRENCE)
public class ExtractSameCoocurrences extends ExtractCoocurrences {

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        checkArgument(annotationStr1.equals(annotationStr2));
    }

    /** only add 1/2 the co-occurences, otherwise they would be counted 2 times. */
    protected Cooccurrence filterCooccurence(JCas jCas, Annotation enclosingAnnot,
            Annotation annot1, Annotation annot2, String[] ids1, String[] ids2) {

        if (annot1.getAddress() < annot2.getAddress()) {
            return super.filterCooccurence(jCas, enclosingAnnot, annot1, annot2, ids1,
                    ids2);
        } else {
            return null;
        }
    }

}
