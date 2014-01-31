package ch.epfl.bbp.nlp.ie.proteinconc;

import ch.epfl.bbp.uima.filter.SectionAnnotator;
import ch.epfl.bbp.uima.filter.SectionRegexAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.typesystem.ContentSection;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SectionBasedCoocConfidenceAnnotatorTest {


    @Test
    public void methodBeforeResultTest() throws Exception {
        String testStr = "METHODS\ncooc1\nRESULTS\ncooc2\nDISCUSSION\n" +
                "cooc3\nREFERENCES\nblabla";
        JCas cas = UimaTests.getTestCas(testStr);

        int cooc1Begin = testStr.indexOf("cooc1");
        int cooc2Begin = testStr.indexOf("cooc2");
        int cooc3Begin = testStr.indexOf("cooc3");
        int cooc1End = cooc1Begin + 5;
        int cooc2End = cooc2Begin + 5;
        int cooc3End = cooc3Begin + 5;

        new DocumentBlock(cas, 0, cooc1End).addToIndexes();
        new DocumentBlock(cas, testStr.indexOf("RESULTS"), cooc2End).addToIndexes();

        int indexOfRef = testStr.indexOf("REFERENCES");
        new DocumentBlock(cas, testStr.indexOf("DISCUSSION"), indexOfRef - 1).addToIndexes();
        new DocumentBlock(cas, indexOfRef, testStr.length() - 1).addToIndexes();

        new Cooccurrence(cas, cooc1Begin, cooc1End).addToIndexes();
        new Cooccurrence(cas, cooc2Begin, cooc2End).addToIndexes();
        new Cooccurrence(cas, cooc3Begin, cooc3End).addToIndexes();

        //TEST THE SECTION ANNOTATOR
        runPipeline(cas,
                createEngine(SectionAnnotator.class),
                createEngine(SectionRegexAnnotator.class),
                createEngine(SectionBasedCoocConfidenceAnnotator.class));

        boolean mAndMFound = false;
        int resFoundCount = 0;
        for (DocumentBlock block : select(cas, DocumentBlock.class)) {
            String label = block.getLabel();
            if (ContentSection.SECTION_MAT_MET.equals(label)) {
                mAndMFound = true;
            } else if (ContentSection.SECTION_RESULTS.equals(label)) {
                resFoundCount++;
            }
        }

        assertTrue("Materials and methods not found", mAndMFound);
        assertEquals("Resutls not found", resFoundCount, 2);


        int count = 0;
        for (Cooccurrence cooc : select(cas, Cooccurrence.class)) {
            if ("cooc1".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc1", (int) cooc.getConfidence(), -1);
                System.out.println("111111");
                count++;
            } else if ("cooc2".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc2", 1, (int) cooc.getConfidence());
                System.out.println("22222");
                count++;
            } else if ("cooc3".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc3", 1, (int) cooc.getConfidence());
                System.out.println("33333");
                count++;
            }
        }

        assertEquals("the confidence of some co-occurrences does not have been annotated.", 3, count);
    }

    @Test
    public void methodsBetweenResultsTest() throws Exception {
        String testStr = "RESULTS\ncooc1\nMETHODS\ncooc2\nDISCUSSION\n" +
                "cooc3\nREFERENCES\nblabla";
        JCas cas = UimaTests.getTestCas(testStr);

        int cooc1Begin = testStr.indexOf("cooc1");
        int cooc2Begin = testStr.indexOf("cooc2");
        int cooc3Begin = testStr.indexOf("cooc3");
        int cooc1End = cooc1Begin + 5;
        int cooc2End = cooc2Begin + 5;
        int cooc3End = cooc3Begin + 5;

        new DocumentBlock(cas, 0, cooc1End).addToIndexes();
        new DocumentBlock(cas, testStr.indexOf("METHODS"), cooc2End).addToIndexes();

        int indexOfRef = testStr.indexOf("REFERENCES");
        new DocumentBlock(cas, testStr.indexOf("DISCUSSION"), indexOfRef - 1).addToIndexes();
        new DocumentBlock(cas, indexOfRef, testStr.length() - 1).addToIndexes();

        new Cooccurrence(cas, cooc1Begin, cooc1End).addToIndexes();
        new Cooccurrence(cas, cooc2Begin, cooc2End).addToIndexes();
        new Cooccurrence(cas, cooc3Begin, cooc3End).addToIndexes();

        //TEST THE SECTION ANNOTATOR
        runPipeline(cas,
                createEngine(SectionAnnotator.class),
                createEngine(SectionRegexAnnotator.class),
                createEngine(SectionBasedCoocConfidenceAnnotator.class));

        boolean mAndMFound = false;
        int resFoundCount = 0;
        for (DocumentBlock block : select(cas, DocumentBlock.class)) {
            String label = block.getLabel();
            if (ContentSection.SECTION_MAT_MET.equals(label)) {
                mAndMFound = true;
            } else if (ContentSection.SECTION_RESULTS.equals(label)) {
                resFoundCount++;
            }
        }

        assertTrue("Materials and methods not found", mAndMFound);
        assertEquals("Resutls not found", resFoundCount, 2);


        int count = 0;
        for (Cooccurrence cooc : select(cas, Cooccurrence.class)) {
            if ("cooc1".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc1", (int) cooc.getConfidence(), 1);
                System.out.println("111111");
                count++;
            } else if ("cooc2".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc2", (int) cooc.getConfidence(), -1);
                System.out.println("22222");
                count++;
            } else if ("cooc3".equals(cooc.getCoveredText())) {
                assertEquals("Bad confidence for cooc3", (int) cooc.getConfidence(), 1);
                System.out.println("33333");
                count++;
            }
        }

        assertEquals("the confidence of some co-occurrences does not have been annotated.", 3, count);
    }

}