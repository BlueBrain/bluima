package ch.epfl.bbp.uima.ae.output;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueUima;

/**
 * Counts the total amount of occurrences of some specified annotations
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CountAnnotationsAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(CountAnnotationsAnnotator.class);

    @ConfigurationParameter(name = BlueUima.PARAM_ANNOTATION_CLASS, //
    mandatory = true, description = "annotation class name to count")
    private String fromAnnotationStr;

    private Class<? extends Annotation> fromAnnotation;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            fromAnnotation = (Class<? extends Annotation>) Class
                    .forName(fromAnnotationStr);
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        LOG.info("{} {}-annotations", select(jCas, fromAnnotation).size(),
                fromAnnotation.getName());
    }
}