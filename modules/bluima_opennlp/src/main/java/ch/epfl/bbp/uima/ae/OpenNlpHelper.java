package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL_FILE;
import static com.google.common.base.Preconditions.checkArgument;
import static de.julielab.jules.ae.opennlp.PosTagAnnotator.PARAM_TAG_DICT;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.nlp.ModelProxy;
import ch.epfl.bbp.nlp.ModelProxyException;
import ch.epfl.bbp.nlp.ModelStream;
import ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.testutils.UimaTests;

public class OpenNlpHelper {
    private static Logger LOG = LoggerFactory.getLogger(OpenNlpHelper.class);

    public final static String OPENNLP_ROOT = BlueUima.BLUE_UIMA_ROOT
            + "modules/bluima_opennlp/";

    public static AnalysisEngineDescription getSentenceSplitter()
            throws ResourceInitializationException {
        return createEngineDescription(SentenceAnnotator.class,
                BlueUima.PARAM_MODEL,
                "ch.epfl.bbp.nlp.res.sentence.PennBioResource");
    }

    public static AnalysisEngineDescription getTokenizer()
            throws ResourceInitializationException {
        String modelFile = OPENNLP_ROOT
                + "src/main/resources/pear_resources/models/token/TokenizerGenia.bin.gz";
        checkArgument(new File(modelFile).exists(), "no model file at "
                + modelFile);
        return createEngineDescription(TokenAnnotator.class,
                PARAM_MODEL_FILE, modelFile);
    }

    public static AnalysisEngineDescription getPosTagger()
            throws ResourceInitializationException {
        String tagDict = OPENNLP_ROOT
                + "src/main/resources/pear_resources/models/postag/tagdict-genia", //
        modelFile = OPENNLP_ROOT
                + "src/main/resources/pear_resources/models/postag/Tagger_Genia.bin.gz";
        checkArgument(new File(tagDict).exists(), "no tag dict file at "
                + tagDict);
        return createEngineDescription(PosTagAnnotator.class,
                PARAM_TAG_DICT, tagDict, PARAM_MODEL_FILE, modelFile);
    }

    /**
     * Conv. method to use the SentenceDetector directly; create it with the
     * method below
     */
    public static List<String> splitSentence2(String text, SentenceDetector sd) {
        List<String> sentences = new ArrayList<String>();
        try {
            int sentenceOffsets[] = sd.sentPosDetect(text);

            int begin = 0;
            int end = 0;
            for (int i = 0; i < sentenceOffsets.length; i++) {
                end = begin
                        + (text.substring(begin, sentenceOffsets[i]).trim())
                                .length();
                sentences.add(text.substring(begin, end));
                begin = sentenceOffsets[i];
            }
        } catch (Exception e) {
            LOG.warn("failed to extract sentences from text '" + text + "'", e);
        }
        return sentences;
    }

    public static ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector getSentenceDetector()
            throws IOException, ModelProxyException {
        // TODO shouldn't it use some config settings instead of
        // hard coded string?
        ModelStream model = ModelProxy
                .getStream("ch.epfl.bbp.nlp.res.sentence.PennBioResource");
        return new ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector(
                model);
    }

    public static AnalysisEngineDescription getChunker()
            throws ResourceInitializationException {
        String modelFile = OPENNLP_ROOT
                + "src/main/resources/pear_resources/models/chunker/Chunker_Genia.bin.gz";
        return createEngineDescription(ChunkAnnotator.class,
                PARAM_MODEL_FILE, modelFile);
    }

    /**
     * @param text
     * @return a JCas that has been (naively) sentence splitted and tokenized
     */
    public static JCas getOpenNlpTokenizedTestCas(String text)
            throws UIMAException, IOException {
        JCas testCas = UimaTests.getTestCas(text);
        runPipeline(testCas, getSentenceSplitter(), getTokenizer());
        return testCas;
    }
}
