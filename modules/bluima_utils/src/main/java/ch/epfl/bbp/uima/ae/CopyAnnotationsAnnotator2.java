package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.VIEW_SYSTEM;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Feature;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.component.ViewCreatorAnnotator;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Copies specified annotations into other specified annotations, then removes
 * the former.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CopyAnnotationsAnnotator2 extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, mandatory = true, //
    description = "annotation class name to copy")
    private String annotationStr;
    private Class<? extends Annotation> annotation;

    public static final String FROM_VIEW = "fromView";
    @ConfigurationParameter(name = FROM_VIEW, mandatory = false, //
    description = "view name to copy from", defaultValue = VIEW_SYSTEM)
    private String fromViewStr;

    public static final String TO_VIEW = "toView";
    @ConfigurationParameter(name = TO_VIEW, mandatory = true, //
    description = "view name to copy from")
    private String toViewStr;

    public static final String DELETE_FROM = "deleteFrom";
    @ConfigurationParameter(name = DELETE_FROM, defaultValue = "true", //
    description = "whether to delete the 'from' annotations (default), or leave them")
    private boolean deleteFrom;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            annotation = (Class<? extends Annotation>) Class
                    .forName(annotationStr);
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        JCas fromJCas = ViewCreatorAnnotator
                .createViewSafely(jCas, fromViewStr);
        JCas toJCas = ViewCreatorAnnotator.createViewSafely(jCas, toViewStr);

        Collection<? extends Annotation> fromsCol = select(fromJCas, annotation);
        Annotation[] froms = fromsCol.toArray(new Annotation[fromsCol.size()]);
        for (Annotation from : froms) {
            copyAnnotationToView(from, toJCas);
        }
        if (deleteFrom) {
            for (int i = 0; i < froms.length; i++) {
                froms[i].removeFromIndexes(fromJCas);
            }
        }
    }

    public static Annotation copyAnnotationToView(Annotation a, JCas view) {
        // To copy the annotation we must process in three steps
        // 1- Clone the annotation from the original view
        Annotation a2 = (Annotation) a.clone();
        // 2- Change the Sofa of the cloned annotation
        Feature sofaFeature = a2.getType().getFeatureByBaseName("sofa");
        a2.setFeatureValue(sofaFeature, view.getSofa());
        // 3- Add this annotation to the indexes of the new view
        a2.addToIndexes(view);
        return a2;
    }
}