package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOO_MANY_OOV;
import static com.google.common.collect.Maps.newHashMap;
import static java.lang.Character.isDigit;
import static java.lang.Integer.parseInt;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.TooManyOOV;
import de.julielab.jules.types.Token;

/**
 * Document-level filtering. Flags a document that has too much OOV tokens,
 * compared to a frequency list. Some basic whitelisting is performed
 * (Uppercase-then-lowercases or 2x-more-digits-than-letters).
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN }, outputs = { TOO_MANY_OOV })
public class TooMuchOOVFilterAnnotator extends JCasAnnotator_ImplBase {

    private static final double RATIO_CUTOFF = 0.4;

    // TODO as param
    final String frequencyFile = BlueUima.BLUE_UTILS_ROOT
            + "src/main/resources/preprocessing/freq_10000.tsv";
    // + "src/main/resources/preprocessing/freq_10000_cleaned.tsv";
    // + "src/main/resources/preprocessing/freq_1000.tsv";
    private Map<String, Integer> frequencyMap = newHashMap();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        try {
            // load token frequency file
            for (List<String> line : new ch.epfl.bbp.io.SVReader.TSVReader(
                    new File(frequencyFile), false)) {
                String token = line.get(0);
                if (token.startsWith("#"))
                    continue;
                int freq = parseInt(line.get(1));
                frequencyMap.put(token, freq);
            }
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Token> tokens = select(jCas, Token.class);

        if (tokens != null) {
            double tokenCnt = tokens.size();
            double freqTokens = 0;

            for (Token t : tokens) {
                String txt = t.getCoveredText();
                if (frequencyMap.containsKey(txt.toLowerCase()) || isOk(txt)) {
                    freqTokens++;
                }
            }

            double ratio = freqTokens / tokenCnt;
            // String pmId = BlueCasUtil.getHeaderDocId(jCas);
            // String source = BlueCasUtil.getHeaderSource(jCas);
            // System.out.println(pmId + "\t" + source + "\tratiooo\t" +
            // ratio+"\ttokenCnt\t"+tokenCnt);
            if (ratio < RATIO_CUTOFF) {
                new TooManyOOV(jCas).addToIndexes();
            }
        }
    }

    final static Pattern UPPER_THEN_LOWER = Pattern.compile("[A-Z]\\p{L}+");

    /**
     * Whitelisting. Ok if Uppercase-then-lowercases or
     * 2x-more-digits-than-letters.
     * 
     * On a sample doc (), went from 1975 OOV to 1116 with
     * Uppercase-then-lowercases, then down to 729 with
     * 2x-more-digits-than-letters.
     * 
     * @return true if this token is acceptable (=considered NOT an OOV).
     */
    private static boolean isOk(String txt) {

        if (UPPER_THEN_LOWER.matcher(txt).matches())
            return true;

        int ints = 0;
        for (char c : txt.toCharArray()) {
            if (isDigit(c)) {
                ints++;
            }
        }
        if (ints * 2 > txt.length())
            return true;

        return false;
    }
}