package ch.epfl.bbp.uima.julielab.opennlp;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL_FILE;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.TestWithBluimaResource;
import ch.epfl.bbp.shaded.opennlp.tools.util.Pair;
import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.ae.TokenAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Chunk;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class OpenNlpHelperTest extends TestWithBluimaResource {

    private static AnalysisEngineDescription createTokenizerEngineDescription()
            throws ResourceInitializationException {
        String tokenModel = BLUIMA_RESOURCE_DIR
                + "/opennlp/token/TokenizerGenia.bin.gz";
        return createEngineDescription(TokenAnnotator.class, PARAM_MODEL_FILE,
                tokenModel);
    }

    @Test
    public void testTokenizer() throws Exception {
        testTokenizerImpl(createTokenizerEngineDescription());
    }

    // Test the now deprecated OpenNlpHelper
    @Test
    public void testTokenizerDeprecated() throws Exception {
        AnalysisEngineDescription tokenizer = OpenNlpHelper.getTokenizer();
        testTokenizerImpl(tokenizer);
    }

    private void testTokenizerImpl(AnalysisEngineDescription tokenizer)
            throws UIMAException, AnalysisEngineProcessException,
            ResourceInitializationException {
        // First test
        JCas jCas = UimaTests.getTestCas("the red cat is blue");
        SimplePipeline.runPipeline(jCas, tokenizer);

        Collection<Token> tokens = JCasUtil.select(jCas, Token.class);
        assertEquals(5, tokens.size());

        Iterator<Token> it = tokens.iterator();
        assertEquals("the", it.next().getCoveredText());

        // Second test
        String text = "CD44, at any stage, is a XYZ";
        String offsets = "0-4;4-5;6-8;9-12;13-18;18-19;20-22;23-24;25-28";
        jCas = UimaTests.getTestCas(text);
        SimplePipeline.runPipeline(jCas, tokenizer);

        tokens = JCasUtil.select(jCas, Token.class);

        Iterator<Token> tokenIt = tokens.iterator();
        String predictedOffsets = "";

        while (tokenIt.hasNext()) {
            Token t = tokenIt.next();
            predictedOffsets += (predictedOffsets.length() > 0) ? ";" : "";
            predictedOffsets += t.getBegin() + "-" + t.getEnd();
        }

        assertEquals(offsets, predictedOffsets);
    }

    @Test
    public void testSentenceSplitter() throws UIMAException {
        String text = "First sentence. Second sentence!";
        Pair offsets[] = { new Pair(0, 15), new Pair(16, 32) };

        // Run the splitter annotator on `text`
        JCas jCas = UimaTests.getTestCas(text);
        runPipeline(jCas, OpenNlpHelper.getSentenceSplitter());

        Collection<Sentence> collection = JCasUtil.select(jCas, Sentence.class);
        Sentence sentences[] = new Sentence[collection.size()];
        collection.toArray(sentences);

        // Make sure the number of sentences and their offsets are corrects
        assertEquals(sentences.length, offsets.length);
        for (int i = 0; i < offsets.length; ++i) {
            Sentence sentence = sentences[i];
            Pair offset = offsets[i];

            assertEquals(sentence.getBegin(), offset.a);
            assertEquals(sentence.getEnd(), offset.b);
        }

        // Test it on more complex sentences
        String sentence1 = "Terminologies which lack semantic connectivity hamper the effective search in biomedical fact databases and document retrieval systems.";
        String sentence2 = "We here focus on the integration of two such isolated resources, the term lists from the protein fact database UNIPROT and the indexing vocabulary MESH from the bibliographic database MEDLINE.";
        text = String.format("%s %s", sentence1, sentence2);
        jCas = UimaTests.getTestCas(text);
        runPipeline(jCas, OpenNlpHelper.getSentenceSplitter());

        collection = JCasUtil.select(jCas, Sentence.class);
        assertEquals(2, collection.size());

        Iterator<Sentence> it = collection.iterator();
        assertEquals(it.next().getCoveredText(), sentence1);
        assertEquals(it.next().getCoveredText(), sentence2);
    }

    @Test
    public void testChunker() throws Exception {
        String text = "A study on the Prethcamide hydroxylation system in rat hepatic microsomes .";
        String chunks = "ChunkNP,ChunkPP,ChunkNP,ChunkPP,ChunkNP,";

        JCas jCas = UimaTests.getTestCas(text);
        // Run chunker pipeline
        SimplePipeline.runPipeline(jCas, OpenNlpHelper.getSentenceSplitter(),
                OpenNlpHelper.getTokenizer(), OpenNlpHelper.getPosTagger(),
                OpenNlpHelper.getChunker());

        Collection<Chunk> collection = JCasUtil.select(jCas, Chunk.class);
        assertEquals(5, collection.size());

        String predictedChunks = "";
        for (Chunk chunk : collection) {
            predictedChunks += chunk.getType().getShortName() + ",";
        }

        assertEquals(chunks, predictedChunks);
    }

    // FIXME this actual tests nothing -- only print
    @Ignore
    @Test
    public void testTokenizedTestCas() throws Exception {
        JCas jCas = OpenNlpHelper
                .getOpenNlpTokenizedTestCas("To each well, 100 l of low-potassium buffer (135 mM NaCl, 4.6 mM KCl, 1 mM CaCl2, 2 mM MgCl2, 0.2% bovine serum albumin, and 10 mM HEPES, pH 7.4, with NaOH) with or without test samples was added, and incubation continued for 30 min at 37°C.");
        for (Token t : JCasUtil.select(jCas, Token.class)) {
            Prin.t(t);
        }
    }

    @Test
    public void testPOS() throws UIMAException {
        String text = "A study on the Prethcamide";
        String postags = "DT;NN;IN;DT;NN;";

        JCas jCas = UimaTests.getTestCas(text);
        // POS annotator assumes sentences and tokens have been annotated
        runPipeline(jCas, OpenNlpHelper.getSentenceSplitter(),
                OpenNlpHelper.getTokenizer(), OpenNlpHelper.getPosTagger());

        Collection<Token> tokens = JCasUtil.select(jCas, Token.class);
        assertEquals(5, tokens.size());

        Iterator<Token> tokenIt = tokens.iterator();
        String predictedPOSTags = "";

        while (tokenIt.hasNext()) {
            Token t = tokenIt.next();
            POSTag tag = (POSTag) t.getPosTag().get(0);
            predictedPOSTags = predictedPOSTags + tag.getValue() + ";";
        }

        assertEquals(postags, predictedPOSTags);
    }
}
