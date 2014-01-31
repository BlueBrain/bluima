package ch.epfl.bbp.uima.ae;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator;
import ch.epfl.bbp.uima.types.Verb;
import ch.epfl.bbp.uima.types.VerbDictTerm;
import de.julielab.jules.types.Token;

/**
 * Adds annotations for: 1) modal verbs (e.g. should, ...) and 2) other verbs
 * (if not covered by VerbDictTerm)<br>
 * Removes all other annotations if POS = V
 * 
 * Use {@link SkipSomePosAnnotator}
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
@Deprecated
public class VerbsCleanerAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Set<Annotation> toRemove = new HashSet<Annotation>();

        for (Token t : JCasUtil.select(jCas, Token.class)) {
            String pos = t.getPos();
            if (pos != null) {

                // modal verbs (e.g. should, ...)
                if (pos.equals("MD")) {
                    Verb v = new Verb(jCas, t.getBegin(), t.getEnd());
                    v.setIsModal(true);
                    v.addToIndexes();

                }

                // other verbs: add if not covered by BioLexicon
                else if (pos.startsWith("V")) {

                    List<VerbDictTerm> covered = JCasUtil.selectCovered(jCas,
                            VerbDictTerm.class, t.getBegin(), t.getEnd());
                    if (covered.isEmpty()) {

                        Verb v = new Verb(jCas, t.getBegin(), t.getEnd());
                        v.setIsModal(true);
                        v.addToIndexes();

                    } else {

                        // we have a VerbDictTerm, we need to remove all
                        // other bio entities annotations
                        for (Annotation maybeRemove : JCasUtil.selectCovered(
                                jCas, Annotation.class, t.getBegin(),
                                t.getEnd())) {
                            if (DisambiguatorAnnotator.SKIP_ANNOTATIONS
                                    .contains(maybeRemove.getClass().getName())) {
                                toRemove.add(maybeRemove);
                            }
                        }
                    }
                }
                // POS is not verbal, remove VerbDictTerm if any
                else {
                    List<VerbDictTerm> covered = JCasUtil.selectCovered(jCas,
                            VerbDictTerm.class, t.getBegin(), t.getEnd());
                    for (VerbDictTerm c : covered) {
                        c.removeFromIndexes(jCas);
                    }
                }
            }
        }

        Annotation[] toRemoveA = toRemove.toArray(new Annotation[toRemove
                .size()]);
        for (int i = 0; i < toRemoveA.length; i++) {
            toRemoveA[i].removeFromIndexes();
        }
    }
}