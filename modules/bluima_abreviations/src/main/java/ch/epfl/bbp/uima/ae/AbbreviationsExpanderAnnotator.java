package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.ABBREVIATION;
import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.NON_CONTENT_ANNOTATIONS_OR_TOKEN;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.Collections.unmodifiableList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.Subiterator;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.julielab.jules.types.Abbreviation;

/**
 * Finds existing {@link Abbreviation}s and copies into short-forms the
 * annotations from the corresponding long-form. E.g. with
 * "glutamic acid decarboxylase (GAD) is great, i love GAD", if
 * "glutamic acid decarboxylase" get annotated as Protein, the second GAD will
 * get annotated as Protein, too.<br>
 * <strong>Should be run AFTER other NERS</strong> (as it copies the data from
 * them).
 * 
 * @author renaud.richardet@epfl.ch
 */
// note: output is dynamic
@TypeCapability(inputs = { ABBREVIATION }, outputs = {})
public class AbbreviationsExpanderAnnotator extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(AbbreviationsExpanderAnnotator.class);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        expandAbbreviations(jCas);
    }

    public static void expandAbbreviations(JCas jCas) {
        String pmId = getHeaderDocId(jCas);

        // otherwise was very slow
        Map<Abbreviation, List<Annotation>> cache = newHashMap();

        List<Abbreviation> tmp = newLinkedList(select(jCas, Abbreviation.class));
        for (Abbreviation abrev : tmp) {

            Annotation reference = abrev.getTextReference();
            if (reference != null && reference instanceof Abbreviation) {
                Abbreviation aRef = (Abbreviation) reference;

                List<Annotation> covereds;
                if (cache.containsKey(aRef))
                    covereds = cache.get(aRef);
                else {
                    covereds = getCovered(jCas, aRef, pmId);
                    cache.put(aRef, covereds);
                }

                // copy them to the other abbreviation short-forms
                for (Annotation covered : covereds) {

                    Annotation clone = (Annotation) covered.clone();
                    clone.setBegin(abrev.getBegin());
                    clone.setEnd(abrev.getEnd());
                    clone.addToIndexes(jCas);

                    if (!clone.getCoveredText().equals(aRef.getCoveredText()))
                        LOG.warn("'{}' not matching2 '{}' in " + pmId,
                                clone.getCoveredText(), aRef.getCoveredText());
                }
            }
        }
    }

    private static List<Annotation> getCovered(JCas jCas, Abbreviation aRef,
            String pmId) {

        List<Annotation> covereds = newLinkedList();
        String expan = aRef.getExpan();

        // gridsearch until we find back the beg-end of expan
        int begin = -1, end = -1;
        int[] spans = { 2, 1, 3, 0 };
        for (int i : spans) {
            for (int j : spans) {
                try {
                    int beginCandidate = aRef.getBegin() - expan.length() - i;
                    int endCandidate = aRef.getBegin() - j;
                    String substring = jCas.getDocumentText().substring(
                            beginCandidate, endCandidate);
                    if (substring.equals(expan)) {
                        begin = beginCandidate;
                        end = endCandidate;
                    }
                } catch (Exception e) {// nope
                }
            }
        }
        if (begin == -1) {
            LOG.warn("'{}' not matching '{}' in " + pmId, expan);
            return covereds;
        }

        // find all annotations covering the abbreviation
        // text-definition (long form)

        for (Annotation coveredRaw : selectMatching(jCas.getCas(), begin, end,
                aRef)) {
            // skip?
            if (!NON_CONTENT_ANNOTATIONS_OR_TOKEN.contains(coveredRaw
                    .getClass().getName()))
                covereds.add(coveredRaw);
        }
        return covereds;
    }

    /**
     * REM: modified from CasUtils..
     * 
     * Get a list of annotations of the given annotation type constraint by a
     * start and end. It's good to provide an annotation that is close to the
     * region, to quickly move the iterator
     * 
     * @param cas
     *            a CAS.
     * @param type
     *            a UIMA type.
     * @param annotationCloseToTheRegion
     *            the covering annotation.
     * @return a return value.
     * @see Subiterator
     */
    private static List<Annotation> selectMatching(CAS cas, final int begin,
            final int end, AnnotationFS annotationCloseToTheRegion) {

        final List<Annotation> list = new ArrayList<Annotation>();
        final FSIterator<AnnotationFS> it = cas.getAnnotationIndex().iterator();

        // Try to seek the insertion point.
        it.moveTo(annotationCloseToTheRegion);

        // If the insertion point is beyond the index, move back to the last.
        if (!it.isValid()) {
            it.moveToLast();
            if (!it.isValid()) {
                return list;
            }
        }

        // Ignore type priorities by seeking to the first that has the same
        // begin
        boolean moved = false;
        while (it.isValid() && (it.get()).getBegin() >= begin) {
            it.moveToPrevious();
            moved = true;
        }

        // If we moved, then we are now on one starting before the requested
        // begin, so we have to
        // move one ahead.
        if (moved) {
            it.moveToNext();
        }

        // If we managed to move outside the index, start at first.
        if (!it.isValid()) {
            it.moveToFirst();
        }

        // Skip annotations whose start is before the start parameter.
        while (it.isValid() && (it.get()).getBegin() < begin) {
            it.moveToNext();
        }

        while (it.isValid()) {
            AnnotationFS a = it.get();
            if (!(a instanceof Annotation))
                continue;

            // If the start of the current annotation is past the end parameter,
            // we're done.
            if (a.getBegin() > end) {
                break;
            }
            it.moveToNext();

            if (a.getBegin() == begin && a.getEnd() == end) {
                list.add((Annotation) a);
            }
        }
        return unmodifiableList(list);
    }
}
