package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.SENTENCE;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.cr.OneDocPerLineReader;
import de.julielab.jules.types.Sentence;

/**
 * Format is:<br>
 * <code>pmId:sentId{tab}text</code>
 * 
 * @see OneDocPerLineReader
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { SENTENCE })
public class SentenceDumpAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_FILE, defaultValue = "sentences.txt", //
    description = "A path to the output file")
    private String outputFile;

    private TextFileWriter writer;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            writer = new TextFileWriter(new File(outputFile));
        } catch (IOException e) {
            throw new ResourceInitializationException();
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String pmId = getHeaderDocId(jCas);
        int sentenceId = 0;

        for (Sentence sentence : select(jCas, Sentence.class)) {
            writer.addLine(pmId + ":" + sentenceId++ + "\t"
                    + sentence.getCoveredText());
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        try {
            writer.flush();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}