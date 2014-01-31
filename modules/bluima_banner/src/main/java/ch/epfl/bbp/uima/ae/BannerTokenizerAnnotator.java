package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import banner.tokenization.SimpleTokenizer;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * BANNER's built-in word tokenizer. Tokens ouput by this tokenizer consist of a
 * contiguous block of alphanumeric characters or a single punctuation mark.
 * Note, therefore, that any construction which contains a punctuation mark
 * (such as a contraction or a real number) will necessarily span over at least
 * three tokens.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { SENTENCE }, outputs = { TOKEN })
public class BannerTokenizerAnnotator extends JCasAnnotator_ImplBase {

    private SimpleTokenizer tokenizer = new SimpleTokenizer();

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        for (Sentence sentence : select(jcas, Sentence.class)) {

            if (sentence.getCoveredText().length() == 0) {
                continue;
            }

            banner.Sentence bannerSentence = new banner.Sentence(
                    sentence.getCoveredText());
            tokenizer.tokenize(bannerSentence);

            for (banner.tokenization.Token bannerToken : bannerSentence
                    .getTokens()) {

                Token t = new Token(jcas, bannerToken.getStart(),
                        bannerToken.getEnd());
                t.addToIndexes();
            }
        }
    }
}
