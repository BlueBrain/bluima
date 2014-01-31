package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static com.google.common.collect.Lists.newArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * Removes duplicates annotations that have same Class, same begin and same end.
 * TODO: There is no logic yet as how to select the annotation to keep in case
 * of duplicates.
 * 
 * @see KeepLargestAnnotationAnnotator for more sophisticated deduplication
 * 
 * @author renaud.richardet@epfl.ch
 */
public class DeduplicatorAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_ANNOTATION_CLASSES, mandatory = true, //
    description = "an array with the full name of each annotation classes")
    private String[] annotationClasses;

    private List<String> annotationClassesList;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        annotationClassesList = Arrays.asList(annotationClasses);
        // validate that the class exists
        for (String annotationClass : annotationClasses) {
            try {
                @SuppressWarnings({ "unchecked", "unused" })
                Class<? extends Annotation> classz = (Class<? extends Annotation>) Class
                        .forName(annotationClass);
            } catch (Exception e) {
                throw new ResourceInitializationException(e);
            }
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // store all annotation in map
        // k: annotation v: (MMap of k:beg-end v:annotation)
        Map<String, Multimap<String, Annotation>> map = new HashMap<String, Multimap<String, Annotation>>();
        for (Annotation a : jCas.getAnnotationIndex()) {
            String name = a.getClass().getName();
            if (annotationClassesList.contains(name)) {

                if (!map.containsKey(name))
                    map.put(name,
                            ArrayListMultimap.<String, Annotation> create());

                String begEnd = a.getBegin() + ":" + a.getEnd();
                map.get(name).put(begEnd, a);
            }
        }

        // iterate map and remove all annot but 1
        for (String aClass : map.keySet()) {
            Multimap<String, Annotation> asMap = map.get(aClass);
            for (String begEnd : asMap.keySet()) {
                List<Annotation> as = newArrayList(asMap.get(begEnd));
                while (as.size() > 1) {
                    as.get(0).removeFromIndexes(jCas);
                    as.remove(0);
                }
            }
        }
    }
}