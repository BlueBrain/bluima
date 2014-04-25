package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_FIELD;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.lang.reflect.Method;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.uima.typesystem.To;

/**
 * At the end of the whole pipeline, prints a histogram (counts) of all instances of a
 * given {@link Annotation}. E.g.
 * 
 * <pre>
 * hello: 123
 * world: 4312
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch *
 */
public class AnnotationInstanceHistogramAnnotator extends
        JCasAnnotator_ImplBase {
    private static final Logger LOG = LoggerFactory
            .getLogger(AnnotationInstanceHistogramAnnotator.class);

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, //
    mandatory = true, description = "annotation class name to count")
    private String annotationStr;
    private Class<? extends Annotation> annotation;

    @ConfigurationParameter(name = PARAM_ANNOTATION_FIELD, defaultValue = "coveredText",//
    description = "The name of the annotation field to get the title from.", mandatory = false)
    protected String fieldRaw;
    protected Method fieldMethod;

    private Histogram<String> histogram = new Histogram<String>();

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            annotation = (Class<? extends Annotation>) Class
                    .forName(annotationStr);

            for (Method m : annotation.getMethods()) {
                if (m.getName().equals("get" + capitalize(fieldRaw.trim()))
                        && m.getParameterTypes().length == 0)
                    fieldMethod = m;
            }

            if (fieldMethod == null) {
                throw new RuntimeException(fieldRaw + " field for annot "
                        + annotation.getCanonicalName() + " not found");
            }
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Annotation a : select(jCas, annotation)) {
            try {
                Object res = fieldMethod.invoke(a);
                if (res != null) {
                    histogram.add(res.toString().replaceAll("[\t\n]", " "));
                }
            } catch (Exception e) {
                LOG.warn(getHeaderDocId(jCas)
                        + " could not call {} on annotation {}", fieldRaw,
                        To.string(a));
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        System.out.println("Histogram " + annotation.getSimpleName() + "::"
                + fieldRaw + "\n" + histogram.toString()
                + "------------------------\n");
    }
}
