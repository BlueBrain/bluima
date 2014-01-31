package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CASE_SENSITIVE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static java.lang.Integer.parseInt;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Removes {@link Keep} annotations that are too frequent or not frequent enough
 * (aka "hapax"), based on a frequency list (that has been generated with
 * {@link FrequencyFilterWriter}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class FrequencyFilterAnnotator extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(FrequencyFilterAnnotator.class);

    public static final String MINIMUM_FREQUENCY = "minimumFrequency";
    @ConfigurationParameter(name = MINIMUM_FREQUENCY, mandatory = true,//
    description = "minimum frequency of token to be retained")
    private int minimumFrequency;
    public static final String MAXIMUM_FREQUENCY = "maximumFrequency";
    @ConfigurationParameter(name = MAXIMUM_FREQUENCY, mandatory = true,//
    description = "maximum frequency of token to be retained")
    private int maximumFrequency;

    @ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false",//
    description = "If true, tokens are not normalized to lowercase before string comparisions")
    private boolean caseSensitive;

    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = true, //
    description = "Path to file containing tokens and their frequency in the corpus")
    private String tokenFrequencyFile;
    /**
     * Set of text tokens whose frequency is acceptable (not too frequent, nor
     * not frequent enough)
     */
    private Set<String> normalText = newHashSet();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        // load frequency list into Set
        try {
            for (String line : linesFrom(tokenFrequencyFile)) {
                String[] split = line.split("\t");
                int freq = parseInt(split[1]);
                if (freq > minimumFrequency && freq < maximumFrequency) {
                    String txt = caseSensitive ? split[0] : split[0]
                            .toLowerCase();
                    normalText.add(txt);
                }
            }
        } catch (FileNotFoundException e) {
            throw new ResourceInitializationException(e);
        }

        // validate min max params
        checkArgument(maximumFrequency > minimumFrequency,
                "expected: maximumFrequency>minimumFrequency");
        checkArgument(minimumFrequency >= 0, "expected: minimumFrequency >= 0");
        if (maximumFrequency == 0)
            LOG.info("FrequencyFilterAnnotator's minimumFrequency set to 0 --> Filter inactive");
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (minimumFrequency == 0)// filter "inactive"
            return;

        Collection<Keep> keeps = select(jCas, Keep.class);
        Keep[] array = keeps.toArray(new Keep[keeps.size()]);

        for (int i = 0; i < array.length; i++) {

            String txt = caseSensitive ? array[i].getNormalizedText()
                    : array[i].getNormalizedText().toLowerCase();

            if (!normalText.contains(txt))
                array[i].removeFromIndexes();
        }
    }
}