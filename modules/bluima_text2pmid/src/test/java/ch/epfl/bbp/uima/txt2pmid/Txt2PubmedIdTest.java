package ch.epfl.bbp.uima.txt2pmid;

import static org.apache.commons.io.FileUtils.listFiles;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.txt2pmid.Txt2PubmedId.PmId;
import ch.epfl.bbp.uima.txt2pmid.Txt2PubmedId.PmId.Confidence;

import com.google.common.io.Files;
@Ignore
public class Txt2PubmedIdTest {

    @Test
    public void testEvaluate() throws IOException {

        String base = "/Volumes/HDD2/ren_data/data_hdd/_papers_etc/pubmed/txt_to_pmid-evaluation_sample/pdfs";

        Txt2PubmedId txt2PubmedId = new Txt2PubmedId(new File(
                Txt2PubmedIdIndexer.INDEX_PATH));
        int highMatch = 0, lowMatch = 0, noMatch = 0, wrongHighMatch = 0, wrongLowMatch = 0;

        @SuppressWarnings("unchecked")
        Collection<File> listFiles = listFiles(new File(base),
                new String[] { "pdf" }, false);
        for (File pdfF : listFiles) {
            int pmIdGOLD = new Integer(pdfF.getName().replaceAll(".pdf", ""));

            // if (!Lists.newArrayList(16963531, 17004004).contains(pmIdGOLD))
            // continue;

            PmId pmId = txt2PubmedId.extract(pdfF);

            System.out.print("pmid\t" + pmIdGOLD + "\t");

            if (pmId == null) { // NO MATCH
                System.out.println("NO___match");
                noMatch++;

            } else if (pmId.getPmId() == pmIdGOLD) {// RIGHT PREDICTION
                if (pmId.getConfidence().equals(Confidence.HIGH)) {
                    System.out.println("HIGH_match");
                    highMatch++;
                } else {
                    System.out.println("LOW__match");
                    lowMatch++;
                }
            } else { // WRONG PREDICTION
                if (pmId.getConfidence().equals(Confidence.HIGH)) {
                    System.out.println("WRONG_HIGH_match");
                    wrongHighMatch++;
                } else {
                    System.out.println("WRONG_LOW__match");
                    wrongLowMatch++;
                }
            }
        }

        System.out.println("highMatch=" + highMatch);
        System.out.println("lowMatch=" + lowMatch);
        System.out.println("noMatch=" + noMatch);
        System.out.println("wrongHighMatch=" + wrongHighMatch);
        System.out.println("wrongLowMatch=" + wrongLowMatch);
    }

    @Test
    @Deprecated
    public void testEvaluateFromLabPeople() throws IOException {

        String index = "index_pubmed";
        String base = "/Users/richarde/Desktop/people/";

        for (String user : new File(base).list()) {

            System.out.println("\n\n___________\nproc user " + user);

            Txt2PubmedId txt2PubmedId = new Txt2PubmedId(new File(index));
            int highMatch = 0, lowMatch = 0, noMatch = 0;
            @SuppressWarnings("unchecked")
            Collection<File> listFiles = listFiles(new File(base + user + "/"),
                    new String[] { "pdf" }, true);
            for (File pdfF : listFiles) {

                PmId pmId = txt2PubmedId.extract(pdfF);

                if (pmId == null) { // NO MATCH
                    System.out.println("NO___match\tNA\t"
                            + pdfF.getAbsolutePath());
                    noMatch++;

                } else if (pmId.getConfidence().equals(Confidence.HIGH)) {
                    System.out.println("HIGH_match\t" + pmId.getPmId() + "\t"
                            + pdfF.getAbsolutePath());
                    highMatch++;
                } else {
                    System.out.println("LOW__match\t" + pmId.getPmId() + "\t"
                            + pdfF.getAbsolutePath());
                    lowMatch++;
                }
            }

            System.out.println("highMatch=" + highMatch);
            System.out.println("lowMatch=" + lowMatch);
            System.out.println("noMatch=" + noMatch);
        }
    }

    @Test
    public void testExtractFromLabPeople() throws IOException {

        String index = Txt2PubmedIdIndexer.INDEX_PATH;
        String base = "/Users/richarde/Desktop/people/";
        String out = "/Users/richarde/Desktop/people_extracted/";

        for (String user : new File(base).list()) {
            System.out.println("\n\n___________\nprocessing user " + user);
            if (user.startsWith("."))
                continue;


            Txt2PubmedId txt2PubmedId = new Txt2PubmedId(new File(index));

            @SuppressWarnings("unchecked")
            Collection<File> listFiles = listFiles(new File(base + user + "/"),
                    new String[] { "pdf" }, true);
            for (File pdfF : listFiles) {

                PmId pmId = txt2PubmedId.extract(pdfF);
                if (pmId != null) {
                    System.out.println("extracted " + pmId.getPmId());

                    Files.copy(pdfF, new File(out + pmId.getPmId() + ".pdf"));

                }
            }
        }
    }
}
