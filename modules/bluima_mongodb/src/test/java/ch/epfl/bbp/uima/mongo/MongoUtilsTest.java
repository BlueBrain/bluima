package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUima;

/**
 * @author renaud.richardet@epfl.ch
 */
public class MongoUtilsTest {

    @Test
    public void testAddFlag() throws Exception {
        String[] conn = MongoTest.getTestConn("MongoAddFlag");

        MongoConnection mongo = new MongoConnection(conn);
        MongoUtils.addFlag(17, "blih", mongo);
        MongoUtils.addFlag(18, "blih", mongo);
        MongoUtils.addFlag(19, "blih", mongo);

        String query = "{ftr.blih:1}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals("there should be 3 docs with flag 'blih'", 3, l.size());

        MongoUtils.addFlag(17, "blah", mongo);
        MongoUtils.addFlag(28, "blah", mongo);

        query = "{ftr.blah:1}";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals("there should be 2 docs with flag 'blah'", 2, l.size());
    }

    @Test
    public void testQueryFlag() throws Exception {
        String[] conn = MongoTest.getTestConn("MongoAddFlag");

        MongoConnection mongo = new MongoConnection(conn);
        MongoUtils.addFlag(17, "blih", mongo);
        MongoUtils.addFlag(18, "blih", mongo);
        MongoUtils.addFlag(19, "blih", mongo);
        MongoUtils.addFlag(20, "blih", mongo);
        MongoUtils.addFlag(21, "blih", mongo);
        MongoUtils.addFlag(22, "blih", mongo);

        String query = "{ftr.blih:1}";
        List<JCas> l = asList(createReader(
                MongoCollectionReader.class, 
                BlueUima.PARAM_DB_CONNECTION, conn, BlueUima.PARAM_QUERY, query));
        assertEquals("there should be 2 docs with flag 'blah'", 6, l.size());

        MongoUtils.addFlag(118, "asdfasdfblah", mongo);
        MongoUtils.addFlag(119, "blasfdasfdsfadh", mongo);
        MongoUtils.addFlag(17, "blah", mongo);
        MongoUtils.addFlag(18, "blah", mongo);
        MongoUtils.addFlag(18, "bluh", mongo);
        MongoUtils.addFlag(19, "bluh", mongo);
        MongoUtils.addFlag(20, "bluh", mongo);

        query = "{ $and: [ { ftr.blah: 1 }, { ftr.bluh: 1} ] }";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals("there should be 1 docs", 1, l.size());

        query = "{ $and: [ { ftr.blah: { $ne: 1 } }, { ftr.bluh: 1} ] }";
        l = asList(createReader(MongoCollectionReader.class,
                 BlueUima.PARAM_DB_CONNECTION, conn,
                BlueUima.PARAM_QUERY, query));
        assertEquals("there should be 2 docs", 2, l.size());

    }

}