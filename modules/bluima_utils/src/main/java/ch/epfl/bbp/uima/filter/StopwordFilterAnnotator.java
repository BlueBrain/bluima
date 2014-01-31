package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CASE_SENSITIVE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Removes {@link Keep} annotations whose {@link Keep#getNormalizedText()}
 * belongs to a stopword list.<br>
 * Stoplist format: one word per line.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class StopwordFilterAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_CASE_SENSITIVE, defaultValue = "false",//
    description = "If true, tokens are not normalized to lowercase before string comparisions")
    private boolean caseSensitive;

    @ConfigurationParameter(name = PARAM_INPUT_FILE, mandatory = false, //
    description = "path to the stopword list. If none is provided, the stopword list of Mallet is used")
    private String stopwordsFilePath;
    private Set<String> stopList = newHashSet();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            if (stopwordsFilePath == null) {
                stopwordsFilePath = BLUE_UTILS_ROOT + RESOURCES_PATH
                        + "stoplists/mallet_stopwords_en.txt";
            }
            checkFileExists(stopwordsFilePath);
            for (String line : linesFrom(stopwordsFilePath)) {
                if (caseSensitive)
                    stopList.add(line.trim());
                else
                    stopList.add(line.toLowerCase().trim());
            }
        } catch (FileNotFoundException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        Collection<Keep> keeps = select(jCas, Keep.class);
        Keep[] array = keeps.toArray(new Keep[keeps.size()]);

        for (int i = 0; i < array.length; i++) {
            // TODO use normalized or covered?
            String txt = array[i].getNormalizedText();

            if (!caseSensitive)
                txt = txt.toLowerCase();

            if (stopList.contains(txt))
                array[i].removeFromIndexes();
        }
    }
}