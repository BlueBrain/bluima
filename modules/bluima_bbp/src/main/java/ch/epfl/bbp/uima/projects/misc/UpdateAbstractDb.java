package ch.epfl.bbp.uima.projects.misc;

import static ch.epfl.bbp.StringUtils.nullToEmpty;
import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.range.RangeBuilder.from;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.ABSTRACT;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.LANG;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.PUBLISHED_DATE;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.PUBMED_ID;
import static ch.epfl.bbp.uima.ae.PubmedArticleEntity.TITLE;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getAbstract;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getDate;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getLanguageJoin;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getPmId;
import static ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.getTitle;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Lists.partition;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang.StringUtils.join;
import static org.slf4j.LoggerFactory.getLogger;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.util.Iterator;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.ae.PubmedArticleEntity;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil;

/**
 * Gets PubMed abstracts in batch (with descending ids), updating the
 * bb_pubmed.pubmed_abstracts database
 * 
 * @author renaud.richardet@epfl.ch
 */
public class UpdateAbstractDb {
    private static final Logger LOG = getLogger(UpdateAbstractDb.class);

    final static int BATCH_SIZE = 500;
    final static String[] db_connection = { "localhost", "bb_pubmed", "root",
            "" };
    private static PubmedSearch pubmed;

    public static void main(String[] args) throws Exception {

        init();

        int maxPmId = 24511642;// TODO update every time
        for (int i = maxPmId; i > 1; i = i - BATCH_SIZE) {
            LOG.debug("query: {}-{}", i, i + BATCH_SIZE);
            updateWithQuery(from(i).upto(i + BATCH_SIZE - 1).asList(), pubmed);
        }
        System.out.println("done :-)");
    }

    private static void init() throws AxisFault {
        PubmedArticleEntity.connect(db_connection);
        pubmed = new PubmedSearch();
    }

    private static void reInit() throws AxisFault {
        PubmedArticleEntity.reConnect(db_connection);
        pubmed = new PubmedSearch();
    }

    public static void main_with_file(String[] args) throws Exception {

        init();

        List<Integer> pmids = newLinkedList();
        for (String line : linesFrom("/home/richarde/Desktop/pm_ft_ids.json")) {
            if (line.startsWith("_id"))
                continue;
            pmids.add(parseInt(line.replaceAll("\"", "")));
        }
        for (List<Integer> partition : partition(pmids, BATCH_SIZE)) {
            LOG.debug("new partition of {} items", BATCH_SIZE);
            updateWithQuery(partition, pubmed);
        }
    }

    static void updateWithQuery(List<Integer> pmIds, PubmedSearch pubmed) {
        String query = join(pmIds, " ");
        try {// just to make sure
            Iterator<PubmedArticleType> res = pubmed.search(query);
            while (res.hasNext()) {
                PubmedArticleType article = res.next();
                try {
                    updateArticle(article);
                } catch (Exception e) {
                    LOG.error("could not update " + getPmId(article), e);
                    reInit();
                }
            }
        } catch (Exception e) {
            LOG.error("could not update query " + query, e);
        }
    }

    private static void updateArticle(PubmedArticleType article) {
        int pmId = PubmedSearchUtil.getPmId(article);

        PubmedArticleEntity a = PubmedArticleEntity.find_(pmId);
        if (a == null) { // new article --> insert
            PubmedArticleEntity p = new PubmedArticleEntity();
            p.setInteger(PUBMED_ID, pmId);
            p.setString(TITLE, nullToEmpty(getTitle(article)));
            p.setString(ABSTRACT, nullToEmpty(getAbstract(article)));
            p.setString(PUBLISHED_DATE, nullToEmpty(getDate(article)));
            p.set(LANG, nullToEmpty(getLanguageJoin(article)).toLowerCase());
            p.save();

        } else { // existing entry --> update given fields
            // update language
            PubmedArticleEntity.update(LANG + " =?", PUBMED_ID + " =?",
                    nullToEmpty(getLanguageJoin(article)).toLowerCase(), pmId);

            // update other fields
            String pbAbs = PubmedSearchUtil.getAbstract(article);
            String aAbs = a.getString(ABSTRACT);
            if (pbAbs != null
                    && (aAbs == null || aAbs.length() < pbAbs.length())) {
                PubmedArticleEntity.update(ABSTRACT + " =?", PUBMED_ID + " =?",
                        pbAbs, pmId);
            }
        }
    }
}
