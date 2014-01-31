package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static com.google.common.collect.Lists.newLinkedList;
import static java.util.regex.Pattern.CASE_INSENSITIVE;
import static java.util.regex.Pattern.compile;

import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.Cooccurrence;
import de.julielab.jules.types.Sentence;

/**
 * Filters cooccurrences whose sentence contains stopwords, e.g. sentences
 * starting with 'Abbreviations:'. Improves precision at the cost of recall.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = COOCCURRENCE)
public class FilterCoocurrencesByStopwords extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(FilterCoocurrencesByStopwords.class);

    private static final Pattern ABBREV = compile("^Abbreviations.*",
            CASE_INSENSITIVE);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        List<Cooccurrence> toRemove = newLinkedList();

        for (Entry<Sentence, Collection<Cooccurrence>> sentenceWithCooc : JCasUtil
                .indexCovered(jCas, Sentence.class, Cooccurrence.class)
                .entrySet()) {

            String sText = sentenceWithCooc.getKey().getCoveredText()
                    .toLowerCase();

            if (ABBREV.matcher(sText).matches()) {
                toRemove.addAll(sentenceWithCooc.getValue());
            }
        }

        // remove
        Cooccurrence[] array = toRemove.toArray(new Cooccurrence[toRemove
                .size()]);
        for (int i = 0; i < array.length; i++) {
            array[i].removeFromIndexes();
        }
    }
}
