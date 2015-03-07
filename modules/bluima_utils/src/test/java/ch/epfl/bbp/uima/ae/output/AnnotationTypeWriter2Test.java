package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.io.LineReader.asText;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FEATURE_NAME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.EnsureDocHasOneSentence;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.cr.TextArrayReader;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import de.julielab.jules.types.Token;

public class AnnotationTypeWriter2Test {

    @Test
    public void test() throws Exception {

        String[] input = { "hello brave world" };
        String outFile = "target/AnnotationTypeWriter2Test-test"
                + System.currentTimeMillis();

        runPipeline(
                createReader(TextArrayReader.class, PARAM_INPUT,
                        input),

                createEngine(EnsureDocHasOneSentence.class),
                createEngine(WhitespaceTokenizerAnnotator.class),

                createEngine(AnnotationTypeWriter2.class, PARAM_OUTPUT_FILE,
                        outFile, PARAM_ANNOTATION_CLASS, Token.class.getName()));

        // check files
        checkFileExists(new File(outFile));
        assertEquals("1\t0\t5\thello\n1\t6\t11\tbrave\n1\t12\t17\tworld\n",
                asText(new File(outFile)));
    }

    @Test
    public void testFeatureName() throws Exception {

        String outFile = "target/AnnotationTypeWriter2Test-test2"
                + System.currentTimeMillis();

        JCas jCas = getTokenizedTestCas("hello AMPA");
        ProteinDictTerm ampa = createAnnot(jCas, ProteinDictTerm.class, 6, 10,
                "AMPA");
        ampa.setEntityId("1234");

        runPipeline(
                jCas,
                createEngine(AnnotationTypeWriter2.class,
                        PARAM_OUTPUT_FILE,
                        outFile, //
                        PARAM_ANNOTATION_CLASS,
                        ProteinDictTerm.class.getName(),//
                        PARAM_FEATURE_NAME, "entityId"));

        // check files
        checkFileExists(new File(outFile));
        assertEquals("null\t6\t10\t1234\n", asText(new File(outFile)));
    }

    @Test
    public void testDocumentText() throws Exception {

        String outFile = "target/AnnotationTypeWriter2Test-test3"
                + currentTimeMillis();

        JCas jCas = getTestCas("hello AMPA");

        runPipeline(
                jCas,
                createEngine(AnnotationTypeWriter2.class,
                        PARAM_OUTPUT_FILE,
                        outFile, //
                        PARAM_ANNOTATION_CLASS,
                        "org.apache.uima.jcas.tcas.Annotation"));

        // check files
        checkFileExists(new File(outFile));
        assertEquals("null\t0\t10\thello AMPA\n", asText(new File(outFile)));
    }
}
