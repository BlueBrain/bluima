package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.VIEW_GOLD;
import static ch.epfl.bbp.uima.ae.relations.CooccurrencesEvaluationAnnotator.contains;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.COOCCURRENCE;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.EvaluationAnnotator;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.validation.TestEvaluator;

/**
 * Evaluates {@link Cooccurrence}s for
 * projects/extract_brainregions/20140221_slurm_extraction <br>
 * In particular, evaluates permutations of the 3 extractors
 * 
 * @author renaud.richardet@epfl.ch
 * @see {@link EvaluationAnnotator}
 */
@TypeCapability(inputs = COOCCURRENCE)
public class CooccurrencesEvaluationAnnotator2 extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(CooccurrencesEvaluationAnnotator2.class);

    private static final String VIEW1 = "topdown", VIEW2 = "kernel",
            VIEW3 = "rules";

    private static final String PARAM_EXTRACTOR_SETUP = "extractorSetup";
    @ConfigurationParameter(name = PARAM_EXTRACTOR_SETUP, description = "e.g. 0 0 1, or best")
    private String extractorSetupStr;

    private TestEvaluator<Annotation, Annotation> evaluator = CooccurrencesEvaluationAnnotator
            .getEvaluator();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = getHeaderIntDocId(jCas);

        try {

            // REM: inefficient, but make it right...
            JCas view1 = jCas.getView(VIEW1), view2 = jCas.getView(VIEW2), view3 = jCas
                    .getView(VIEW3), goldView = jCas.getView(VIEW_GOLD);

            final Collection goldAnnot = select(goldView, Cooccurrence.class);
            final Collection systAnnot = newArrayList();
            final Collection<Cooccurrence> first = select(view1,
                    Cooccurrence.class);
            final Collection<Cooccurrence> second = select(view2,
                    Cooccurrence.class);
            final Collection<Cooccurrence> third = select(view3,
                    Cooccurrence.class);

            print(pmId + " GOLD::");
            for (Object c : goldAnnot)
                print(To.string(c));
            print(pmId + " FIRST::");
            for (Cooccurrence c : first)
                print(To.string(c));
            print(pmId + " SECOND::");
            for (Cooccurrence c : second)
                print(To.string(c));
            print(pmId + " THIRD::");
            for (Cooccurrence c : third)
                print(To.string(c));

            final Collection<Cooccurrence> union = newLinkedList();
            for (Cooccurrence c : first)
                if (!contains(union, c))
                    union.add(c);
            for (Cooccurrence c : second)
                if (!contains(union, c))
                    union.add(c);
            for (Cooccurrence c : third)
                if (!contains(union, c))
                    union.add(c);
            print(pmId + " UNION::");
            for (Cooccurrence c : union)
                print(To.string(c));

            for (Cooccurrence c : union) {

                if (extractorSetupStr.equals("1 1 1")) {
                    if (contains(first, c) && contains(second, c)
                            && contains(third, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("1 1 0")) {
                    if (contains(first, c) && contains(second, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("1 0 1")) {
                    if (contains(first, c) && contains(third, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("1 0 0")) {
                    if (contains(first, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("0 1 1")) {
                    if (contains(second, c) && contains(third, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("0 1 0")) {
                    if (contains(second, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("0 0 1")) {
                    if (contains(third, c))
                        systAnnot.add(c);

                } else if (extractorSetupStr.equals("best")) {

                    if ((contains(first, c) || contains(second, c))
                            && contains(third, c))
                        systAnnot.add(c);

                } else {
                    throw new Exception("config wrong: " + extractorSetupStr);
                }
            }
            print(pmId + " SYSTEM::");
            for (Object c : systAnnot)
                print(To.string(c));

            print("comparing #gold:" + goldAnnot.size() + " #sys:"
                    + systAnnot.size());

            print(/* "pmId:"+pmId + "\t" + */evaluator.add(goldAnnot,
                    systAnnot, pmId + ""));

        } catch (Exception e) {
            LOG.error("something went wrong with " + pmId, e);
            throw new AnalysisEngineProcessException(e);
        }
    }

    private static void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        // print evaluation results
        System.out.println(evaluator.compare());
    }
}
