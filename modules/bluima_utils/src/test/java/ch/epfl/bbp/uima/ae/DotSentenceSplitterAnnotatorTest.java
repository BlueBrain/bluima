package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.ArrayList;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Sentence;

public class DotSentenceSplitterAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jcas = getTokenizedTestCas("aa aa.bb bb");
        ArrayList<Sentence> sentences = newArrayList(select(jcas,
                Sentence.class));
        assertEquals(2, sentences.size());
        assertEquals("aa aa.", sentences.get(0).getCoveredText());
        assertEquals("bb bb", sentences.get(1).getCoveredText());

        for (String test : newArrayList("aa aa. bb bb. cc cc.",
                "aa aa. bb bb.cc cc. ")) {
            jcas = getTokenizedTestCas(test);
            sentences = newArrayList(select(jcas, Sentence.class));
            assertEquals(3, sentences.size());
            assertEquals("aa aa.", sentences.get(0).getCoveredText());
            assertEquals("bb bb.", sentences.get(1).getCoveredText());
            assertEquals("cc cc.", sentences.get(2).getCoveredText());
        }

        for (String test : newArrayList("aa.", "aa. ")) {
            jcas = getTokenizedTestCas(test);
            sentences = newArrayList(select(jcas, Sentence.class));
            assertEquals(1, sentences.size());
            assertEquals("aa.", sentences.get(0).getCoveredText());
        }

        jcas = getTokenizedTestCas("aa");
        sentences = newArrayList(select(jcas, Sentence.class));
        assertEquals(1, sentences.size());
        assertEquals("aa", sentences.get(0).getCoveredText());

    }
}
