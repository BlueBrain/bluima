package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static java.util.regex.Pattern.compile;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.io.TextFileWriter;
import de.julielab.jules.types.Sentence;

/**
 * Remove sentences that do not contain a given regex
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { SENTENCE }, outputs = { SENTENCE })
public class SentenceFilterAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "target/")
    private String outputDir;

    public static final String PARAM_REGEX = "regex";
    @ConfigurationParameter(name = PARAM_REGEX, description = "Regex that should find() in the desired sentences")
    private String regex;
    private Pattern pattern;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        pattern = compile(regex);
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        StringBuilder sb = new StringBuilder();
        for (Sentence s : select(jCas, Sentence.class)) {
            if (pattern.matcher(s.getCoveredText()).find()) {
                sb.append(s.getCoveredText().replaceAll("[\t\r\n]", " ") + " ");
            }
        }

        try {
            int pmId = getHeaderIntDocId(jCas);
            TextFileWriter.write(new File(outputDir + "/" + pmId + ".tsv"),
                    pmId + "\t" + sb.toString().trim());
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}