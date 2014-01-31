package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.distance;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.Cooccurrence;

/**
 * Filters cooccurrences whose two entities are separated by more that the
 * specified distance. Improves precision at the cost of recall.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = COOCCURRENCE)
public class FilterCoocurrencesByDistance extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(FilterCoocurrencesByDistance.class);

    public static final String PARAM_MAXIMUM_DISTANCE = "maximumDistance";
    @ConfigurationParameter(name = PARAM_MAXIMUM_DISTANCE, mandatory = true,//
    description = "the maximum distance between the 2 entities of that co-occurrence")
    protected int maximumDistance;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Cooccurrence> coocs = select(jCas, Cooccurrence.class);
        Cooccurrence[] array = coocs.toArray(new Cooccurrence[coocs.size()]);

        for (int i = 0; i < array.length; i++) {
            if (distance(array[i].getFirstEntity(), array[i].getSecondEntity()) > maximumDistance) {
                array[i].removeFromIndexes();
            }
        }
    }
}
