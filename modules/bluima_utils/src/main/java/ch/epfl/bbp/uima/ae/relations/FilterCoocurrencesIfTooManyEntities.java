package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.uima.fit.util.JCasUtil.indexCovered;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.TypeSystem;
import de.julielab.jules.types.Sentence;

/**
 * Filters cooccurrences if more than {@link #PARAM_MAXIMUM_ENTITIES} occur in a
 * sentence. Improves precision at the cost of recall. <br>
 * * <i> We tested two simple modifications of the sentence-level co-occurrence
 * technique. The first reduces co-occurrence predictions to sentences with a
 * limited number of brain region mentions. By extracting co-occurring pairs
 * from sentences with only two brain region mentions, precision reaches 23.1%
 * and 17.2% recall (f-measure 1⁄4 19.7%). This means that an average sentence
 * with two brain region mentions is reporting a connection in almost one of
 * four cases. By varying this threshold, the f-measure increases until
 * sentences with six or more brain region mentions are included. We observed
 * that some of these larger sentences merely list brain regions involved in the
 * study and not their relationships. <strong>By limiting the threshold at five
 * brain region mentions or less per sentence, co-occurrence provides 18.8%
 * precision and 66.1% recall.</strong></i>(French 2012)
 * 
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { COOCCURRENCE })
public class FilterCoocurrencesIfTooManyEntities extends JCasAnnotator_ImplBase {

    public static final String PARAM_MAXIMUM_ENTITIES = "maximumEntities";
    @ConfigurationParameter(name = PARAM_MAXIMUM_ENTITIES, defaultValue = "5",//
    mandatory = false, description = "the maximum number of enclosed annotations"
            + " (e.g. brain regions) mentions allowed per enclosing scope."
            + " If more, delete ALL co-occurrences in the enclosing scope.")
    protected int maximumDistance;

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, defaultValue = TypeSystem.BRAIN_REGION,//
    mandatory = false, description = "the enclosed annotation")
    protected String annotationStr;
    protected Class<Annotation> annotation;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            annotation = (Class<Annotation>) Class.forName(annotationStr);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        List<Cooccurrence> toRemove = newLinkedList();

        Map<Sentence, Collection<Annotation>> sentenceWithBr = indexCovered(
                jCas, Sentence.class, annotation);

        for (Entry<Sentence, Collection<Cooccurrence>> sentenceWithCooc : indexCovered(
                jCas, Sentence.class, Cooccurrence.class).entrySet()) {
            Sentence s = sentenceWithCooc.getKey();

            if (sentenceWithBr.containsKey(s)) {
                int nrBr = sentenceWithBr.get(s).size();
                // System.out.println("###" + nrBr + " " + s.getCoveredText());
                if (nrBr > maximumDistance) // --> remove all coocs in sentence
                    toRemove.addAll(sentenceWithCooc.getValue());
            }
        }

        // remove
        Cooccurrence[] array = toRemove.toArray(new Cooccurrence[toRemove
                .size()]);
        for (int i = 0; i < array.length; i++)
            array[i].removeFromIndexes();
    }
}
