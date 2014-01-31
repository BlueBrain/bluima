package ch.epfl.bbp.uima.cr;

import static org.apache.uima.resource.ResourceInitializationException.COULD_NOT_ACCESS_DATA;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

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
 * Allow to read Pubmed articles based on a list of PubMed ids. It is
 * recommended not to use a very large list ( < 100k).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PubmedFromListDatabaseCR extends JCasCollectionReader_ImplBase {
    Logger LOG = LoggerFactory.getLogger(PubmedFromListDatabaseCR.class);

    private ResultSet res;
    private Database db;

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, //
    defaultValue = { "128.178.187.248", "bb_pubmed", "bemyguest", "" },//
    description = "host, dbname, user, pw")
    private String[] db_connection;

    public static final String PUBMED_IDS = "ids";
    @ConfigurationParameter(name = PUBMED_IDS, description = "a list of pubmed ids", mandatory = true)
    private int[] ids;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            db = PubmedDatabaseCR.getDb(db_connection);
            res = db.executeQuery(getSqlQuery(ids));
        } catch (Exception e) {
            throw new ResourceInitializationException(COULD_NOT_ACCESS_DATA,
                    new Object[] { e });
        }
    }

    protected String getSqlQuery(int[] ids) {
        String ins = ch.epfl.bbp.StringUtils.implode(ids, ",");
        return "SELECT pubmed_id, title, abstrct FROM pubmed_abstracts "
                + " WHERE pubmed_id IN (" + ins + ")";
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        PubmedDatabaseCR.getNext(jcas, res);
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return PubmedDatabaseCR.hasNext(res);
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