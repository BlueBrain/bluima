package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.BlueCasUtil.setDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.filter.SentenceFilterAnnotator.PARAM_REGEX;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.DotSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;

public class SentenceFilterAnnotatorTest {

    @Test
    public void test() throws Exception {

        String testDir = "target/SentenceFilterAnnotatorTest"
                + currentTimeMillis();
        new File(testDir).mkdir();

        JCas jCas = getTestCas("I like neurons. I like cells, too. But not cakes.");
        setDocId(jCas, 17);

        PipelineBuilder builder = new JcasPipelineBuilder(jCas);

        builder.add(DotSentenceSplitterAnnotator.class);
        builder.add(
                SentenceFilterAnnotator.class, //
                PARAM_REGEX, "\\b((inter)?neurone?s?|cells?)\\b",
                PARAM_OUTPUT_DIR, testDir);
        builder.process();

        File testFile = new File(testDir + "/17.tsv");
        checkFileExists(testFile);
        String txt = asText(testFile);
        assertEquals("17\tI like neurons. I like cells, too.\n",txt);
    }
}
