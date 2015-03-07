package ch.epfl.bbp.uima.jython;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.jython.JythonAnnotator.SCRIPT_PATH;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

public class JythonAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jcas = getTestCas("Where is Dave going?");

        AnalysisEngine ae = createEngine(JythonAnnotator.class, SCRIPT_PATH,
                "jython_script/dave_annotator.py");

        runPipeline(jcas, ae);

        List<Annotation> annotations = asList(select(jcas, Annotation.class));

        assertEquals(2, annotations.size());
        // first one is DocumentAnnotation
        assertEquals(9, annotations.get(1).getBegin());
        assertEquals(13, annotations.get(1).getEnd());
    }
}
