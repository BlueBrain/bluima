package ch.epfl.bbp.uima.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PollForNewerPubmed {

    private static Logger LOG = LoggerFactory
	    .getLogger(PollForNewerPubmed.class);

    private EFetchPubmedServiceStub service2;

    public PollForNewerPubmed() throws AxisFault {
	service2 = new EFetchPubmedServiceStub();
    }

    /**
     * @param query
     * @param maxNrResults
     * @return a list of articles
     */
    public List<PubmedArticleType> searchByIds(int from, int to)
	    throws RemoteException {

	assert (from < to) : "from should be smaller than to";
	assert (to - from < 200) : "can fetch max 200";
	LOG.debug("Fetching results from id {} to id {} ", from, to);

	List<Integer> idsList = new ArrayList<Integer>();
	for (int i = from; i <= to; i++) {
	    idsList.add(i);
	}
	return searchByIds(idsList);
    }

    private List<PubmedArticleType> searchByIds(List<Integer> idsList)
	    throws RemoteException {
	String ids = org.apache.commons.lang.StringUtils.join(idsList, ",");

	List<PubmedArticleType> articles = new ArrayList<EFetchPubmedServiceStub.PubmedArticleType>();
	EFetchPubmedServiceStub.EFetchRequest req2 = new EFetchPubmedServiceStub.EFetchRequest();
	req2.setEmail("georges@gmail.com");
	req2.setId(ids);

	EFetchPubmedServiceStub.EFetchResult res2 = service2.run_eFetch(req2);
	LOG.debug("res {}", res2.getPubmedArticleSet()
		.getPubmedArticleSetChoice().length);
	for (int j = 0; j < res2.getPubmedArticleSet()
		.getPubmedArticleSetChoice().length; j++) {

	    PubmedArticleType art = res2.getPubmedArticleSet()
		    .getPubmedArticleSetChoice()[j].getPubmedArticle();
	    if (art != null) {
		LOG.debug("found ID{}:{}", art.getMedlineCitation().getPMID(),
			art.getMedlineCitation().getArticle().getArticleTitle());
		articles.add(art);
	    }
	}
	return articles;
    }

    public static void main(String[] args) throws RemoteException {
	new PollForNewerPubmed().searchByIds(20760024, 20760026);
    }
}
