package ch.epfl.bbp.nlp.tables;

import static ch.epfl.bbp.nlp.tables.TableAnnotator.TARGET_I;
import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MODE;
import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.iteratePipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.nlp.tables.TableAnnotator.Mode;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;
import ch.epfl.bbp.uima.types.DocumentLine;

public class GoldAnnotationTest {

    public static String ANNOTATED_PDFS = TEST_RESOURCES_PATH + "annots/";

    @Test
    public void testParse() throws Exception {

        File annotationFile = new File(ANNOTATED_PDFS + "17072837.annot");
        List<GoldAnnotation> annotations = GoldAnnotation.parse(annotationFile);
        assertEquals(21, annotations.size());
    }

    @Test
    public void testMatch() throws Exception {

        JCas jCas = iteratePipeline(
                createReaderDescription(PdfCollectionReader.class, JULIE_TSD,
                        PARAM_INPUT_DIRECTORY, ANNOTATED_PDFS), //
                createEngineDescription(TableAnnotator.class,//
                        PARAM_CORPUS_ROOT, TableCorpusTest.CORPUS_PATH,//
                        PARAM_MODE, Mode.eval.name()))//
                .iterator().next();

        int matches = 0;
        for (DocumentLine line : select(jCas, DocumentLine.class)) {
            if (line.getLabel().equals(TARGET_I))
                matches++;
        }

        assertEquals("all gold matched on pdf", 21, matches);
    }

    @Test
    public void testMatch_10535635() throws Exception {

        JCas jCas = iteratePipeline(
                createReaderDescription(PdfCollectionReader.class, JULIE_TSD,
                        PARAM_INPUT_DIRECTORY, TEST_RESOURCES_PATH
                                + "annots_10535635/"), //
                createEngineDescription(TableAnnotator.class,//
                        PARAM_CORPUS_ROOT, TableCorpusTest.CORPUS_PATH,//
                        PARAM_MODE, Mode.eval.name()))//
                .iterator().next();

        int matches = 0;
        for (DocumentLine line : select(jCas, DocumentLine.class)) {
            if (line.getLabel().equals(TARGET_I))
                matches++;
        }

        assertEquals("all gold matched on pdf", 109, matches);
    }
}
