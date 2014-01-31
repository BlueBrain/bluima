package ch.epfl.bbp.uima.pubmed;

import static com.google.common.base.Preconditions.checkArgument;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.epfl.bbp.io.LineReader;

public class Fetch_Emails_From_PmIds__Script {

    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        String base = "/Users/richarde/Desktop/BBP_experiments/25_pmids_for_srikanth/";
        for (int i = 5; i < 6; i++) {

            System.out.println(">>>>reading file " + i);
            int pmIds[] = LineReader.intsFrom(new FileInputStream(base + i));
            checkArgument(pmIds.length > 3);

            for (int pmid : pmIds) {

                try {
                    Iterator<PubmedArticleType> articleIt = ps.search(pmid
                            + "[pmid]", 1);
                    if (articleIt.hasNext()) {
                        PubmedArticleType article = articleIt.next();

                        String email = PubmedSearchUtil.getEmail(article);
                        System.out.println(pmid + "\t" + email);

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

}
