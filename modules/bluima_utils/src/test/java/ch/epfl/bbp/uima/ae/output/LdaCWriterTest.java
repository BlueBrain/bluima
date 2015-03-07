package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_TEST_BASE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.ae.output.LdaCWriter.PARAM_IDS_OUTPUT_FILE;
import static ch.epfl.bbp.uima.ae.output.LdaCWriter.PARAM_VOCABULARY_INPUT_FILE;
import static ch.epfl.bbp.uima.ae.output.LdaCWriter.PARAM_VOCABULARY_OUTPUT_FILE;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.junit.Test;

import ch.epfl.bbp.uima.ae.EnsureDocHasOneSentence;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.cr.OneDocPerLineReader;
import ch.epfl.bbp.uima.filter.Tokens2KeepAnnotator;

public class LdaCWriterTest {

    @Test
    public void test() throws Exception {

        long time = System.currentTimeMillis();
        String outFile = "target/ldac-" + time;
        String vocabFile = "target/ldac-vocab" + time;
        String idsFile = "target/ldac-ids" + time;

        runPipeline(
                createReader(OneDocPerLineReader.class,
                        PARAM_INPUT_FILE, BLUE_UTILS_TEST_BASE
                                + "LdaCWriterTest.txt"),

                createEngine(EnsureDocHasOneSentence.class),
                createEngine(WhitespaceTokenizerAnnotator.class),
                createEngine(Tokens2KeepAnnotator.class),

                createEngine(LdaCWriter.class, PARAM_OUTPUT_FILE, outFile,
                        PARAM_VOCABULARY_OUTPUT_FILE, vocabFile,
                        PARAM_IDS_OUTPUT_FILE, idsFile));

        // check files
        checkFileExists(new File(outFile));
        checkFileExists(new File(vocabFile));
        assertEquals("4 0:1 1:1 2:1 3:1\n4 1:1 3:3 4:1 5:2\n3 0:1 6:1 7:1\n",
                asText(new File(outFile)));
        assertEquals("this\nis\na\ntest\nhere\nanother\nwow\nworks\n",
                asText(new File(vocabFile)));
    }

    @Test
    public void testWithExistingVocab() throws Exception {

        long time = System.currentTimeMillis();
        String outFile = "target/ldac-" + time;
        String idsFile = "target/ldac-ids" + time;

        runPipeline(
                createReader(OneDocPerLineReader.class,
                        PARAM_INPUT_FILE, BLUE_UTILS_TEST_BASE
                                + "LdaCWriterTest.txt"),

                createEngine(EnsureDocHasOneSentence.class),
                createEngine(WhitespaceTokenizerAnnotator.class),
                createEngine(Tokens2KeepAnnotator.class),

                createEngine(LdaCWriter.class, PARAM_OUTPUT_FILE, outFile,
                        PARAM_VOCABULARY_INPUT_FILE, BLUE_UTILS_TEST_BASE
                                + "LdaCWriterVocabTest.txt",
                        PARAM_IDS_OUTPUT_FILE, idsFile));

        // check files
        checkFileExists(new File(outFile));
        assertEquals("2 0:1 1:1\n1 1:3\n2 0:1 2:1\n", asText(new File(outFile)));
    }
}
