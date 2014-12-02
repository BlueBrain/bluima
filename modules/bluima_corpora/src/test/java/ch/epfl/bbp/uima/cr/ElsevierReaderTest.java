package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.StringUtils.snippetize;
import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static ch.epfl.bbp.uima.CorporaHelper.CORPORA_HOME;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;

public class ElsevierReaderTest {
    Logger LOG = LoggerFactory.getLogger(ElsevierReaderTest.class);

    @Test
    public void test() throws Exception {

        List<JCas> cases = asList(createReader(ElsevierReader.class, JULIE_TSD,
                PARAM_INPUT_DIRECTORY, CORPORA_HOME + TEST_RESOURCES_PATH
                        + "elsevier"));

        assertEquals(1, cases.size());
        for (JCas jCas : cases) {
            LOG.debug(snippetize(jCas.getDocumentText(), 200));
        }
    }

    @Test
    @Ignore
    public void test2() throws Exception {

        CollectionReader cr = createReader(
                ElsevierReader.class,
                JULIE_TSD,
                PARAM_INPUT_DIRECTORY,
                "/Users/richarde/Desktop/BBP_experiments/23_extract_brainregions/crawl/neuroscience");

        // runPipeline(cr, createEngine(SysoutDumper.class));
        runPipeline(
                cr,
                createEngine(StatsAnnotatorPlus.class,
                        StatsAnnotatorPlus.PARAM_PRINT_EVERY, 200));
    }
}
