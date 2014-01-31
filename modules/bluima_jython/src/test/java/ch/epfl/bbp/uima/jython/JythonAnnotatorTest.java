package ch.epfl.bbp.uima.jython;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.TypeSystem;

public class JythonAnnotatorTest {

    @Test
    public void test() throws Exception {

        JCas jcas = UimaTests.getTestCas("Where is Dave going?");

        AnalysisEngine ae = AnalysisEngineFactory.createEngine(
                JythonAnnotator.class, TypeSystem.JULIE_TSD,
                JythonAnnotator.SCRIPT_PATH, "jython_script/dave_annotator.py");

        SimplePipeline.runPipeline(jcas, ae);

        List<Annotation> annotations = asList(select(jcas, Annotation.class));

        assertEquals(2, annotations.size());
        // first one is DocumentAnnotation
        assertEquals(9, annotations.get(1).getBegin());
        assertEquals(13, annotations.get(1).getEnd());
    }
}
