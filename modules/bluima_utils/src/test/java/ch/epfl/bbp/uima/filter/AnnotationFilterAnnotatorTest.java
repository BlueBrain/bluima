package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASSES;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class AnnotationFilterAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jCas = getTokenizedTestCas("this is a sentence. and another one.");

        runPipeline(
                jCas,
                createEngine(Tokens2KeepAnnotator.class),
                createEngine(AnnotationFilterAnnotator.class,
                        PARAM_ANNOTATION_CLASSES,
                        new String[] { Token.class.getName() }),

                createEngine(LeaveOnlyKeepsEnclosedAnnotationsAnnotator.class));

        assertEquals("Sentences are still there", 2,
                asList(select(jCas, Sentence.class)).size());
        assertFalse("no more token", exists(jCas, Token.class));
    }

}
