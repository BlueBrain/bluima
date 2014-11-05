package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ENCLOSING_SCOPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT_ID_FIELD;
import static ch.epfl.bbp.uima.ae.relations.ExtractCoocurrences.PARAM_KEEP_ONLY_NEAREST_NEIGHBORS;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.ProteinDictTerm;
import de.julielab.jules.types.Sentence;

/**
 * @author renaud.richardet@epfl.ch
 */
// TODO Phil test that one can pass a comma separated list
public class ExtractCoocurrencesTest {

    @Test
    public void test() throws Exception {

        JCas jCas = getTokenizedTestCas("12 mM of AMPA was found. AMPA only has been found. "
                + "Only one measure like 10 nM here. Nothing has been found. Oh here again "
                + "a coocurence of NMDA with 17 ml");

        ProteinDictTerm ampa1 = createAnnot(jCas, ProteinDictTerm.class, 9, 13,
                "AMPA");
        ampa1.setEntityId("1234");

        Measure mes1 = createAnnot(jCas, Measure.class, 0, 5, "12 mM");
        mes1.setValue(12);
        mes1.setUnit("mM");

        createAnnot(jCas, ProteinDictTerm.class, 25, 29, "AMPA");
        // id (entityId) not set!

        Measure mes2 = createAnnot(jCas, Measure.class, 73, 78);
        mes2.setValue(10);
        mes2.setUnit("nM");
        assertEquals("10 nM", mes2.getCoveredText());

        ProteinDictTerm nmda = createAnnot(jCas, ProteinDictTerm.class, 139,
                143, "NMDA");
        // id (entityId) not set!

        assertEquals("NMDA", nmda.getCoveredText());
        Measure mes3 = createAnnot(jCas, Measure.class, 149, 154);
        mes3.setValue(17);
        mes3.setUnit("ml");
        assertEquals("17 ml", mes3.getCoveredText());

        runPipeline(jCas, createEngine(ExtractCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, ProteinDictTerm.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName(),//
                PARAM_SECOND_ANNOT_ID_FIELD, "value, unit"));

        List<Sentence> sentences = asList(select(jCas, Sentence.class));
        assertEquals(5, sentences.size());
        List<Cooccurrence> cooc = asList(select(jCas, Cooccurrence.class));
        assertEquals(2, cooc.size());

        assertEquals(ampa1, cooc.get(0).getFirstEntity());
        assertEquals("should extract coveredText", "AMPA", cooc.get(0)
                .getFirstIds().get(0));
        assertEquals(mes1, cooc.get(0).getSecondEntity());
        assertEquals("" + 12f, cooc.get(0).getSecondIds().get(0));
        assertEquals("mM", cooc.get(0).getSecondIds().get(1));

        assertEquals(nmda, cooc.get(1).getFirstEntity());
        assertEquals("NMDA", cooc.get(1).getFirstIds(0));
        assertEquals(mes3, cooc.get(1).getSecondEntity());
        assertEquals("" + 17f, cooc.get(1).getSecondIds(0));
        assertEquals("ml", cooc.get(1).getSecondIds(1));
    }

    private static final String CHOICE_ERROR = "bad choice of co-occurrences";

    @Test
    public void testFilter() throws Exception {

        JCas jCas = getTokenizedTestCas("a1 b1 bla bla a2 b2");

        createAnnot(jCas, ProteinDictTerm.class, 0, 2, "a1");
        createAnnot(jCas, Measure.class, 3, 5, "b1");
        createAnnot(jCas, Measure.class, 17, 19, "b2");

        runPipeline(jCas, createEngine(ExtractCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, ProteinDictTerm.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName(),//
                PARAM_KEEP_ONLY_NEAREST_NEIGHBORS, true));

        List<Cooccurrence> coocs = asList(select(jCas, Cooccurrence.class));
        // System.out.println(coocs);
        assertTrue("too many co-occurrences have been kept", coocs.size() == 1);
        assertEquals(CHOICE_ERROR, "a1 b1", coocs.get(0).getCoveredText());
    }

    @Test
    public void testFilterMoreComplex() throws Exception {

        JCas jCas = getTokenizedTestCas("a1 b1 bli bla a2 b2");

        createAnnot(jCas, Measure.class, 0, 2, "a1");
        createAnnot(jCas, Measure.class, 3, 5, "b1");
        createAnnot(jCas, Measure.class, 6, 9, "bli");
        createAnnot(jCas, Measure.class, 14, 16, "a2");
        createAnnot(jCas, ProteinDictTerm.class, 10, 13, "bla");
        createAnnot(jCas, ProteinDictTerm.class, 17, 19, "b2");

        runPipeline(jCas, createEngine(ExtractCoocurrences.class, //
                PARAM_ENCLOSING_SCOPE, Sentence.class.getName(),//
                PARAM_FIRST_ANNOT, ProteinDictTerm.class.getName(),//
                PARAM_SECOND_ANNOT, Measure.class.getName(),//
                PARAM_KEEP_ONLY_NEAREST_NEIGHBORS, true));

        List<Cooccurrence> coocs = asList(select(jCas, Cooccurrence.class));
        // System.out.println(coocs);
        assertTrue("too many co-occurrences have been kept", coocs.size() == 2);
        assertEquals(CHOICE_ERROR, "bli bla", coocs.get(0).getCoveredText());
        assertEquals(CHOICE_ERROR, "a2 b2", coocs.get(1).getCoveredText());
    }
}
