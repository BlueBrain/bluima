package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.getTitle;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.*;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.BlueUima;
import de.julielab.jules.types.Date;

/**
 * Writes the given {@link JCas} to a MySQL database (the bb_pubmed database).
 * Useful for updating it with new articles from PubMed's FTP, for example.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class PubmedDatabaseAE extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(PubmedDatabaseAE.class);

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, //
    defaultValue = { "localhost", "bb_pubmed", "root", "" },//
    description = "host, dbname, user, pw")
    private String[] db_connection;
    private int processed = 0, inserted = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://" + db_connection[0]
                + "/" + db_connection[1] + "", db_connection[2],
                db_connection[3]);
        processed = 0;
        inserted = 0;
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = getHeaderIntDocId(jCas);
        try {
            if (PubmedArticleEntity.findFirst(PUBMED_ID + " = ?", pmId) == null) {

                PubmedArticleEntity a = new PubmedArticleEntity();
                a.set(PUBMED_ID, pmId);
                a.set(ABSTRACT, jCas.getDocumentText());

                try {
                    Date date = JCasUtil.selectSingle(jCas, Date.class);
                    a.set(PUBLISHED_DATE,
                            date.getYear() + "-" + date.getMonth() + "-"
                                    + date.getDay());
                } catch (Exception e) {// nope
                    LOG.warn("could not add date to " + pmId, e);
                }

                a.set(TITLE, StringUtils.snippetizeAtSpace(getTitle(jCas), 510));
                a.saveIt();
                inserted++;
                if (processed++ % 10000 == 0)
                    LOG.debug("processed {}\tinserted {}", processed, inserted);
            }
        } catch (Exception e) {
            LOG.error("could not insert " + pmId, e);
        }
    }

    @Override
    public void destroy() {
        try {
            Base.close();
        } catch (Exception e) {
            LOG.warn("could not close conn", e);
        }
    }
}
