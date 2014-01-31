package ch.epfl.bbp.uima.laucher;

import static org.apache.commons.lang.StringUtils.join;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.apache.uima.fit.factory.JCasFactory;

import ch.epfl.bbp.uima.typesystem.TypeSystem;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;

/**
 * Annotate text, based on a pipeline
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AnnotatorService {
    private static Logger LOG = getLogger(AnnotatorService.class);

    private JcasPipelineBuilder jCasBuilder;

    public AnnotatorService(File scriptFile, List<String> cliArgs) {
        try {

            if (!scriptFile.exists()) {
                throw new IOException("Script file does not exist ("
                        + scriptFile.getAbsolutePath() + ")");
            }

            LOG.info("Parsing pipeline script at '{}'",
                    scriptFile.getAbsolutePath() + " \n with CLI parameters: "
                            + join(cliArgs, ", "));

            Pipeline pipeline = PipelineScriptParser.parse(scriptFile, cliArgs);
            jCasBuilder = pipeline.getJCasBuilder();
            LOG.info("Successfully parsed pipeline script");
        } catch (Exception e) {
            throw new RuntimeException(e);// TODO
        }
    }

    public void start() throws UIMAException, IOException, CpeDescriptorException {
        annotate("");
    }

    public JCas annotate(String text) throws UIMAException, IOException,
            CpeDescriptorException {
        JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
        jCas.setDocumentText(text);
        jCasBuilder.process(jCas);
        return jCas;
    }
}
