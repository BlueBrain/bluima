package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.DocumentPage;
import de.julielab.jules.types.Sentence;

/**
 * Add a {@link DocumentPage} annotation every X {@link Sentence}
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { SENTENCE })
public class SplitInPages extends JCasAnnotator_ImplBase {

    private int splitEvery = 10;//LATER configure

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int sentenceCnt = 0;
        int runningPageEnd = 0;

        for (Sentence sentence : JCasUtil.select(jCas, Sentence.class)) {

            if (sentenceCnt++ % splitEvery == 0) {

                new DocumentPage(jCas, runningPageEnd, sentence.getEnd())
                        .addToIndexes();
                runningPageEnd = sentence.getEnd();
            }
        }
    }
}
