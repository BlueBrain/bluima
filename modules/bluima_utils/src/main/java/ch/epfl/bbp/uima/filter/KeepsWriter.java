package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.uima.types.Keep;

/**
 * Writes all {@link Keep}s to a file. One document per line. Useful to train
 * word2vec
 * 
 * @author renaud.richardet@epfl.ch
 */
public class KeepsWriter extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE)
    private String outputFile;

    private static final String PARAM_WRITE_DASHES = "writeDashes";
    @ConfigurationParameter(name = PARAM_WRITE_DASHES, mandatory = false, defaultValue = "false",//
    description = "Whether to replace all spaces in (multi)words with dashes ('_')")
    private boolean writeDashes;

    private PrintWriter writer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(
                    new File(outputFile))));
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    private int i = 0;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        List<String> words = newArrayList();
        for (Keep k : select(jCas, Keep.class)) {
            if (writeDashes) {
                words.add(k.getNormalizedText().replaceAll(" ", "_"));
            } else {
                words.add(k.getNormalizedText());
            }

        }
        writer.println(join(words, " "));

        if (i++ % 1000 == 0)
            writer.flush();
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        closeQuietly(writer);
    }
}