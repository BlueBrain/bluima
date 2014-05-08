package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Sentence;

public class NewlineSentenceSplitterAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jcas = getTokenizedTestCas("aa");
        ArrayList<Sentence> sentences = newArrayList(select(jcas,
                Sentence.class));
        assertEquals(1, sentences.size());
        assertEquals("aa", sentences.get(0).getCoveredText());

        jcas = getTokenizedTestCas("aa aa\nbb bb");
        sentences = newArrayList(select(jcas, Sentence.class));
        assertEquals(2, sentences.size());
        assertEquals("aa aa", sentences.get(0).getCoveredText());
        assertEquals("bb bb", sentences.get(1).getCoveredText());

        jcas = getTokenizedTestCas("aa aa\nbb bb\ncc cc\n");
        sentences = newArrayList(select(jcas, Sentence.class));
        assertEquals(3, sentences.size());
        assertEquals("aa aa", sentences.get(0).getCoveredText());
        assertEquals("bb bb", sentences.get(1).getCoveredText());
        assertEquals("cc cc", sentences.get(2).getCoveredText());

        // leading \n is kept
        jcas = getTokenizedTestCas("\naa");
        sentences = newArrayList(select(jcas, Sentence.class));
        assertEquals(2, sentences.size());
        assertEquals("", sentences.get(0).getCoveredText());
        assertEquals("aa", sentences.get(1).getCoveredText());
    }

    public static JCas getTokenizedTestCas(String text) throws UIMAException,
            IOException {
        JCas testCas = getTestCas(text);
        runPipeline(testCas,
                createEngine(NewlineSentenceSplitterAnnotator.class));
        return testCas;
    }
}
