package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static org.apache.uima.fit.util.JCasUtil.*;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Sentence;

/**
 * Dumps all {@link Keep}s to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepsDumper extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String pmId = getHeaderDocId(jCas);
        System.out.println(pmId + " -----------------------------");

        for (Collection<Keep> sentence : indexCovered(jCas, //
                Sentence.class, Keep.class).values()) {

            for (Keep k : sentence) {
                System.out.print(k.getNormalizedText() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}