package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.iterator;
import static ch.epfl.bbp.uima.BlueUima.PARAM_MAX_NR_RESULTS;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.BrainRegion;

public class WhiteTextCollectionReaderTest extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(WhiteTextCollectionReaderTest.class);
    
    @Test
    public void test() throws Exception {

        CollectionReader cr = createReader(WhiteTextCollectionReader.class,
                JULIE_TSD);

        JCas jCas = iterator(cr).next();

        Collection<BrainRegion> br = select(jCas, BrainRegion.class);
        assertEquals(8, br.size());

        BrainRegion brainRegion = br.iterator().next();
        assertEquals("superior colliculus", brainRegion.getCoveredText());
    }

    @Test
    public void testMaxNr() throws Exception {

        CollectionReader cr = createReader(WhiteTextCollectionReader.class,
                JULIE_TSD, PARAM_MAX_NR_RESULTS, 10);
        assertEquals(10, asList(cr).size());

    }

    public static void main(String[] args) throws Exception {

        CollectionReader cr = createReader(WhiteTextCollectionReader.class,
                JULIE_TSD);

        SimplePipeline.runPipeline(cr, AnalysisEngineFactory
                .createEngine(WhiteTextCollectionReaderTest.class));
    }

    @Override
    public void process(JCas cas) throws AnalysisEngineProcessException {

        LOG.debug(getHeaderDocId(cas)+"\t"+cas.getDocumentText());
        
        for (BrainRegion br : JCasUtil.select(cas, BrainRegion.class)) {
            LOG.debug(br.getCoveredText());
        }

    }
}
