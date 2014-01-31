package ch.epfl.bbp.uima.pubmed.mesh;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.MeshHeading;

public class MeshTermsFromBBPPeople_Script {

    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        for (int pmid : LineReader
                .intsFrom(new FileInputStream(
                        "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/pm_ids_from_bbp_people_cut.txt"))) {

            try {
                Iterator<PubmedArticleType> articleIt = ps.search(pmid
                        + "[pmid]", 1);
                if (articleIt.hasNext()) {
                    PubmedArticleType article = articleIt.next();
                    List<MeshHeading> meshTerms = PubmedSearchUtil
                            .getMeshTerms(article);
                    System.out.println(pmid + "\t" + meshTerms);

                } else {
                    System.out.println(pmid);
                }

            } catch (Exception e) {
                System.err.println(pmid + " " + e.getMessage());
                ps = new PubmedSearch();
            }
        }
    }
}
