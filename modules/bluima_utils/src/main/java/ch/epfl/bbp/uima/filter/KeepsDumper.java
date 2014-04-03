package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static org.apache.uima.fit.util.JCasUtil.*;

import java.util.Collection;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Sentence;

/**
 * Dumps all {@link Keep}s to sysout, useful for debugging
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepsDumper extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String pmId = getHeaderDocId(jCas);
        System.out.println(pmId + " -----------------------------");

        Map<Sentence, Collection<Keep>> sentences = indexCovered(jCas, //
                Sentence.class, Keep.class);

        for (Sentence s : JCasUtil.select(jCas, Sentence.class)) {

            for (Keep k : sentences.get(s)) {
                System.out.print(k.getNormalizedText() + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}