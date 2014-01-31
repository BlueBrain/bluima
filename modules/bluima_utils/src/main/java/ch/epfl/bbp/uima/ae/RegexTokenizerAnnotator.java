package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.fixNoSentences;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Tokenizer that can be configured with a regex {@link Pattern}. Mostly useful
 * for tests.
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { TOKEN }, inputs = { SENTENCE })
public class RegexTokenizerAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(RegexTokenizerAnnotator.class);

    /** Splits on any punctuation character, digits and case change. @see tests */
    public static final String patterPunctDigitsCamelcase = "(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)|(?<=[A-Z])(?=[a-z])|(?<=\\d)(?=\\D)|(?<=\\D)(?=\\d)";

    /** Splits on any punctuation character, @see tests */
    public static final String patterPunctuation = "(?<=\\w)(?=\\W)|(?<=\\W)(?=\\w)";

    /** Splits on any punctuation character, except dashes, @see tests */
    public static final String patterPunctuationNoDash = "(?<=[(a-zA-Z_0-9\\-)])(?=[^(a-zA-Z_0-9\\-)])|(?<=[^(a-zA-Z_0-9\\-)])(?=[(a-zA-Z_0-9\\-)])";

    public static final String PARAM_TOKENIZATION_PATTERN = "tokenizationPattern";
    @ConfigurationParameter(name = PARAM_TOKENIZATION_PATTERN, defaultValue = patterPunctuationNoDash, //
    description = "a String that will be compiled to a regex Pattern, and used for tokenization")
    private String tokenizationPatternString;

    private Pattern tokenizationPattern;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        tokenizationPattern = Pattern.compile(tokenizationPatternString);
    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        fixNoSentences(jcas);

        for (Sentence sentence : select(jcas, Sentence.class)) {
            int start = sentence.getBegin();
            String text = sentence.getCoveredText();
            if (text.endsWith("."))// remove trailing dot
                text = text.substring(0, text.length() - 1);

            String[] split = tokenizationPattern.split(text);
            for (String word : split) {
                if (!word.equals(" ")) {
                    Token token = new Token(jcas, start, start + word.length());
                    token.setComponentId(RegexTokenizerAnnotator.class
                            .getSimpleName());
                    token.addToIndexes();
                    LOG.trace("tagging Token: " + To.string(token));
                }
                start += word.length();
            }
        }
    }
}