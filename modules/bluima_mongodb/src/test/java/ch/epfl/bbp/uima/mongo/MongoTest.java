package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIELD_NAME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_QUERY;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.BeforeClass;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.BiolexiconDictTerm;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import de.julielab.jules.types.Header;

public class MongoTest {

    static String[] getTestConn(String testName) {
        return new String[] { "128.178.187.248", "uima_testing",
                "test_" + testName + "_" + System.currentTimeMillis(), "", "" };
    }

    private static String[] conn;

    @BeforeClass
    public static void before() throws Exception {
        conn = getTestConn("MongoTest");

        // write one test document into Mongo
        JCas jCas = UimaTests.getTokenizedTestCas();

        BiolexiconDictTerm b = new BiolexiconDictTerm(jCas, 0, 11);
        b.setEntityId("theId");
        b.addToIndexes();

        Header h = new Header(jCas);
        h.setDocId("17");
        h.addToIndexes();

        JcasPipelineBuilder p = new JcasPipelineBuilder(jCas);
        p.add(MongoWriter.class, BlueUima.PARAM_DB_CONNECTION, conn);
        p.process();
    }

    @Test
    public void testRead() throws Exception {

        // read
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn));
        assertEquals(1, l.size());

        JCas jCas = l.get(0);
        assertEquals(UimaTests.TEST_SENTENCE, jCas.getDocumentText());

        for (Annotation a : JCasUtil.select(jCas, Annotation.class)) {
            System.out.println(a);
        }

        BiolexiconDictTerm b = selectSingle(jCas, BiolexiconDictTerm.class);
        assertNotNull(b);
        assertEquals("theId", b.getEntityId());

        Header h = selectSingle(jCas, Header.class);
        assertNotNull(h);
        assertEquals("17", h.getDocId());
    }

    @Test
    public void testReadWithQuery() throws Exception {

        String query = "{_id: \"17\"}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals(1, l.size());
    }

    @Test
    public void testReadWithBetweenQuery() throws Exception {

        String query = "{pmid: { $gt: 12, $lt: 19 }}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals(1, l.size());

        query = "{pmid: { $gt: 18, $lt: 19 }}";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals(0, l.size());

        query = "{pmid: { $gt: 8, $lt: 11 }}";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals(0, l.size());
    }

    @Test
    public void testReadWithInQuery() throws Exception {

        String query = "{pmid:{$in:[12,17]}}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals(1, l.size());

        query = "{pmid:{$in:[12,16]}}";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals(0, l.size());
    }

    @Test
    public void testReadWithEmptyQuery() throws Exception {

        String query = "{_id: \"1sadfsaf7\"}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals(0, l.size());
    }

    @Test
    public void testReadWithRegexQuery() throws Exception {

        String field = "_id";

        for (String positivePattern : new String[] { "..", "17", ".7", "1.",
                ".*" }) {
            List<JCas> l = asList(createReader(
                    RegexMongoCollectionReader.class, 
                    PARAM_DB_CONNECTION, conn, PARAM_QUERY, positivePattern,
                    PARAM_FIELD_NAME, field));
            assertEquals("pattern " + positivePattern + " should match", 1,
                    l.size());
        }

        for (String negPattern : new String[] { ".2", ".*aF" }) {
            List<JCas> l = asList(createReader(
                    RegexMongoCollectionReader.class, 
                    PARAM_DB_CONNECTION, conn, PARAM_QUERY, negPattern,
                    PARAM_FIELD_NAME, field));
            assertEquals("pattern " + negPattern + " should NOT match", 0,
                    l.size());
        }
    }

    @Test
    public void testBetween() throws Exception {

        List<JCas> l = asList(createReader(
                BetweenMongoCollectionReader.class, 
                PARAM_DB_CONNECTION, conn, PARAM_BETWEEN, new int[] { 12, 19 }));
        assertEquals("[12-19] should find 17", 1, l.size());

        l = asList(createReader(BetweenMongoCollectionReader.class,
                 PARAM_DB_CONNECTION, conn, PARAM_BETWEEN, new int[] {
                        12, 14 }));
        assertEquals("[12-14] should not find 17", 0, l.size());

    }
}
