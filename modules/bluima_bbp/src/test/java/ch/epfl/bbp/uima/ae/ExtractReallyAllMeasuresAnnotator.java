package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.isEmptyText;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static java.util.regex.Pattern.compile;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import de.julielab.jules.types.Chunk;

public class ExtractReallyAllMeasuresAnnotator extends JCasAnnotator_ImplBase {

    // number, space, series of chars (that represent the unit), space
    private Pattern p = compile("-?(?:\\d+[\\.,]\\d+|\\d+\\.|\\.\\d+|\\d+)(?:(?:[eE][+-]?\\d+)|(?:x10\\(.{0,4}\\)))? (.*?) ");

    @Override
    public void process(JCas jcas) throws AnalysisEngineProcessException {
        if (!isEmptyText(jcas)) {
            Matcher m = p.matcher(jcas.getDocumentText());
            while (m.find()) {
                System.out.println(m.group());
                Chunk unitAnnotation = new Chunk(jcas, m.start(), m.end() - 1);
                unitAnnotation.addToIndexes();
            }
        }
    }

    @Test
    public void testExtractor() throws Exception {

        JCas jCas = getTestCas("we have 10.54 mm of snow here!");
        runPipeline(jCas,
                createEngine(ExtractReallyAllMeasuresAnnotator.class));

        Collection<Chunk> chunks = select(jCas, Chunk.class);
        assertEquals(1, chunks.size());
        assertEquals("10.54 mm", chunks.iterator().next().getCoveredText());
    }
}