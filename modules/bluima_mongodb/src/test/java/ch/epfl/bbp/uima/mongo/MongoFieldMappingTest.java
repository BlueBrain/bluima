package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.StringArray;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.DataTable;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import de.julielab.jules.types.Header;

public class MongoFieldMappingTest {
    private static Logger LOG = getLogger(MongoFieldMappingTest.class);

    private static String[] conn;

    @BeforeClass
    public static void before() {
        conn = MongoTest.getTestConn("MongoFieldMappingTest");
        LOG.debug("got connection");
    }

    @Test
    @Ignore
    public void testListAnnotations() throws Exception {
        for (String a : MongoFieldMapping.ALL_MAPPINGS.keySet()) {
            LOG.debug(a);
        }
    }

    @Test
    public void test() throws Exception {

        JCas jCas = UimaTests.getTokenizedTestCas();

        DataTable dt = new DataTable(jCas);
        dt.setTableId(12);
        dt.setPageNumber(13);

        StringArray sa = new StringArray(jCas, 4);
        sa.copyFromArray(new String[] { "one", "two", "t", "f" }, 0, 0, 4);
        dt.setBody(sa);

        dt.addToIndexes();

        Header h = new Header(jCas);
        h.setDocId("117");
        h.addToIndexes();

        LOG.debug("done creating jCas");

        JcasPipelineBuilder p = new JcasPipelineBuilder(jCas);
        p.add(MongoWriter.class, BlueUima.PARAM_DB_CONNECTION, conn);
        p.process();

        LOG.debug("done storing in db");

        // read
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn));
        assertEquals(1, l.size());

        jCas = l.get(0);
        assertEquals(UimaTests.TEST_SENTENCE, jCas.getDocumentText());

        h = selectSingle(jCas, Header.class);
        assertNotNull(h);
        assertEquals("117", h.getDocId());

        dt = selectSingle(jCas, DataTable.class);
        assertNotNull(dt);
        assertEquals(12, dt.getTableId());
        StringArray body = dt.getBody();
        assertEquals(4, body.size());

        assertEquals("one", body.get(0));
        assertEquals("f", body.get(3));

        LOG.debug("done done");
    }

}
