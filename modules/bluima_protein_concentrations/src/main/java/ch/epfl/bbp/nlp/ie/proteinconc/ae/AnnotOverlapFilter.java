package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import ch.epfl.bbp.uima.BlueCasUtil;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import java.util.LinkedList;
import java.util.List;

import static org.apache.uima.fit.util.JCasUtil.select;

/**
 * Removes an annotation when it overlaps with other
 * specified ones. The annotation one want to remove
 * in case of overlapping is called the protected
 * annotation as we protect it against overlapping.
 * The annotations which we do not allow the protected
 * one to overlap with are called the filtered annotations.
 *
 * Note that only instances of the protected annotation will
 * be remove from the index.
 * 
 * @author Philémon Favrod (philemon.favrod@epfl.ch)
 * 
 */

public class AnnotOverlapFilter extends
        org.apache.uima.fit.component.JCasAnnotator_ImplBase {

    public static final String PARAM_PROTECTED_ANNOTATION = "protectedAnnotationClassName";
    public static final String PARAM_FILTERED_ANNOTATIONS = "filteredAnnotationsClassNames";

    @ConfigurationParameter(name = PARAM_PROTECTED_ANNOTATION, mandatory = true,//
    description = "the annotation we want to protect against overlaps")
    private String protectedAnnotationClassName;

    @ConfigurationParameter(name = PARAM_FILTERED_ANNOTATIONS, mandatory = true,//
    description = "the forbiden-to-overlap annotations")
    private String[] filteredAnnotationsClassNames;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        //First, one use java reflection to check the existence of the given
        //annotation's classes.
        Class<? extends Annotation> protectedAnnotationClass;
        List<Class<? extends Annotation>> filteredAnnotationClasses = new LinkedList<Class<? extends Annotation>>();

        try {

            protectedAnnotationClass = (Class<? extends Annotation>) Class
                    .forName(protectedAnnotationClassName);
            for (String filteredAnnotationClassName : filteredAnnotationsClassNames) {
                filteredAnnotationClasses
                        .add((Class<? extends Annotation>) Class
                                .forName(filteredAnnotationClassName));
            }

        } catch (ClassNotFoundException e) {
            throw new AnalysisEngineProcessException(e);
        }

        //Then, one simply go through all pairs of the existing annotations
        //verifying if they overlap (distance = -1)
        List<Annotation> toDelete = new LinkedList<Annotation>();
        for (Annotation protectedOccurrence : select(jCas,
                protectedAnnotationClass)) {

            for (Class<? extends Annotation> filteredAnnotationClass : filteredAnnotationClasses) {
                for (Annotation filteredAnnotOccurrence : select(jCas,
                        filteredAnnotationClass)) {
                    boolean overlap = BlueCasUtil.distance(protectedOccurrence,
                            filteredAnnotOccurrence) == -1;
                    if (overlap) {
                        toDelete.add(protectedOccurrence);
                    }
                }
            }

        }

        for (Annotation annot : toDelete) {
            annot.removeFromIndexes();
        }
    }
}
