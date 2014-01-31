package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Token;

/**
 * Add a {@link Keep} for every {@link Token}. Useful when testing the filtering
 * steps, without using {@link ViterbiFilterAnnotator}.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN }, outputs = { KEEP })
public class Tokens2KeepAnnotator extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (Token t : select(jCas, Token.class)) {

            Keep k = new Keep(jCas, t.getBegin(), t.getEnd());
            k.setEnclosedAnnot(t);
            k.setNormalizedText(t.getCoveredText()); // just the token text
            k.addToIndexes();
        }
    }
}