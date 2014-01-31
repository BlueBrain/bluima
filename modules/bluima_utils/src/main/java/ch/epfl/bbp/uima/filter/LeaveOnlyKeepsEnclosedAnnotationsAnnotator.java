package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static ch.epfl.bbp.uima.typesystem.TypeSystemSemantics.NON_CONTENT_ANNOTATIONS;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.List;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.ae.output.BartWriter;
import ch.epfl.bbp.uima.types.Keep;

/**
 * Remove ALL {@link Annotation}s, except the ones referenced by {@link Keep}
 * annotations. Useful as a global cleanup after the filtering step, e.g. to
 * display annotations in {@link BartWriter}
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = { KEEP })
public class LeaveOnlyKeepsEnclosedAnnotationsAnnotator extends
        JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // store annotations referenced by Keeps in a whitelist
        // (NOT the Keep annotation itself, this one gets deleted as well)
        Set<Integer> annotsToKeepIndex = newHashSet();
        for (Keep k : select(jCas, Keep.class))
            annotsToKeepIndex.add(k.getEnclosedAnnot().getAddress());

        // prune all other content-annotations
        List<Annotation> toDelete = newArrayList();
        for (Annotation a : jCas.getAnnotationIndex()) {

            if (!NON_CONTENT_ANNOTATIONS.contains(a.getClass().getName())
                    && !annotsToKeepIndex.contains(a.getAddress())) {
                toDelete.add(a);
            }
        }
        Annotation[] arr = toDelete.toArray(new Annotation[toDelete.size()]);
        for (int i = 0; i < arr.length; i++) {
            arr[i].removeFromIndexes(jCas);
        }
    }
}