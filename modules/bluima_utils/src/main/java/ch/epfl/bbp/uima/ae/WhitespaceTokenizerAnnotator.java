package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Simple whitespace tokenizer. Useful mostly for tests.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TOKEN }, inputs = { SENTENCE })
public class WhitespaceTokenizerAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(WhitespaceTokenizerAnnotator.class);

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        for (Sentence sentence : select(jcas, Sentence.class)) {
            int start = sentence.getBegin();
            String text = sentence.getCoveredText();
            if (text.endsWith("."))// remove trailing dot
                text = text.substring(0, text.length() - 1);

            for (String word : text.split(" ")) {
                Token token = new Token(jcas, start, start + word.length());
                token.setComponentId(WhitespaceTokenizerAnnotator.class
                        .getSimpleName());
                token.addToIndexes();
                start += word.length() + 1;
                LOG.trace("tagging Token: " + To.string(token));
            }
        }
    }
}