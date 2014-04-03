package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CASE_SENSITIVE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.KEEP;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.triechar.Trie;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.Keep;

/**
 * Generates a frequency list, based on {@link Keep#getNormalizedText()}, and on
 * whether to filter the whole document with {@link BlueCasUtil#keepDoc(JCas)}.
 * This frequency list can be used with {@link FrequencyFilterAnnotator}.<br>
 * sort it with sort -t $'\t' -k 2nr vocab_file
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { KEEP }, outputs = {})
public class FrequencyFilterWriter extends JCasAnnotator_ImplBase {

    // LATER allow to not write the whole trie, only if > minfreq
    // public static final String MINIMUM_FREQUENCY = "minimumFrequency";
    // @ConfigurationParameter(name = MINIMUM_FREQUENCY, mandatory = true,//
    // description = "minimum frequency of token to be retained")
    // private int minimumFrequency;

    @ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false",//
    description = "If true, tokens are not normalized to lowercase before string comparisions")
    private boolean caseSensitive;

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, description = "Where to write frequency file")
    private String tokenFrequencyFile;

    private Trie trie;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        trie = new Trie(caseSensitive);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        if (BlueCasUtil.keepDoc(jCas))
            for (Keep keep : select(jCas, Keep.class)) {
                // System.err.println(keep.getNormalizedText() + "\t\t"
                // + keep.getEnclosedAnnot().getCoveredText() + "\t"
                // + keep.getEnclosedAnnot().getClass());
                trie.addWord(keep.getNormalizedText());
            }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        try {
            trie.toFrequencyFile(tokenFrequencyFile);
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}