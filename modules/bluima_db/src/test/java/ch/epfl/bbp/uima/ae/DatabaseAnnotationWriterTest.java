package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_FIELDS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.ae.DatabaseAnnotationWriter.PARAM_CREATE_TABLE_STATEMENT;
import static ch.epfl.bbp.uima.ae.DatabaseAnnotationWriter.PARAM_INSERT_STATEMENT;
import static ch.epfl.bbp.uima.cr.PubmedDatabaseCR.getDb;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.sql.ResultSet;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.cr.TextArrayReader;
import ch.epfl.bbp.uima.db.utils.Database;
import de.julielab.jules.types.Token;

public class DatabaseAnnotationWriterTest {

    @Test
    @Ignore
    // if no db is present...
    public void test() throws Exception {

        String[] conn = { "localhost", "test_bluima", "root", "" };
        String tableName = "table_" + currentTimeMillis();

        String createTable = "CREATE TABLE `" + tableName + "` ("
                + "  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,"
                + "  `pmid` int(11) DEFAULT NULL,"
                + "  `token` varchar(255) DEFAULT NULL,"
                + "  `begin` int(6) DEFAULT NULL,"
                + "  `end` int(6) DEFAULT NULL," + "  PRIMARY KEY (`id`)"
                + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

        String insert = "INSERT INTO `" + tableName
                + "` (`pmid`, `token`, `begin`, `end`) VALUES (?,?,?,?)";
        String[] fields = { "coveredText", "begin", "end" };
        AnalysisEngineDescription aed = createEngineDescription(
                DatabaseAnnotationWriter.class,
                PARAM_DB_CONNECTION,
                conn,//
                PARAM_CREATE_TABLE_STATEMENT, createTable,
                PARAM_INSERT_STATEMENT, insert, PARAM_ANNOTATION_CLASS,
                Token.class.getName(), PARAM_ANNOTATION_FIELDS, fields);

        runPipeline(
                createReader(TextArrayReader.class, JULIE_TSD,
                        PARAM_INPUT, new String[] { "bli blah", "boo" }), //
                createEngineDescription(NaiveSentenceSplitterAnnotator.class),
                createEngineDescription(WhitespaceTokenizerAnnotator.class),
                aed);

        Database db = getDb(conn);

        assertTrue(db.listTables().contains(tableName));

        ResultSet rs = db.executeQuery("SELECT pmid, token, begin, end FROM "
                + tableName);
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        assertEquals("bli", rs.getString(2));
        assertEquals(0, rs.getInt(3));
        assertEquals(3, rs.getInt(4));
        assertTrue(rs.next());
        assertTrue(rs.next());

        // db.execute(db.generateDrop(tableName));
        db.close();
    }
}
