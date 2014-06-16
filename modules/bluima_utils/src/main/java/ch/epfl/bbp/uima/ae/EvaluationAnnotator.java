package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_VERBOSE;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.PARAM_GOLD_ANNOTATION;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.PARAM_SYSTEM_ANNOTATION;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.VIEW_GOLD;
import static ch.epfl.bbp.uima.ae.EvaluationPreprocessorAnnotator.VIEW_SYSTEM;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CASException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.validation.TestEvaluator;

/**
 * Evaluates precision, recall and f-score, comparing annotations in Gold sofa
 * with the annotions in normal view.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class EvaluationAnnotator extends JCasAnnotator_ImplBase {
    // private static Logger LOG = LoggerFactory.getLogger(BartWriter.class);

    @ConfigurationParameter(name = PARAM_GOLD_ANNOTATION, mandatory = true, description = "the "
            + "annotation class name to copy to the gold view (and remove from initial view")
    private String goldAnnotationStr;
    private Class<? extends Annotation> goldAnnotation;

    @ConfigurationParameter(name = PARAM_SYSTEM_ANNOTATION, mandatory = true, description = "the "
            + "annotation class name to copy to the gold view (and remove from initial view")
    private String systemAnnotationStr;
    private Class<? extends Annotation> systemAnnotation;

    @ConfigurationParameter(name = "evaluator", defaultValue = "exact",//
    description = "possible values: atLeastCovered, atLeastCovering, exact, overlap")
    private String evaluatorType;
    TestEvaluator<Annotation, Annotation> evaluator;

    @ConfigurationParameter(name = PARAM_VERBOSE, mandatory = false, //
    defaultValue = "true", description = "whether to print verbose logs")
    private boolean verbose;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            goldAnnotation = (Class<? extends Annotation>) Class
                    .forName(goldAnnotationStr);
            systemAnnotation = (Class<? extends Annotation>) Class
                    .forName(systemAnnotationStr);
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
        if (evaluatorType.equals("atLeastCovered"))
            evaluator = TestEvaluator.getAtLeastCoveredEvaluator();
        else if (evaluatorType.equals("atLeastCovering"))
            evaluator = TestEvaluator.getAtLeastCoveringEvaluator();
        else if (evaluatorType.equals("overlap"))
            evaluator = TestEvaluator.getOverlapEvaluator();
        else if (evaluatorType.equals("exact"))
            evaluator = TestEvaluator.getExactEvaluator();
        else
            throw new ResourceInitializationException(
                    new IllegalArgumentException(
                            "not a valid parameter for 'evaluator': "
                                    + evaluatorType));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        JCas goldView, systemView;
        try {
            goldView = jCas.getView(VIEW_GOLD);
            systemView = jCas.getView(VIEW_SYSTEM);
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }

        Collection goldAnnot = select(goldView, goldAnnotation);
        Collection systAnnot = select(systemView, systemAnnotation);
        if (verbose)
            System.out
                    .println("comparing #gold:" + goldAnnot.size() + " #sys:"
                            + systAnnot.size() + " [pmid "
                            + getHeaderDocId(jCas) + "]");

        String log = evaluator.add(goldAnnot, systAnnot, "");
        if (verbose)
            System.out.println(log);
        // System.out.println(evaluator.compare());
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        // print evaluation results
        System.out.println(evaluator.compare());
    }
}