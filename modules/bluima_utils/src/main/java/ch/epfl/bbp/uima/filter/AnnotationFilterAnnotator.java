package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Filters {@link Keep}s that enclose the configured {@link Annotation}s.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = {})
public class AnnotationFilterAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASSES, mandatory = true, //
    description = "an array with the full name of each annotation classes")
    private String[] annotationClasses;

    @SuppressWarnings("rawtypes")
    private Set<Class> annotationClassesList = new java.util.HashSet<Class>();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        // validate that the class exists
        for (String annotationClass : annotationClasses) {
            try {
                @SuppressWarnings({ "unchecked" })
                Class<? extends Annotation> classz = (Class<? extends Annotation>) Class
                        .forName(annotationClass);
                checkNotNull(classz, "could not load class " + annotationClass);
                annotationClassesList.add(classz);
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Keep> keeps = select(jCas, Keep.class);
        Keep[] array = keeps.toArray(new Keep[keeps.size()]);

        for (int i = 0; i < array.length; i++) {
            Class<? extends Annotation> classz = array[i].getEnclosedAnnot()
                    .getClass();
            if (annotationClassesList.contains(classz)) {
                array[i].removeFromIndexes();
            }
        }
    }
}
