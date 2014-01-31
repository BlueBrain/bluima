package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.uima.fit.util.JCasUtil.indexCovered;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Cooccurrence;
import de.julielab.jules.types.Sentence;

/**
 * Filters {@link Cooccurrence}s if the enclosing scope (e.g. {@link Sentence}
 * length) is larger than {@link #PARAM_MAXIMUM_SCOPE_LENGTH}.
 * 
 * Here some sentence length statistics from a random sample of ~50k abstract and
 * pdfs:
 * 
 * <pre>
 * Full text
 * Min. 1st Qu.  Median    Mean    3rd Qu.    Max.
 * 1.0  22.0     81.0      109.8   158.0      16380.0
 * 
 * Abstracts
 * Min. 1st Qu.  Median    Mean    3rd Qu.    Max.
 * 2.0    96.0   136.0     145.5   185.0      1215.0
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = COOCCURRENCE)
public class FilterCoocurrencesInLongSentences extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ENCLOSING_SCOPE, defaultValue = SENTENCE, //
    mandatory = false, description = "the enclosing scope to iterate on and extract co-occurrence from. Defaults to sentences")
    protected String enclosingScopeStr;
    protected Class<? extends Annotation> enclosingScope;

    public static final String PARAM_MAXIMUM_SCOPE_LENGTH = "maximumEnclosingScopeLength";
    @ConfigurationParameter(name = PARAM_MAXIMUM_SCOPE_LENGTH, defaultValue = "1000",//
    mandatory = false, description = "If the enclosing scope is longer than this value, "
            + "all co-occurrences are filtered out")
    protected int maximumEnclosingScopeLength;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            enclosingScope = (Class<? extends Annotation>) Class
                    .forName(enclosingScopeStr);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        List<Cooccurrence> toRemove = newLinkedList();

        Map<? extends Annotation, Collection<Cooccurrence>> enclosingScopeWithCoocurences = indexCovered(
                jCas, enclosingScope, Cooccurrence.class);

        for (Annotation es : select(jCas, enclosingScope)) {

            if (es.getCoveredText().length() > maximumEnclosingScopeLength
                    && enclosingScopeWithCoocurences.containsKey(es)) {

                toRemove.addAll(enclosingScopeWithCoocurences.get(es));
            }
        }

        // remove
        Cooccurrence[] array = toRemove.toArray(new Cooccurrence[toRemove
                .size()]);
        for (int i = 0; i < array.length; i++)
            array[i].removeFromIndexes();
    }
}