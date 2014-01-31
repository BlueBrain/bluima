package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SKIP_EMPTY_DOCS;
import static org.apache.uima.resource.ResourceInitializationException.COULD_NOT_ACCESS_DATA;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.db.utils.Database;
import de.julielab.jules.types.pubmed.Header;

/**
 * @author renaud.richardet@epfl.ch
 * @see PubmedWholeDatabaseCR to iterate the whole Pubmed database
 */
public class PubmedDatabaseCR extends JCasCollectionReader_ImplBase {
    static final Logger LOG = LoggerFactory.getLogger(PubmedDatabaseCR.class);

    private ResultSet res;
    private Database db;

    @ConfigurationParameter(name = PARAM_BETWEEN, description = "specifies a"
            + " range of pubmed_id, e.g. {13,17} --> 13 <= pubmed_id <= 17. "
            + "It is recommended to keep it under 1M, as these results are "
            + "all stored in the db memory")
    private int[] between;

    @ConfigurationParameter(name = PARAM_SKIP_EMPTY_DOCS, description = //
    "Skip PubMed articles that have no abstract", defaultValue = "true")
    private boolean skipEmptyDocs;

    @ConfigurationParameter(name = PARAM_DB_CONNECTION, //
    defaultValue = { "128.178.187.248", "bb_pubmed", "bemyguest", "" },//
    // defaultValue = { "127.0.0.1", "bb_pubmed", "root", "" },//
    // warning: somehow "localhost" does not work...
    description = "host, dbname, user, pw")
    private String[] db_connection;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            db = getDb(db_connection);
            res = db.executeQuery(getSqlQuery(between, skipEmptyDocs));
        } catch (Exception e) {
            throw new ResourceInitializationException(COULD_NOT_ACCESS_DATA,
                    new Object[] { e });
        }
    }

    public static Database getDb(String[] db_connection_) throws SQLException {
        LOG.debug("connect to {} with user {}", db_connection_[0],
                db_connection_[2]);
        return new Database(db_connection_[0], db_connection_[1],
                db_connection_[2], db_connection_[3]);
    }

    private static String getSqlQuery(int[] between, boolean skipEmptyDocs) {
        return "SELECT pubmed_id, title, abstrct FROM pubmed_abstracts "
                + ((between == null) ? "" : (" WHERE pubmed_id BETWEEN "
                        + between[0] + " AND " + between[1]))
                + (skipEmptyDocs ? " AND abstrct IS NOT NULL" : "");
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        getNext(jcas, res);
    }

    static void getNext(JCas jcas, ResultSet res_) throws CollectionException {
        try {
            int pmid = res_.getInt(1);
            String title = res_.getString(2);
            String abstrct = res_.getString(3);
            if (abstrct == null)
                abstrct = "";

            Header h = new Header(jcas);
            h.setDocId(pmid + "");
            h.setTitle(title);
            h.addToIndexes();

            jcas.setDocumentText(abstrct);

        } catch (SQLException e) {
            throw new CollectionException(e);
        }
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return hasNext(res);
    }

    static boolean hasNext(ResultSet res_) throws CollectionException {
        try {
            if (res_.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new CollectionException(e);
        }
        return false;
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }

    @Override
    public void close() throws IOException {
        try {
            res.close();
            db.close();
        } catch (SQLException e) {
            LOG.warn("could not close conn", e);
        }
    }
}
