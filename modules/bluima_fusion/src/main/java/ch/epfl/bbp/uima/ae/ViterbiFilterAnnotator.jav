package ch.epfl.bbp.uima.ae;

import static org.uimafit.util.JCasUtil.select;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uimafit.component.JCasAnnotator_ImplBase;

import scala.collection.Iterator;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.Transition;
import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.CellDictTerm;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.DiseaseDictTerm;
import ch.epfl.bbp.uima.types.IonchannelDictTerm;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.MethodDictTerm;
import ch.epfl.bbp.uima.types.MoleculeDictTerm;
import ch.epfl.bbp.uima.types.NifDictTerm;
import ch.epfl.bbp.uima.types.OrganismDictTerm;
import ch.epfl.bbp.uima.types.POSSkip;
import ch.epfl.bbp.uima.types.POSVerb;
import ch.epfl.bbp.uima.types.POSWh;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import ch.epfl.bbp.uima.types.Punctuation;
import ch.epfl.bbp.uima.types.RegionDictTerm;
import ch.epfl.bbp.uima.types.SexDictTerm;
import ch.epfl.bbp.uima.types.ShortestPath;
import ch.epfl.bbp.uima.types.Verb;
import ch.epfl.bbp.uima.types.VerbDictTerm;
import ch.epfl.bbp.uima.types.WordnetDictTerm;

import com.google.common.collect.Lists;

import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * 
 * Filters "redundant" annotations. First, computes the shortest path to cover a
 * 
 * @link{Sentence and removes annotations that are not on that shortest path.
 *                Keep multiple annotations pairs if they are both on the
 *                shortest path. Second, keeps only one of the annotation pairs
 *                (based on confidence).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ViterbiFilterAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(ViterbiFilterAnnotator.class);

    // in INCREASING order of prio
    public static final List<String> PRIORITY_ANNOTATIONS = Lists.newArrayList( //
            Token.class.getName() //
            , DictTerm.class.getName() //
            , Verb.class.getName() //
            , BiolexiconDictTerm.class.getName() //
            , ProteinDictTerm.class.getName() //
            , VerbDictTerm.class.getName() //
            , WordnetDictTerm.class.getName() //
            , Chemical.class.getName() //
            , MoleculeDictTerm.class.getName() //
            , MethodDictTerm.class.getName() //
            , SexDictTerm.class.getName() //
            , CellDictTerm.class.getName() //
            , DiseaseDictTerm.class.getName() //
            , RegionDictTerm.class.getName() //
            , BrainRegionDictTerm.class.getName() //
            , LinnaeusSpecies.class.getName() //
            , Punctuation.class.getName() //
            , Measure.class.getName() //
            , Protein.class.getName() //
            , NifDictTerm.class.getName() //
            , OrganismDictTerm.class.getName() //
            , IonchannelDictTerm.class.getName() //
            , AgeDictTerm.class.getName() //
            , POSVerb.class.getName() //
            , POSWh.class.getName() //
            , POSSkip.class.getName() //
            );

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        if (BlueCasUtil.isEmptyText(jCas))
            return;

        for (Sentence s : select(jCas, Sentence.class)) {

            LOG.debug("sentence::: " + s.getCoveredText());

            List<Annotation> sentenceAnnotations = Lists.newArrayList();
            for (Annotation a : jCas.getAnnotationIndex()) {
                if (a.getBegin() > s.getBegin() && a.getEnd() < s.getEnd())
                    sentenceAnnotations.add(a);
            }

            scala.collection.immutable.List<scala.collection.immutable.List<Transition>> shortestPath = ch.epfl.bbp.uima.ae.Viterbi
                    .viterbi(sentenceAnnotations, jCas);
            LOG.debug("path:::");

            // /shortestPath.foreach { ts =>
            // /dbg(ts.head.from + "::" + ts.head.to)
            // /ts.foreach(t => ///dbg("* " + (t)))
            // /}

            // TODO FIXME validate that at least 1 short path EXISTS

            // keep the "best" annotation if multiple are on a sortest-path step
            List<Annotation> prunedShortestPath = Lists.newArrayList();
            Iterator<scala.collection.immutable.List<Transition>> it = shortestPath
                    .iterator();
            while (it.hasNext()) { // fold
                scala.collection.immutable.List<Transition> ts = it.next();
                Transition bestT = null;
                Iterator<Transition> it2 = ts.iterator();
                while (it2.hasNext()) {
                    Transition t = it2.next();
                    if (bestT == null)
                        bestT = t;
                    else
                        bestT = compare(bestT, t);
                }
                if (bestT.getAnnot() != null)
                    prunedShortestPath.add(bestT.getAnnot());
            }
            Set<Integer> prunedShortestPathIndex = new HashSet<Integer>();
            for (Annotation a : prunedShortestPath) {
                prunedShortestPathIndex.add(a.getAddress());
            }

            // LOG.debug("\nprunedShortestPath:::");
            // for (Annotation a : prunedShortestPath) {
            // // LOG.debug(a.getCoveredText() + " [["
            // // + a.getClass().getSimpleName());
            // LOG.debug(To.string(a));
            // }

            // prune all annotations not on shortest path
            for (Annotation a : sentenceAnnotations) {
                // LOG.debug("maybe [[" + To.string(a) + "]]");

                if (!Viterbi.skip.contains(a.getClass().getName()) //
                        && a.getClass() != Token.class // do not remove Tokens
                        && a.getEnd() != 0) { // do not remove annot without
                                              // text

                    if (!prunedShortestPathIndex.contains(a.getAddress())) {
                        // LOG.debug(" removing [[" + To.string(a) + "]]");
                        a.removeFromIndexes(jCas);
                    } else {
                        ShortestPath sp = new ShortestPath(jCas, a.getBegin(),
                                a.getEnd());
                        sp.setEnclosedAnnot(a);
                        sp.addToIndexes();
                    }
                }
            }
            // /sentenceAnnotations.foreach(a => ///dbg(To.string(a)))
        }
    }

    public static Transition compare(Transition a, Transition b) {

        if (a.getAnnot() == null)
            return b;
        else if (b.getAnnot() == null)
            return a;

        else if (a.getAnnot() instanceof Token)
            return b;
        else if (b.getAnnot() instanceof Token)
            return a;

        else {
            int ai = PRIORITY_ANNOTATIONS.indexOf(a.getAnnot().getClass()
                    .getName());
            int bi = PRIORITY_ANNOTATIONS.indexOf(b.getAnnot().getClass()
                    .getName());
            // / println("a: index " + ai + " for " + To.string(a))
            // / println("b: index " + bi + " for " + To.string(b) + "\n")
            if (ai > bi)
                return a;
            else
                return b;
        }
    }
}