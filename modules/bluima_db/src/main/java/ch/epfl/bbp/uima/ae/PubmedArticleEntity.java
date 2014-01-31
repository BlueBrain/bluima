package ch.epfl.bbp.uima.ae;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 * 
 * ActiveJDBC entity for bb_pubmed.pubmed_abstracts database table.
 * 
 * @author renaud.richardet@epfl.ch
 */
@Table("pubmed_abstracts")
public class PubmedArticleEntity extends Model {

    public static final String PUBMED_ID = "pubmed_id";
    public static final String ABSTRACT = "abstrct";
    public static final String AUTHORS_RAW = "authors_raw";
    public static final String JOURNAL_RAW = "journal_raw";
    public static final String MESHS_RAW = "meshs_raw";
    public static final String PUBLISHED_DATE = "published_date";
    public static final String TITLE = "title";
    public static final String DOI = "doi";
    public static final String DATA_PATH = "data_path";
    public static final String CRAWL_STATUS = "crawl_status";
    public static final String PMC_ID = "pmc_id";
    public static final String PRIO = "prio";
    public static final String LANG = "lang";

    /** Need an established connection (Base.open...) */
    public static PubmedArticleEntity getArticle(int pmId) {
        return PubmedArticleEntity.findFirst(PUBMED_ID + " = ?", pmId);
    }

    public static void reConnect(String[] db_connection) {
        try {
            Base.close();
        } catch (Exception e) {// nope
        }
        connect(db_connection);
    }

    public static void connect(String[] db_connection) {
        Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://" + db_connection[0]
                + "/" + db_connection[1] + "", db_connection[2],
                db_connection[3]);
    }

    public static PubmedArticleEntity find_(int pmId) {
        return PubmedArticleEntity.findFirst(PUBMED_ID + " = ?", pmId);
    }

}
