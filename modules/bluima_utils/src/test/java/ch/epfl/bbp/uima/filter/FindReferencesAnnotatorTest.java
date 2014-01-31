package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import de.julielab.jules.types.Section;

public class FindReferencesAnnotatorTest {

    @Test
    public void test() throws Exception {

        AnalysisEngineDescription refFinder = createEngineDescription(ReferencesFinderAnnotator.class);

        JCas jCas = getTokenizedTestCas("bla bla\nreferences\nbli bli");
        runPipeline(jCas, refFinder);
        assertEquals(1, select(jCas, Section.class).size());

        jCas = getTokenizedTestCas("bla bl\nREFERENCES\n bbli bli");
        runPipeline(jCas, refFinder);
        assertEquals(1, select(jCas, Section.class).size());
    }
}