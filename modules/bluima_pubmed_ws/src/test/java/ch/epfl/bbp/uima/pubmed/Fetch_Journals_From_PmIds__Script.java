package ch.epfl.bbp.uima.pubmed;

import static com.google.common.base.Preconditions.checkArgument;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.MedlineCitationType;
import gov.nih.nlm.ncbi.www.soap.eutils.EFetchPubmedServiceStub.PubmedArticleType;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

public class Fetch_Journals_From_PmIds__Script {

    // get journal names
    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        int pmIds[] = LineReader.intsFrom(new FileInputStream(
                "src/test/resources/all_pmids.txt"));
        checkArgument(pmIds.length > 100);

        Histogram<String> hist = new Histogram<String>();

        int i = 0;
        for (List<Integer> partition : Lists.partition(Ints.asList(pmIds), 100)) {
            System.out.println("batch " + i++);
            try {
                Iterator<PubmedArticleType> articleIt = ps.search(
                        StringUtils.join(partition, " "), 101);
                while (articleIt.hasNext()) {
                    PubmedArticleType article = articleIt.next();
                    MedlineCitationType mc = article.getMedlineCitation();

                    String pubmedId = mc.getPMID().getString();

                    try {
                        hist.add(mc.getArticle().getJournal().getTitle()
                                .toString());
                    } catch (Exception e) {
                        System.out.println(pubmedId + e);
                    }
                }

            } catch (Exception e) {
                e.fillInStackTrace();
                ps = new PubmedSearch();
            }
        }
        System.out.println(hist);
    }

    // get pmids from these journals
    public static void main2(String[] args) throws Exception {

        TextFileWriter writer = new TextFileWriter(new File(
                "target/20130309_ns_journals_ids.txt"));

        PubmedSearch searcher = new PubmedSearch();
        for (String q : new LineReader(new FileInputStream(
                "src/test/resources/20130309_all_journals.txt"))) {

            System.out.println("searching for " + q);
            for (Integer pmId : searcher.searchIds(q + "[journal]")) {
                writer.addLine(pmId + "");
            }
            writer.flush();
        }
        writer.close();
        System.out.println("done :-)");
    }
}
