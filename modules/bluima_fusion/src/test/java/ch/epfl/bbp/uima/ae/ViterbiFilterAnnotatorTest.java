package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.ae.ViterbiFilterAnnotator.REMOVE_OTHER_ANNOTATIONS;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Random;

import org.apache.uima.UIMAException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;


import ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator;
//import ch.epfl.bbp.uima.ae.cleanup.DisambiguatorAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.AgeDictTerm;
import ch.epfl.bbp.uima.types.Keep;
import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.types.Punctuation;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

public class ViterbiFilterAnnotatorTest {

    static JCas getTestCas() throws UIMAException, IOException {
        // has Sentence & Tokens
        JCas jCas = UimaTests.getTokenizedTestCas("a b c d e f");
        Protein abc = new Protein(jCas, 0, 5);
        abc.addToIndexes();
        assertEquals("a b c", abc.getCoveredText());
        Protein ef = new Protein(jCas, 8, 11);
        ef.addToIndexes();
        assertEquals("e f", ef.getCoveredText());
        return jCas;
    }

    @Test
    public void testShortestPathGoesThroghAnnotations_abc_and_ef()
            throws Exception {
        JCas jCas = getTestCas();
        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));
        assertEquals("the 2 Protein remain", 2, select(jCas, Protein.class)
                .size());
        assertEquals("all Tokens kept", 6, select(jCas, Token.class).size());
    }

    /**
     * Added 'cd' annotation will get deleted, because it is covered by a
     * shortest path through annotations 'abc' and 'ef'
     */
    @Test
    public void testDeleted() throws Exception {

        JCas jCas = getTestCas();

        Protein cd = new Protein(jCas, 4, 7);
        cd.addToIndexes();
        assertEquals("c d", cd.getCoveredText());

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("only two Protein remain", 2, select(jCas, Protein.class)
                .size());
        assertEquals("all Tokens kept", 6, select(jCas, Token.class).size());
    }

    @Test
    /** Added 'p' and 'p2' annotations will get deleted, 
     * because they are covered by a longer annotation 'ef'
     */
    public void testCovered() throws Exception {

        JCas jCas = getTestCas();

        Punctuation p = new Punctuation(jCas, 9, 11);
        p.addToIndexes();
        Punctuation p2 = new Punctuation(jCas, 8, 10);
        p2.addToIndexes();

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("still the 2 Proteins remain", 2,
                select(jCas, Protein.class).size());
        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
    }

    @Test
    /** Added 2 more 'abc' and 'ef' AgeDictTerm annotation (same start-end, but having a higher prio),
     * so they will be kept and the other 'abc' and 'ef' annotations discarded
     */
    public void testHigherPrio() throws Exception {

        JCas jCas = getTestCas();

        AgeDictTerm abc = new AgeDictTerm(jCas, 0, 5);
        abc.addToIndexes();
        AgeDictTerm ef = new AgeDictTerm(jCas, 8, 11);
        ef.addToIndexes();

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Protein remain, lower prio", 0,
                select(jCas, Protein.class).size());
        assertEquals("AgeDictTerm remains", 2, select(jCas, AgeDictTerm.class)
                .size());
    }

    @Test
    /** add random annotations, and check that after viterbi, no overlapping remains     */
    public void testAddRandomAnnotations() throws Exception {

        for (int i = 0; i < 10; i++) {

            JCas jCas = UimaTests.getTokenizedTestCas(UimaTests.TEST_ABSTRACT);
            int length = jCas.getDocumentText().length();
            Random rnd = new Random();

            for (int j = 0; j < 50; j++) {
                int start = rnd.nextInt(length - 3);
                int end = start + rnd.nextInt(length - start);
                Protein pr = new Protein(jCas, start, end);
                pr.addToIndexes();
            }
            for (int j = 0; j < 50; j++) {
                int start = rnd.nextInt(length - 3);
                int end = start + rnd.nextInt(length - start);
                Punctuation pct = new Punctuation(jCas, start, end);
                pct.addToIndexes();
            }
            for (int j = 0; j < 50; j++) {
                int start = rnd.nextInt(length - 3);
                int end = start + rnd.nextInt(length - start);
                AgeDictTerm adt = new AgeDictTerm(jCas, start, end);
                adt.addToIndexes();
            }

            runPipeline(
                    jCas,
                    createEngine(ViterbiFilterAnnotator.class,
                            REMOVE_OTHER_ANNOTATIONS, true));

            for (Annotation a : jCas.getAnnotationIndex()) {
                if (!DisambiguatorAnnotator.SKIP_ANNOTATIONS.contains(a
                        .getClass().getName())
                        && !a.getClass().getName().equals(Keep.class.getName())) {

                    // annots.foreach { a =>
                    // //Prin.t(a)
                    // val covered = JCasUtil.selectCovered(jCas,
                    // classOf[Annotation],
                    // a.getBegin, a.getEnd)
                    // .filter { x =>
                    // (!DisambiguatorAnnotator.SKIP_ANNOTATIONS.contains(x.getClass.getName)&&
                    // !a.getClass.getName.equals(classOf[ShortestPath].getName)))
                    // }
                    // .filter { x => x != a }
                    // //Prin.t(covered)
                    // assert(covered.isEmpty,
                    // "must have no covered annotations for " +
                    // To.string(a)+" (covered with "+covered)
                    // //println("------")
                }
            }
        }
    }

    @Test
    public void testNombreDArcs() throws Exception {

        JCas jCas = UimaTests.getTestCas("abcd"); // not tokenized!!!
        (new Sentence(jCas, 0, 4)).addToIndexes();

        (new Punctuation(jCas, 0, 1)).addToIndexes(); // ab
        (new Punctuation(jCas, 1, 3)).addToIndexes(); // bd

        (new Protein(jCas, 0, 2)).addToIndexes(); // ac1
        (new Protein(jCas, 0, 2)).addToIndexes(); // ac2
        (new Protein(jCas, 0, 2)).addToIndexes(); // ac3
        (new Protein(jCas, 2, 3)).addToIndexes();// cd

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
        assertEquals("still the Proteins remain", 2,
                select(jCas, Protein.class).size());
    }

    @Test
    public void testNombreDArcs2() throws Exception {

        JCas jCas = UimaTests.getTestCas("abcd"); // not tokenized!!!
        (new Sentence(jCas, 0, 4)).addToIndexes();

        (new Punctuation(jCas, 0, 1)).addToIndexes(); // ab1
        (new Punctuation(jCas, 0, 1)).addToIndexes(); // ab2
        (new Punctuation(jCas, 0, 1)).addToIndexes(); // ab3
        (new Punctuation(jCas, 1, 2)).addToIndexes(); // bc

        (new Protein(jCas, 0, 2)).addToIndexes();// ac

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
        assertEquals("still the ac Protein remain", 1,
                select(jCas, Protein.class).size());
    }

    @Test
    public void testNombreDArcs3() throws Exception {

        JCas jCas = UimaTests.getTestCas("abcdef"); // not tokenized!!!
        (new Sentence(jCas, 0, 6)).addToIndexes();

        (new Protein(jCas, 0, 1)).addToIndexes();// ab1
        (new Protein(jCas, 0, 1)).addToIndexes(); // ab2
        (new Protein(jCas, 1, 4)).addToIndexes(); // be
        (new Protein(jCas, 4, 5)).addToIndexes(); // ef

        (new Punctuation(jCas, 0, 2)).addToIndexes(); // ac
        (new Punctuation(jCas, 2, 3)).addToIndexes(); // cd
        (new Punctuation(jCas, 3, 5)).addToIndexes(); // df

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
        assertEquals("still the Proteins remain", 3,
                select(jCas, Protein.class).size());
    }

    @Test
    public void testFindAShortestPath() throws Exception {

        JCas jCas = UimaTests.getTestCas("abcde"); // not tokenized!!!
        (new Sentence(jCas, 0, 5)).addToIndexes();

        (new Punctuation(jCas, 0, 1)).addToIndexes();// ab
        (new Punctuation(jCas, 1, 2)).addToIndexes(); // bc
        (new Punctuation(jCas, 2, 4)).addToIndexes(); // ce

        (new Protein(jCas, 0, 3)).addToIndexes(); // ad
        (new Protein(jCas, 3, 4)).addToIndexes(); // de

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
        assertEquals("still the Proteins remain", 2,
                select(jCas, Protein.class).size());
    }

    @Test
    public void testTokenRemainUnchanged() throws Exception {

        JCas jCas = UimaTests.getTestCas("abcde"); // not tokenized!!!

        (new Sentence(jCas, 0, 5)).addToIndexes();

        (new Token(jCas, 0, 1)).addToIndexes();
        (new Token(jCas, 1, 2)).addToIndexes();
        (new Token(jCas, 2, 3)).addToIndexes();
        (new Token(jCas, 3, 4)).addToIndexes();

        (new Punctuation(jCas, 0, 1)).addToIndexes(); // ab
        (new Punctuation(jCas, 1, 2)).addToIndexes(); // bc
        (new Punctuation(jCas, 2, 4)).addToIndexes(); // ce

        (new Protein(jCas, 0, 3)).addToIndexes(); // ad
        (new Protein(jCas, 3, 4)).addToIndexes(); // de

        runPipeline(
                jCas,
                createEngine(ViterbiFilterAnnotator.class,
                        REMOVE_OTHER_ANNOTATIONS, true));

        assertEquals("no more Punctuation", 0, select(jCas, Punctuation.class)
                .size());
        assertEquals("still the Proteins remain", 2,
                select(jCas, Protein.class).size());
        assertEquals("still the Tokens remain", 4, select(jCas, Token.class)
                .size());
    }
}