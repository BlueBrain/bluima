package ch.epfl.bbp.uima.pubmed.mesh;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static com.google.common.collect.Sets.newHashSet;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil;
import ch.epfl.bbp.uima.pubmed.PubmedSearchUtil.MeshHeading;

public class TestThatNSMeshTermsAreGoodEnough__Script {

    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        Set<String> nsMeshTerms = newHashSet(linesFrom(new FileInputStream(
                "src/main/resources/mesh_onto.txt")));

        for (int pmid : LineReader
                .intsFrom(new FileInputStream(
                        "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/pm_ids_from_bbp_people.txt"))) {

            try {
                Iterator<PubmedArticleType> articleIt = ps.search(pmid
                        + "[pmid]", 1);
                if (articleIt.hasNext()) {
                    PubmedArticleType article = articleIt.next();

                    List<MeshHeading> meshTerms = PubmedSearchUtil
                            .getMeshTerms(article);
                    boolean foundInNsList = false;
                    for (MeshHeading meshHeading : meshTerms) {
                        if (nsMeshTerms.contains(meshHeading.descriptor)) {
                            foundInNsList = true;
                            break;
                        }
                    }
                    if (!foundInNsList)
                        System.out.println(pmid + "\tNOT FOUND");
                    else
                        System.out.println(pmid + "\t");

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
