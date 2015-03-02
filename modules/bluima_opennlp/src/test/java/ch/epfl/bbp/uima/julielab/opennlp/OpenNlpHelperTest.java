package ch.epfl.bbp.uima.julielab.opennlp;

import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;

import org.apache.uima.UIMAException;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.shaded.opennlp.tools.util.Pair;
import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Chunk;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class OpenNlpHelperTest {

    @Test
    public void testTokenizer() throws Exception {

        JCas jCas = UimaTests.getTestCas("the red cat is blue");
        SimplePipeline.runPipeline(jCas, OpenNlpHelper.getTokenizer());

        Collection<Token> tokens = JCasUtil.select(jCas, Token.class);
        assertEquals(5, tokens.size());

        Iterator<Token> it = tokens.iterator();
        it.hasNext();
        assertEquals("the", it.next().getCoveredText());
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

        JCas jCas = UimaTests
                .getTestCas("Terminologies which lack semantic connectivity hamper the effective search in biomedical fact databases and document retrieval systems. We here focus on the integration of two such isolated resources, the term lists from the protein fact database UNIPROT and the indexing vocabulary MESH from the bibliographic database MEDLINE.");
        SimplePipeline.runPipeline(jCas, OpenNlpHelper.getSentenceSplitter(),
                OpenNlpHelper.getTokenizer(), OpenNlpHelper.getPosTagger(),
                OpenNlpHelper.getChunker());

        Collection<Chunk> chunks = JCasUtil.select(jCas, Chunk.class);
        assertEquals(23, chunks.size());
        for (Chunk chunk : chunks) {
            Prin.t(chunk);
        }

        // Iterator<Chunk> it = chunks.iterator();
        // it.hasNext();
        // assertEquals(
        // "Terminologies which lack semantic connectivity hamper the effective search in biomedical fact databases and document retrieval systems.",
        // it.next().getCoveredText());
    }

    @Test
    public void testTokenizedTestCas() throws Exception {
        JCas jCas = OpenNlpHelper
                .getOpenNlpTokenizedTestCas("To each well, 100 l of low-potassium buffer (135 mM NaCl, 4.6 mM KCl, 1 mM CaCl2, 2 mM MgCl2, 0.2% bovine serum albumin, and 10 mM HEPES, pH 7.4, with NaOH) with or without test samples was added, and incubation continued for 30 min at 37°C.");
        for (Token t : JCasUtil.select(jCas, Token.class)) {
            Prin.t(t);
        }
    }

}
