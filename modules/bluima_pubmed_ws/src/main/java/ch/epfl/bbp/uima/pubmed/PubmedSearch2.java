package ch.epfl.bbp.uima.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;
import gov.nih.nlm.ncbi.www.soap.eutils.EUtilsServiceStub;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import javatools.datatypes.ArrayQueue;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

/**
 * Interface to PubMed's webservice.
 * 
 * @see http://www.ncbi.nlm.nih.gov/books/NBK25499/#chapter4.ESearch
 * @author renaud.richardet@epfl.ch
 */
public class PubmedSearch2 {
    private static Logger LOG = LoggerFactory.getLogger(PubmedSearch2.class);

    private EUtilsServiceStub service;
    private EFetchPubmedServiceStub service2;

    public PubmedSearch2() throws AxisFault {
        init();
    }

    private void init() throws AxisFault {
        service = new EUtilsServiceStub();
        service2 = new EFetchPubmedServiceStub();
    }

    /**
     * @param query
     *            the query parameter for entrez. E.g. 'cat' or 'brain[mesh]' "
     *            + "or '16381840[pmid]' or '17170002 16381840' (for several
     *            PMIDs)
     * @return a list of articles
     */
    public Iterator<PubmedArticleType> search(String query)
            throws RemoteException {
        return search(query, Integer.MAX_VALUE);
    }

    /**
     * @param query
     *            the query parameter for entrez. E.g. 'cat' or 'brain[mesh]' "
     *            + "or '16381840[pmid]' or '17170002 16381840' (for several
     *            PMIDs)
     * @param maxNrResults
     * @return a list of article ids
     */
    public List<Integer> searchIds(String query) throws RemoteException {

        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setEmail("georges@gmail.com");
        req.setTerm(query);
        req.setRetStart("0");
        req.setRetMax(Integer.MAX_VALUE + "");
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        int count = new Integer(res.getCount());
        LOG.debug("Found {} ids for query '{}'", count, query);

        List<Integer> articleIds = new ArrayList<Integer>();
        if (count == 0)
            return articleIds;
        String[] idList = res.getIdList().getId();
        for (String id : idList) {
            articleIds.add(new Integer(id));
        }
        assert (count == articleIds.size()) : "result counts should match, "
                + articleIds.size() + ":" + count;
        return articleIds;
    }

    /**
     * @param query
     *            the query parameter for entrez. E.g. 'cat' or 'brain[mesh]' "
     *            + "or '16381840[pmid]' or '17170002 16381840' (for several
     *            PMIDs)
     * @param maxNrResults
     * @return a list of article ids
     */
    public List<Integer> searchIdsDaysBack(int daysBack) throws RemoteException {
        Preconditions.checkArgument(daysBack > 0);

        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setEmail("georges@gmail.com");
        req.setReldate(daysBack + "");
        req.setRetStart("0");
        req.setRetMax(Integer.MAX_VALUE + "");
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        int count = new Integer(res.getCount());
        LOG.debug("Found {} ids for {} daysBack", count, daysBack);

        List<Integer> articleIds = new ArrayList<Integer>();
        String[] idList = res.getIdList().getId();
        for (String id : idList) {
            articleIds.add(new Integer(id));
        }
        return articleIds;
    }

    /**
     * @param query
     *            the query parameter for entrez. E.g. 'cat' or 'brain[mesh]' "
     *            + "or '16381840[pmid]' or '17170002 16381840' (for several
     *            PMIDs)
     * @param maxNrResults
     * @return an iterator over articles
     */
    public Iterator<PubmedArticleType> search(String query, int maxNrResults)
            throws RemoteException {
        // STEP #1: search in PubMed for "query"
        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setTerm(query);
        req.setEmail("georges@gmail.com");
        // not working req.setRetStart("0"); req.setRetMax(maxNrResults + "");
        // req.setSort("PublicationDate");
        req.setUsehistory("y");// important!
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        final int count = new Integer(res.getCount());
        final int finalMaxNrResults = Math.min(maxNrResults, count);
        LOG.debug("Found {} results in total for query '{}'", count, query);
        // results output
        final String webEnv = res.getWebEnv();
        final String query_key = res.getQueryKey();
        LOG.trace("WebEnv: " + webEnv + "\nQueryKey: " + query_key);

        return getResults(webEnv, query_key, finalMaxNrResults);
    }

