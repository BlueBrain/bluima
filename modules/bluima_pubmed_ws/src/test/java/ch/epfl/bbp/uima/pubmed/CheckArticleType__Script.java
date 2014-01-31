package ch.epfl.bbp.uima.pubmed;

import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.FileInputStream;
import java.util.Iterator;

import ch.epfl.bbp.io.LineReader;

public class CheckArticleType__Script {

    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        for (int pmid : LineReader
                .intsFrom(new FileInputStream(
                        "/Users/richarde/Desktop/BBP_experiments/28_extract_reference-section/pmids_without_refs.txt"))) {
//        "/Users/richarde/Desktop/BBP_experiments/28_extract_reference-section/pmids_with_multiple_refs.txt"))) {

            try {
                Iterator<PubmedArticleType> articleIt = ps.search(pmid
                        + "[pmid]", 1);
                if (articleIt.hasNext()) {
                    PubmedArticleType article = articleIt.next();

                    System.out.println(pmid + "\t"
                            + PubmedSearchUtil.getArticleType(article));

                } else {
                    System.out.println("ERRRR " + pmid);
                }

            } catch (Exception e) {
                System.err.println(pmid + " " + e.getMessage());
                ps = new PubmedSearch();
            }
        }
    }
}
