package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.util.CasCreationUtils.createCas;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.typesystem.To;

public class UscTteCollectionReaderTest {
    Logger LOG = LoggerFactory.getLogger(UscTteCollectionReaderTest.class);

    @Test
    public void test() throws Exception {

        CollectionReader cr = createReader(UscTteCollectionReader.class,
                JULIE_TSD, PARAM_INPUT_DIRECTORY, "dummy");

        CAS cas = createCas(cr.getProcessingResourceMetaData());
        cr.getNext(cas);

        LOG.debug("text: {}", cas.getJCas().getDocumentText());

        List<BrainRegion> brs = asList(select(cas.getJCas(), BrainRegion.class));
        for (BrainRegion br : brs)
            LOG.debug("{}-{} " + To.string(br), br.getBegin(), br.getEnd());

        assertEquals(61, brs.size());

        BrainRegion br = brs.iterator().next();
        assertEquals("the cerebellum", br.getCoveredText());
    }
}
