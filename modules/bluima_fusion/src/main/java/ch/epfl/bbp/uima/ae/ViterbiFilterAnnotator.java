package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.ANNOTATIONS_PRIORITY;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.Viterbi.Transition;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.typesystem.TypeSystemSemantics;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Marks annotations that lie on the shortest path to cover a {@link Sentence}.
 * Optionally, removes annotations that are not on that shortest path. If
 * multiple annotations pairs are both on the same shortest path node, marks
 * only the "best" one of the annotation pairs (based on confidence).<br>
 * Shortest path is computed using the Viterbi algorithm. Marking is done by
 * adding a {@link Keep} annotation.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ViterbiFilterAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(ViterbiFilterAnnotator.class);

    public static final String REMOVE_OTHER_ANNOTATIONS = "removeOtherAnnotations";
    @ConfigurationParameter(name = REMOVE_OTHER_ANNOTATIONS, defaultValue = "false",//
    description = "Whether to remove annotations that are not on the shortest path")
    private boolean removeOtherAnnotations;

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        if (BlueCasUtil.isEmptyText(jCas))
            return;

        for (Sentence s : select(jCas, Sentence.class)) {

            // LOG.trace("sentence::: " + s.getCoveredText() + " {}-{}",
            // s.getBegin(), s.getEnd());

            List<Annotation> sentenceAnnotations = BlueCasUtil.selectCovered(
                    jCas.getCas(), s);
            // MUCH SLOWWWWER:
            // List<Annotation> sentenceAnnotations = Lists.newArrayList();
            // for (Annotation a : jCas.getAnnotationIndex()) {
            // if (a.getBegin() >= s.getBegin() && a.getEnd() <= s.getEnd())
            // sentenceAnnotations.add(a);
            // }

            LOG.trace("added {} annotations", sentenceAnnotations.size());

            List<List<Transition>> shortestPath = Viterbi.viterbi(
                    sentenceAnnotations, jCas);
            LOG.trace("path:::");
            // /shortestPath.foreach { ts =>
            // /dbg(ts.head.from + "::" + ts.head.to)
            // /ts.foreach(t => ///dbg("* " + (t)))
            // /}

            // keep the "best" annotation if multiple are on the same
            // shortest-path node
            List<Annotation> prunedKeep = newArrayList();
            for (List<Transition> ts : shortestPath) {
                Transition bestT = null;
                for (Transition t : ts) {
                    if (bestT == null)
                        bestT = t;
                    else
                        bestT = compare(bestT, t);
                }
                if (bestT.annot != null)
                    prunedKeep.add(bestT.annot);
            }
            Set<Integer> prunedKeepIndex = new HashSet<Integer>();
            for (Annotation a : prunedKeep) {
                prunedKeepIndex.add(a.getAddress());
            }

            // LOG.debug("\nprunedShortestPath:::");
            // for (Annotation a : prunedKeep) {
            // // LOG.debug(a.getCoveredText() + " [["
            // // + a.getClass().getSimpleName());
            // LOG.debug(To.string(a));
            // }

            // only Keep annotations on shortest path
            for (Annotation a : sentenceAnnotations) {
                // LOG.debug("maybe [[" + To.string(a) + "]]");

                if (prunedKeepIndex.contains(a.getAddress())) {
                    Keep keep = new Keep(jCas, a.getBegin(), a.getEnd());
                    keep.setEnclosedAnnot(a);
                    // just the annotation text:
                    keep.setNormalizedText(a.getCoveredText());
                    keep.addToIndexes();

                } else {
                    if (removeOtherAnnotations
                            & !TypeSystemSemantics.NON_CONTENT_ANNOTATIONS
                                    .contains(a.getClass().getName()) //
                            // do not remove Tokens
                            && a.getClass() != Token.class
                            // do not remove annot without text
                            && a.getEnd() != 0) {

                        // LOG.debug(" removing [[" + To.string(a) + "]]");
                        a.removeFromIndexes(jCas);
                    }
                }
            }
        }
    }

    public static Transition compare(Transition a, Transition b) {

        if (a.annot == null)
            return b;
        else if (b.annot == null)
            return a;

        else if (a.annot instanceof Token)
            return b;
        else if (b.annot instanceof Token)
            return a;

        else {
            int ai = ANNOTATIONS_PRIORITY.indexOf(a.annot.getClass().getName());
            int bi = ANNOTATIONS_PRIORITY.indexOf(b.annot.getClass().getName());
            // / println("a: index " + ai + " for " + To.string(a))
            // / println("b: index " + bi + " for " + To.string(b) + "\n")
            if (ai > bi)
                return a;
            else
                return b;
        }
    }
}