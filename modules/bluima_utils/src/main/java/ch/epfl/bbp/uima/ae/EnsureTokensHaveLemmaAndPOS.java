package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;

import de.julielab.jules.types.Lemma;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Token;

/**
 * Checks that each {@link Token} has a POS and a lemma, sets otherwise the POS
 * to a "null" string, resp. the lemma to the coveredText itself
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN })
public class EnsureTokensHaveLemmaAndPOS extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        for (Token token : JCasUtil.select(jcas, Token.class)) {

            if (token.getPosTag() == null) {
                setPosTags(token, jcas, "null");
            }
            if (token.getLemma() == null) {
                setLemma(token, jcas, token.getCoveredText());
            }
        }
    }

    /** Convenience method to set POS tag */
    public static void setPosTags(Token token, JCas jcas, String... posTags) {

        FSArray slots = new FSArray(jcas, posTags.length);
        for (int i = 0; i < posTags.length; i++) {
            POSTag posTag = new POSTag(jcas);
            posTag.setValue(posTags[i]);
            posTag.addToIndexes();
            slots.set(i, posTag);
        }
        token.setPosTag(slots);
    }

    /** Convenience method to set POS tag */
    public static void setLemma(Token token, JCas jcas, String lemmaStr) {
        Lemma lemma = new Lemma(jcas);
        lemma.setValue(lemmaStr);
        lemma.addToIndexes();
        token.setLemma(lemma);
    }
}
