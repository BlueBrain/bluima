package ch.epfl.bbp.uima.filter;

import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.Collection;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Filtering framework. A place for constants and static methods
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FilterFrameworkUtils {

    /**
     * @param annot
     *            All {@link Keep} annotations under this annotation are removed
     */
    public static void removeKeepsFrom(JCas jCas, Annotation annot) {
        Collection<Keep> sps = selectCovered(jCas, Keep.class, annot);
        if (sps != null && !sps.isEmpty()) {
            Keep[] array = sps.toArray(new Keep[sps.size()]);
            for (int i = 0; i < array.length; i++) {
                array[i].removeFromIndexes();
            }
        }
    }
}
