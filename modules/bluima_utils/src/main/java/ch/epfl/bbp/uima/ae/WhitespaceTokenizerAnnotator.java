package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.fixNoSentences;
import static ch.epfl.bbp.uima.BlueCasUtil.fixNoText;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;

import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Simple whitespace tokenizer. Useful mostly for tests.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TOKEN }, inputs = { SENTENCE })
public class WhitespaceTokenizerAnnotator extends JCasAnnotator_ImplBase {

    public static final String COMPONENT_ID = WhitespaceTokenizerAnnotator.class
            .getSimpleName();

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        fixNoText(jCas);
        fixNoSentences(jCas);

        for (Sentence sentence : select(jCas, Sentence.class)) {
            int start = sentence.getBegin();
            String text = sentence.getCoveredText();
            if (text.endsWith(".")) // remove trailing dot
                text = text.substring(0, text.length() - 1);

            for (String word : text.split(" ")) {
                Token token = new Token(jCas, start, start + word.length());
                token.setComponentId(COMPONENT_ID);
                token.addToIndexes();
                start += word.length() + 1;
            }
        }
    }
}