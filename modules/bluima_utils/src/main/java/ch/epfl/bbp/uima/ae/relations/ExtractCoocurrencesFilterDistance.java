package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.distance;

import ch.epfl.bbp.uima.types.Cooccurrence;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Extracts cooccurrences, filtering by maximum distance.<br>
 * <strong> Use {@link FilterCoocurrencesByDistance} instead.</strong>
 * 
 * @author renaud.richardet@epfl.ch
 */
// makes things simpler to handle a single file
@OperationalProperties(multipleDeploymentAllowed = false)
@Deprecated
public class ExtractCoocurrencesFilterDistance extends ExtractCoocurrences {
    protected static Logger LOG = LoggerFactory
            .getLogger(ExtractCoocurrencesFilterDistance.class);

    public static final String PARAM_MAXIMUM_DISTANCE = "maximumDistance";
    @ConfigurationParameter(name = PARAM_MAXIMUM_DISTANCE, mandatory = true,//
    description = "TODO")
    protected int maximumDistance;

    /**
     * Adds all cooccurrences (no filtering). Subclasses can implement finer
     * filtering.
     * 
     * @param jCas
     * @param enclosingAnnot
     * @param annot1
     * @param annot2
     * @param ids1
     * @param ids2
     */
    @Override
    protected Cooccurrence filterCooccurence(JCas jCas,
            Annotation enclosingAnnot, Annotation annot1, Annotation annot2,
            String[] ids1, String[] ids2) {

        if (distance(annot1, annot2) < maximumDistance) {
            return super.filterCooccurence(jCas, enclosingAnnot, annot1,
                    annot2, ids1, ids2);
        } else {
            return null;
        }
    }
}
