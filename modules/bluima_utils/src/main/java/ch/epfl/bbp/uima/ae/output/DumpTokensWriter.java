package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import de.julielab.jules.types.Token;

/**
 * A consumer that writes out {@link Token}s to a text file (one file per
 * {@link JCas})
 * 
 * @author renaud.richardet@epfl.ch
 */
public class DumpTokensWriter extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "output")
    private String outputDir;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {

            PrintWriter writer = new PrintWriter(new BufferedWriter(
                    new FileWriter(new File(outputDir + "/"
                            + getHeaderDocId(jCas) + ".tokens.txt"))));
            for (Token t : select(jCas, Token.class)) {
                writer.println(t.getCoveredText());
            }
            writer.close();

        } catch (Exception e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}
