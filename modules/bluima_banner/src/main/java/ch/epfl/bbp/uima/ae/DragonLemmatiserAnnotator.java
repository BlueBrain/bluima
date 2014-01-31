package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.ResourceHelper.getFile;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.TOKEN;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.resource.ResourceConfigurationException.RESOURCE_DATA_NOT_VALID;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import de.julielab.jules.types.Lemma;
import de.julielab.jules.types.Token;
import dragon.nlp.tool.lemmatiser.EngLemmatiser;

/**
 * Lemmatiser (http://en.wikipedia.org/wiki/Lemmatisation) Annotator that uses
 * the Dragon Toolkit (http://dragon.ischool.drexel.edu/).<br>
 * The Dragon Toolkit is used in BANNER. It is described in the paper Zhou, X.,
 * Zhang, X., and Hu, X.,
 * "Dragon Toolkit: Incorporating Auto-learned Semantic Knowledge into Large-Scale Text Retrieval and Mining,"
 * In proceedings of the 19th IEEE International Conference on Tools with
 * Artificial Intelligence (ICTAI), October 29-31, 2007, Patras, Greece
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { TOKEN })
public class DragonLemmatiserAnnotator extends JCasAnnotator_ImplBase {

    public static final String PARAM_LEMMATISER_DATA = "lemmatiser_data";
    @ConfigurationParameter(name = PARAM_LEMMATISER_DATA,//
    defaultValue = "pear_resources/nlpdata/lemmatiser")
    private static String lemmatiserData;

    private EngLemmatiser lemmatiser;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            File lemmatiserDataDirectory = getFile(lemmatiserData);
            checkFileExists(lemmatiserDataDirectory);
            lemmatiser = new EngLemmatiser(
                    lemmatiserDataDirectory.getAbsolutePath(), false, true);
        } catch (FileNotFoundException e) {
            throw new ResourceInitializationException(RESOURCE_DATA_NOT_VALID,
                    new Object[] { lemmatiserData, "lemmatiserDataDirectory" },
                    e);
        }
    }

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {

        for (Token token : select(jcas, Token.class)) {

            Lemma lemma = new Lemma(jcas, token.getBegin(), token.getEnd());
            lemma.setValue(lemmatiser.lemmatize(token.getCoveredText()));
            lemma.addToIndexes();

            token.setLemma(lemma);
        }
    }
}
