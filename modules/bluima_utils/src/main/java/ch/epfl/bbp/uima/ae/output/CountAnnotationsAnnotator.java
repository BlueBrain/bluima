package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * At the end of the whole pipeline, prints the count of occurrences of some
 * specified annotations
 * 
 * @see AnnotationHistogramAnnotator
 * @see AnnotationInstanceHistogramAnnotator
 * @author renaud.richardet@epfl.ch
 */
public class CountAnnotationsAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(CountAnnotationsAnnotator.class);

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, //
    mandatory = true, description = "annotation class name to count")
    private String fromAnnotationStr;

    private Class<? extends Annotation> fromAnnotation;

    int totalCount;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            fromAnnotation = (Class<? extends Annotation>) Class
                    .forName(fromAnnotationStr);
            totalCount = 0;
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int cnt = select(jCas, fromAnnotation).size();
        LOG.info("{} {}-annotations", cnt, fromAnnotation.getName());
        totalCount += cnt;
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        LOG.info("TOTAL {} {}-annotations", totalCount,
                fromAnnotation.getName());

    }
}