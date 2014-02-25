package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.haveSameBeginEnd;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.VIEW_GOLD;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.VIEW_SYSTEM;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.EvaluationAnnotator;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.validation.Comparator;
import ch.epfl.bbp.uima.validation.TestEvaluator;

/**
 * Evaluates if two {@link Cooccurrence}'s entities are the same. Any entities
 * can be used, since they are compared on start/end of them.
 * 
 * @author renaud.richardet@epfl.ch
 * @see {@link EvaluationAnnotator}
 */
@TypeCapability(inputs = COOCCURRENCE)
public class CooccurrencesEvaluationAnnotator extends JCasAnnotator_ImplBase {
    // private static Logger LOG = LoggerFactory.getLogger(BartWriter.class);

    private TestEvaluator<Annotation, Annotation> evaluator;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        evaluator = getEvaluator();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String pmId = BlueCasUtil.getHeaderDocId(jCas);
        print("pmId " + pmId);

        JCas goldView, systemView;
        try {
            goldView = jCas.getView(VIEW_GOLD);
            systemView = jCas.getView(VIEW_SYSTEM);
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }

        Collection goldAnnot = select(goldView, Cooccurrence.class);
        Collection systAnnot = select(systemView, Cooccurrence.class);
        print("comparing #gold:" + goldAnnot.size() + " #sys:"
                + systAnnot.size());

        print(/* "pmId:"+pmId + "\t" + */evaluator.add(goldAnnot, systAnnot,
                pmId));
    }

    private static void print(String msg) {
       // System.out.println(msg);
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        // print evaluation results
        System.out.println(evaluator.compare());
    }

    public static <A extends Annotation, B extends Annotation> TestEvaluator<A, B> getEvaluator() {
        Comparator<A, B> simpleComparator = new Comparator<A, B>() {
            @Override
            public boolean areTheSame(A _gold, B _system) {
                Cooccurrence gold = (Cooccurrence) _gold;
                Cooccurrence system = (Cooccurrence) _system;
                return CooccurrencesEvaluationAnnotator
                        .areTheSame(gold, system);
            }
        };
        return new TestEvaluator<A, B>(simpleComparator);
    }

    /**
     * @return true if both {@link Cooccurrence#getFirstEntity()} and
     *         {@link Cooccurrence#getSecondEntity()} have same begin/end in
     *         gold and system
     */
    public static boolean areTheSame(Cooccurrence gold, Cooccurrence system) {
        if ((haveSameBeginEnd(gold.getFirstEntity(), system.getFirstEntity()) && //
                haveSameBeginEnd(gold.getFirstEntity(), system.getFirstEntity()))
                || //
                (haveSameBeginEnd(gold.getSecondEntity(),
                        system.getFirstEntity()) && //
                haveSameBeginEnd(gold.getFirstEntity(),
                        system.getSecondEntity()))) {
            return true;
        }
        return false;
    }

    public static java.util.Comparator<Cooccurrence> comp() {
        return new java.util.Comparator<Cooccurrence>() {
            public int compare(final Cooccurrence o1, final Cooccurrence o2) {
                return areTheSame(o1, o2) ? 0 : 1;
            }
        };
    }
}