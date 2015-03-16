package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL;
import static ch.epfl.bbp.uima.ae.PosTagAnnotator.PARAM_TAG_DICT;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

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

@Deprecated(/* Use annotators directly instead */)
public class OpenNlpHelper {
    private static Logger LOG = LoggerFactory.getLogger(OpenNlpHelper.class);

    public final static String OPENNLP_ROOT = BlueUima.BLUE_UIMA_ROOT
            + "modules/bluima_opennlp/";

    @Deprecated(/* Use SentenceAnnotator directly with appropriate model */)
    public static AnalysisEngineDescription getSentenceSplitter()
            throws ResourceInitializationException {
        return createEngineDescription(SentenceAnnotator.class, PARAM_MODEL,
                "ch.epfl.bbp.nlp.res.sentence.PennBioResource");
    }

    @Deprecated(/* Use TokenAnnotator directly with appropriate model */)
    public static AnalysisEngineDescription getTokenizer()
            throws ResourceInitializationException {
        return createEngineDescription(TokenAnnotator.class, PARAM_MODEL,
                "ch.epfl.bbp.nlp.res.token.GeniaResource");
    }

    @Deprecated(/* Use PosTagAnnotator directly with appropriate model */)
    public static AnalysisEngineDescription getPosTagger()
            throws ResourceInitializationException {
        return createEngineDescription(PosTagAnnotator.class, PARAM_TAG_DICT,
                "ch.epfl.bbp.nlp.res.tag.dict.GeniaResource", PARAM_MODEL,
                "ch.epfl.bbp.nlp.res.tag.GeniaResource");
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

    @Deprecated(/* Use SentenceDetector directly with appropriate model */)
    public static ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector getSentenceDetector()
            throws IOException, ModelProxyException {
        ModelStream model = ModelProxy
                .getStream("ch.epfl.bbp.nlp.res.sentence.PennBioResource");
        return new ch.epfl.bbp.shaded.opennlp.tools.lang.english.SentenceDetector(
                model);
    }

    @Deprecated(/* Use ChunkAnnotator directly with appropriate model */)
    public static AnalysisEngineDescription getChunker()
            throws ResourceInitializationException {
        return createEngineDescription(ChunkAnnotator.class, PARAM_MODEL,
                "ch.epfl.bpp.nlp.res.chunk.GeniaResource");
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
