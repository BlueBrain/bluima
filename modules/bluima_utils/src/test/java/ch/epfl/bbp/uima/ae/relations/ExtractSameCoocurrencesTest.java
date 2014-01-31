package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.ae.relations.ExtractCoocurrences.PARAM_KEEP_ONLY_NEAREST_NEIGHBORS;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.typesystem.Prin;
import de.julielab.jules.types.Sentence;

public class ExtractSameCoocurrencesTest {

    @Test
    public void testSameAnnot() throws Exception {

        JCas jCas = getTokenizedTestCas("AMPA and GABA and SNARE.");

        Measure ampa = createAnnot(jCas, Measure.class, 0, 4, "AMPA");
        Measure gaba = createAnnot(jCas, Measure.class, 9, 13, "GABA");
        Measure snare = createAnnot(jCas, Measure.class, 18, 23, "SNARE");

        runPipeline(jCas, createEngine(ExtractSameCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, Measure.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName()));

        List<Sentence> sentences = asList(select(jCas, Sentence.class));
        assertEquals(1, sentences.size());
        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        for (Cooccurrence c : cooc) {
            Prin.t(c);
        }
        assertEquals(3, cooc.size());
        assertEquals(ampa, cooc.get(0).getFirstEntity());
        assertEquals(snare, cooc.get(0).getSecondEntity());
        assertEquals(ampa, cooc.get(1).getFirstEntity());
        assertEquals(gaba, cooc.get(1).getSecondEntity());
        assertEquals(gaba, cooc.get(2).getFirstEntity());
        assertEquals(snare, cooc.get(2).getSecondEntity());
    }

    @Test
    public void testFilter() throws Exception {

        JCas jCas = getTokenizedTestCas("AMPA and and and GABA and SNARE.");

        createAnnot(jCas, Measure.class, 0, 4, "AMPA");
        Measure gaba = createAnnot(jCas, Measure.class, 17, 21, "GABA");
        Measure snare = createAnnot(jCas, Measure.class, 26, 31, "SNARE");

        runPipeline(jCas, createEngine(ExtractSameCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, Measure.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName(),//
                PARAM_KEEP_ONLY_NEAREST_NEIGHBORS, true));

        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals(1, cooc.size());
        assertEquals(gaba, cooc.get(0).getFirstEntity());
        assertEquals(snare, cooc.get(0).getSecondEntity());
    }

    @Test
    public void testFilterMore() throws Exception {

        JCas jCas = getTokenizedTestCas("AMPA and GABA and and SNARE and and D and E.");

        Measure ampa = createAnnot(jCas, Measure.class, 0, 4, "AMPA");
        Measure gaba = createAnnot(jCas, Measure.class, 9, 13, "GABA");
        createAnnot(jCas, Measure.class, 22, 27, "SNARE");
        Measure d = createAnnot(jCas, Measure.class, 36, 37, "D");
        Measure e = createAnnot(jCas, Measure.class, 42, 43, "E");

        runPipeline(jCas, createEngine(ExtractSameCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, Measure.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName(),//
                PARAM_KEEP_ONLY_NEAREST_NEIGHBORS, true));

        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals("should ",2, cooc.size());
        assertEquals(ampa, cooc.get(0).getFirstEntity());
        assertEquals(gaba, cooc.get(0).getSecondEntity());
        assertEquals(d, cooc.get(1).getFirstEntity());
        assertEquals(e, cooc.get(1).getSecondEntity());
    }
}
