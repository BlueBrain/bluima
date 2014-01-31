package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MAX_NR_RESULTS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_QUERY;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import java.util.Iterator;
import java.util.List;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.BlueCasUtil;
import de.julielab.jules.types.Header;

public class PubmedWebServiceCollectionReaderTest {
    Logger LOG = getLogger(PubmedWebServiceCollectionReaderTest.class);

    @Test
    public void testCat() throws Exception {

        CollectionReader cr = createReader(
                PubmedWebServiceCollectionReader.class, JULIE_TSD,//
                PARAM_QUERY, "mouse", PARAM_MAX_NR_RESULTS, 20);

        List<JCas> results = BlueCasUtil.asList(cr);
        assertEquals("20 results", 20, results.size());

        for (JCas jCas : results) {

            Header header = selectSingle(jCas, Header.class);
            LOG.debug("pmid: {}, text: {}", header.getDocId(),
                    jCas.getDocumentText());
            assertNotNull(header.getDocId());
        }

        cr.close();
    }

    @Test
    public void testNervousSystem() throws Exception {

        CollectionReader cr = PubmedWebServiceCollectionReader.getCR(
                "nervous system[mesh]", 20);

        int cnt = 0;
        Iterator<JCas> it = BlueCasUtil.iterator(cr);
        while (it.hasNext()) {
            JCas jCas = it.next();

            Header header = selectSingle(jCas, Header.class);
            LOG.debug("pmid: {}, text: {}", header.getDocId(),
                    jCas.getDocumentText());
            assertNotNull(header.getDocId());
            ++cnt;
        }

        assertEquals("20 results", 20, cnt);
        cr.close();
    }
}
