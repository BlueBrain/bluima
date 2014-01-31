package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Prunes/dedupes overlapping Annotations, keeps the largest one.
 * 
 * @see DeduplicatorAnnotator for simpler algo
 * @see ViterbiAnnotator for complex tasks and multiple annotation types
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepLargestAnnotationAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, mandatory = true, //
    description = "the full name of the annotation class")
    private String annotationClassStr;
    private Class<? extends Annotation> annotationClass;

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            annotationClass = (Class<? extends Annotation>) Class
                    .forName(annotationClassStr);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        List<? extends Annotation> annots = new ArrayList<Annotation>(select(
                jcas, annotationClass));
        Set<Annotation> toDelete = newHashSet();

        for (int i = 0; i < annots.size(); i++) {
            Annotation outer = annots.get(i);
            if (!toDelete.contains(outer)) {
                toDelete.addAll(selectCovered(annotationClass, outer));
            }
        }

        Annotation[] arr = toDelete.toArray(new Annotation[toDelete.size()]);
        for (int i = 0; i < arr.length; i++) {
            arr[i].removeFromIndexes(jcas);
        }
    }
}