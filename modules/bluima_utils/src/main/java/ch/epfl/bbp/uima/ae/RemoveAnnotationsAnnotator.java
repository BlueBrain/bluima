package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

/**
 * Removes specific annotation from the {@link CAS}es
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RemoveAnnotationsAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, mandatory = true, //
    description = "The name (including package) of the class to be detagged, use: MyClass.class.getName(), or 'all' to remove ALL annotations")
    private String className = null;
    private Class<? extends Annotation> aClass;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            if (!className.equals("all"))
                aClass = (Class<? extends Annotation>) Class.forName(className);
        } catch (Throwable t) {
            throw new RuntimeException("could not find class " + className);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // Copy the tokens into a new collection to avoid
        // ConcurrentModificationExceptions
        if (className.equals("all")) {
            for (TOP t : newArrayList(select(jCas, TOP.class)))
                t.removeFromIndexes();
        } else {
            for (Annotation a : newArrayList(select(jCas, aClass)))
                a.removeFromIndexes();
        }
    }
}
