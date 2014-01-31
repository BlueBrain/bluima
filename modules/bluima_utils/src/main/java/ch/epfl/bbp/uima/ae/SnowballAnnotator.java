package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.utils.SnowballStemmer;
import de.julielab.jules.types.Token;

/**
 * Snowball stemmer annotator for {@link Token}s
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN })
public class SnowballAnnotator extends JCasAnnotator_ImplBase {

    private final SnowballStemmer stemmer = new SnowballStemmer();

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        for (Token token : select(jcas, Token.class)) {

            String stem = stemmer.stem(token.getCoveredText());

            EnsureTokensHaveLemmaAndPOS.setLemma(token, jcas, stem);
            token.setLemmaStr(stem);
        }
    }
}
