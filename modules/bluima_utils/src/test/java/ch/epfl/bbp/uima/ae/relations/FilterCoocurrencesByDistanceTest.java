package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT_ID_FIELD;
import static ch.epfl.bbp.uima.ae.relations.FilterCoocurrencesByDistance.PARAM_MAXIMUM_DISTANCE;
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
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import de.julielab.jules.types.Sentence;

/**
 * @see ExtractCoocurrencesTest
 * @author renaud.richardet@epfl.ch
 */
public class FilterCoocurrencesByDistanceTest {

    @Test
    public void test() throws Exception {

        JCas jCas = doTest(5);
        List<Sentence> sentences = asList(select(jCas, Sentence.class));
        assertEquals(1, sentences.size());
        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals("one cooc, distance ok", 1, cooc.size());
        assertEquals(9, cooc.get(0).getFirstEntity().getBegin());
        assertEquals(0, cooc.get(0).getSecondEntity().getBegin());

        jCas = doTest(1);
        sentences = asList(select(jCas, Sentence.class));
        assertEquals(1, sentences.size());
        cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals("no cooc, since too far away", 0, cooc.size());
    }

    public static JCas doTest(int maxDist) throws Exception {
        JCas jCas = getTokenizedTestCas("12 mM of AMPA was found.");

        ProteinDictTerm ampa1 = createAnnot(jCas, ProteinDictTerm.class, 9, 13,
                "AMPA");
        ampa1.setEntityId("1234");
        Measure mes1 = createAnnot(jCas, Measure.class, 0, 5, "12 mM");
        mes1.setUnit("mM");

        runPipeline(
                jCas,
                createEngine(ExtractCoocurrences.class,//
                        PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                        PARAM_FIRST_ANNOT, ProteinDictTerm.class.getName(),//
                        PARAM_SECOND_ANNOT, Measure.class.getName(),//
                        PARAM_SECOND_ANNOT_ID_FIELD, "unit"),
                createEngine(FilterCoocurrencesByDistance.class,
                        PARAM_MAXIMUM_DISTANCE, maxDist));

        return jCas;
    }
}
