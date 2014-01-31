package ch.epfl.bbp.uima.ae;

import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.CasCopier;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

/**
 * Copies specified annotation to view_gold (later used by
 * {@link EvaluationAnnotator}) and removes it from the _InitialView.<br>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class EvaluationPreprocessorAnnotator extends JCasAnnotator_ImplBase {

    public static final String VIEW_SYSTEM = "_InitialView";
    public static final String VIEW_GOLD = "view_gold";
    public static final String PARAM_GOLD_ANNOTATION = "gold_annotation";
    public static final String PARAM_SYSTEM_ANNOTATION = "system_annotation";

    @ConfigurationParameter(name = PARAM_GOLD_ANNOTATION, mandatory = true, description = "the "
            + "annotation class name to copy to the gold view (and remove from initial view")
    private String goldAnnotationStr;
    private Class<? extends Annotation> goldAnnotation;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            goldAnnotation = (Class<? extends Annotation>) Class
                    .forName(goldAnnotationStr);
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // because of later evaluation, copy annotation to view_gold (later used
        // by AnnotationEvaluator) and remove it from view_system.
        Collection<? extends Annotation> goldsFromInitialView = select(jCas,
                goldAnnotation);
        JCas goldView = null;
        try {
            goldView = jCas.createView(VIEW_GOLD);
        } catch (Throwable e) {
            throw new AnalysisEngineProcessException(
                    NO_RESOURCE_FOR_PARAMETERS, new Object[] { VIEW_GOLD }, e);
        }

        CasCopier casCopier = new CasCopier(jCas.getCas(), goldView.getCas());

        goldView.setDocumentText(jCas.getDocumentText());
        // view_system annot. stored in List for later delete
        // (conccurentModifExeption)
        List<Annotation> toDelete = new ArrayList<Annotation>();
        for (Annotation g : goldsFromInitialView) {
            goldView.addFsToIndexes(casCopier.copyFs(g));
            toDelete.add(g);
        }
        Annotation[] arr = toDelete.toArray(new Annotation[toDelete.size()]);
        for (int i = 0; i < arr.length; i++) {
            arr[i].removeFromIndexes(jCas);
        }
    }
}
