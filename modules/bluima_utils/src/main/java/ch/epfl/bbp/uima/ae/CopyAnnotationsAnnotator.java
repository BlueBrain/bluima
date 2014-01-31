package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.VIEW_SYSTEM;
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

import ch.epfl.bbp.uima.BlueUima;

/**
 * Copies specified annotations into other specified annotations, then removes
 * the former.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CopyAnnotationsAnnotator extends JCasAnnotator_ImplBase {

    public static final String FROM_ANNOTATION = "from";
    @ConfigurationParameter(name = FROM_ANNOTATION, mandatory = true, //
    description = "annotation class name to copy")
    private String fromAnnotationStr;
    private Class<? extends Annotation> fromAnnotation;

    public static final String TO_ANNOTATION = "to";
    @ConfigurationParameter(name = TO_ANNOTATION, mandatory = true, //
    description = "annotation class name to copy")
    private String toAnnotationStr;
    private Class<? extends Annotation> toAnnotation;

    public static final String FROM_VIEW = "fromView";
    @ConfigurationParameter(name = FROM_VIEW, mandatory = false, //
    description = "view name to copy from", defaultValue = VIEW_SYSTEM)
    private String fromViewStr;

    public static final String TO_VIEW = "toView";
    @ConfigurationParameter(name = TO_VIEW, mandatory = false, //
    description = "view name to copy from", defaultValue = VIEW_SYSTEM)
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
            fromAnnotation = (Class<? extends Annotation>) Class
                    .forName(fromAnnotationStr);
            toAnnotation = (Class<? extends Annotation>) Class
                    .forName(toAnnotationStr);
            // TODO should not allow to copy if they are subclass of eachother
        } catch (Throwable t) {
            throw new ResourceInitializationException(t);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        JCas fromJCas = jCas, toJCas = jCas;
        try {
            if (!fromViewStr.equals(BlueUima.VIEW_SYSTEM))
                fromJCas = jCas.getView(fromViewStr);
            if (!toViewStr.equals(BlueUima.VIEW_SYSTEM))
                toJCas = jCas.getView(toViewStr);
        } catch (CASException e) {
            throw new AnalysisEngineProcessException(e);
        }

        Collection<? extends Annotation> fromsCol = select(fromJCas,
                fromAnnotation);
        Annotation[] froms = fromsCol.toArray(new Annotation[fromsCol.size()]);

        for (Annotation from : froms) {
            try {
                Annotation to = toAnnotation.getDeclaredConstructor(JCas.class,
                        int.class, int.class).newInstance(toJCas,
                        from.getBegin(), from.getEnd());
                // TODO so far, only copies begin/end
                toJCas.addFsToIndexes(to);
            } catch (Exception e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
        if (deleteFrom) {
            for (int i = 0; i < froms.length; i++) {
                froms[i].removeFromIndexes(fromJCas);
            }
        }
    }
}