package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_SKIP_EMPTY_DOCS;
import static org.apache.uima.resource.ResourceInitializationException.COULD_NOT_ACCESS_DATA;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.db.utils.Database;

/**
 * Allow to iterate the WHOLE db (no BETWEEN)
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class PubmedWholeDatabaseCR extends JCasCollectionReader_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(PubmedWholeDatabaseCR.class);

    protected ResultSet res;
    protected Database db;

    protected int maxPubmedId;
    protected int currentBatchStart = 0, batchIncrement = 5000; // counters

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, //
    defaultValue = { "128.178.187.248", "bb_pubmed", "bemyguest", "" },//
    // defaultValue = { "127.0.0.1", "bb_pubmed", "root", "" },//
    // warning: somehow "localhost" does not work...
    description = "host, dbname, user, pw")
    protected String[] db_connection;

    @ConfigurationParameter(name = PARAM_SKIP_EMPTY_DOCS, description = " Skip PubMed articles that have no abstract", defaultValue = "true")
    protected boolean skipEmptyDocs;

    @ConfigurationParameter(name = BlueUima.PARAM_AND_QUERY, description = "specifies an additional query to filter on, e.g.  AND pubmed_id IN (SELECT id FROM neuroscience_ids_from_mesh)", defaultValue = "")
    private String andQuery;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            db = PubmedDatabaseCR.getDb(db_connection);

            res = db.executeQuery("SELECT MAX(pubmed_id) FROM pubmed_abstracts");
            res.next();
            maxPubmedId = res.getInt(1);

            res = db.executeQuery(getSqlQuery(currentBatchStart,
                    currentBatchStart + batchIncrement, skipEmptyDocs, andQuery));

        } catch (Exception e) {
            throw new ResourceInitializationException(COULD_NOT_ACCESS_DATA,
                    new Object[] { e });
        }
    }

    public String getSqlQuery(int from, int to, boolean skipEmptyDocs_,
            String andQuery_) {
        return "SELECT pubmed_id, title, abstrct FROM pubmed_abstracts "
                + " WHERE pubmed_id BETWEEN " + from + " AND " + to
                + (skipEmptyDocs_ ? " AND abstrct IS NOT NULL" : "") + " "
                + andQuery_;
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        PubmedDatabaseCR.getNext(jcas, res);
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        try {
            if (res.next()) {
                return true;
            } else {
                // try to fetch more from db
                while (currentBatchStart < maxPubmedId) {
                    currentBatchStart += batchIncrement;

                    Statement stmt = db.createStatement();
                    stmt.setFetchSize(Integer.MIN_VALUE);
                    res = stmt.executeQuery(getSqlQuery(currentBatchStart,
                            currentBatchStart + batchIncrement - 1,
                            skipEmptyDocs, andQuery));
                    if (res.next()) {
                        return true;
                    }
                }
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