    /**
     * @param daysBack
     *            the number of days back
     * @return an iterator over articles
     */
    public Iterator<PubmedArticleType> searchDaysBack(int daysBack)
            throws RemoteException {
        Preconditions.checkArgument(daysBack > 0);
        // STEP #1: search in PubMed for articles day back
        EUtilsServiceStub.ESearchRequest req = new EUtilsServiceStub.ESearchRequest();
        req.setDb("pubmed");
        req.setEmail("raoul@gmail.com");
        req.setUsehistory("y");// important!
        req.setReldate(daysBack + "");
        EUtilsServiceStub.ESearchResult res = service.run_eSearch(req);
        final int count = new Integer(res.getCount());
        LOG.debug("Found {} results for the {} daysBack", count, daysBack);
        // results output
        final String webEnv = res.getWebEnv();
        final String query_key = res.getQueryKey();
        LOG.trace("WebEnv: " + webEnv + "\nQueryKey: " + query_key);
        return getResults(webEnv, query_key, new Integer(20));// res.getCount()));
    }

    private Iterator<PubmedArticleType> getResults(final String webEnv,
            final String query_key, final int finalMaxNrResults) {

        return new Iterator<EFetchPubmedServiceStub.PubmedArticleType>() {

            private Queue<PubmedArticleType> articlesQueue = new ArrayQueue<EFetchPubmedServiceStub.PubmedArticleType>();
            private int nrResultsReturned = 0;
            private int fetchesPerRuns = Math.min(20, finalMaxNrResults);
            // private int runs = (int) Math.ceil(count
            // / new Double(fetchesPerRuns));
            // private int currentRun = 0;
            private int start = 0;

            private void tryToFetchMore() {
                if (nrResultsReturned < finalMaxNrResults) {
                    try {
                        LOG.debug("Fetching results from id {} to id {} ",
                                start, start + fetchesPerRuns);
                        EFetchPubmedServiceStub.EFetchRequest req2 = new EFetchPubmedServiceStub.EFetchRequest();
                        req2.setWebEnv(webEnv);
                        req2.setQuery_key(query_key);
                        // req2.setRetstart(start + "");
                        // req2.setRetmax(fetchesPerRuns + "");

                        EFetchPubmedServiceStub.EFetchResult res2 = service2
                                .run_eFetch(req2);
                        for (int j = 0; j < res2.getPubmedArticleSet()
                                .getPubmedArticleSetChoice().length; j++) {

                            PubmedArticleType art = res2.getPubmedArticleSet()
                                    .getPubmedArticleSetChoice()[j]
                                    .getPubmedArticle();
                            if (art != null) {

                                LOG.debug("found ID{}:{}", art
                                        .getMedlineCitation().getPMID(), art
                                        .getMedlineCitation().getArticle()
                                        .getArticleTitle());
                                articlesQueue.add(art);
                                nrResultsReturned++;
                            }
                            if (nrResultsReturned >= finalMaxNrResults) { // enough!
                                return;
                            }
                        }
                        start += fetchesPerRuns;
                    } catch (Exception e) {
                        LOG.error("error fetching more elements", e);
                    }
                }
            }

            @Override
            public boolean hasNext() {
                if (articlesQueue.isEmpty()) {
                    tryToFetchMore();
                }
                return !articlesQueue.isEmpty();
            }

            @Override
            public PubmedArticleType next() {
                return articlesQueue.poll();
            }

            @Override
            public void remove() {
                throw new NotImplementedException();
            }
        };
    }
}