package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.MissingUtils.printf;

import java.io.File;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * How many journals from The Journal of comparative neurology effectively got
 * stored in the nfs4 preprocessed folder.
 * <code>select pubmed_id FROM pubmed_abstracts WHERE journal_raw = 'The Journal of comparative neurology'  and data_path IS NOT NULL</code>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CheckHowManyPdfsExtractedToNfs4 {

    public static void main(String[] args) throws Exception {

        // list of all pmIds from "The Journal of comparative neurology"'s pdfs
        // that have been successfully crawled

        int[] pmIds = LineReader
                .intsFrom("/Users/richarde/Desktop/BBP_experiments/23_extract_brainregions/ABA/20131120_bluima_extractions/20131204_The_Journal_comparative_neurology-crawled.txt");

        int total = 0, exists = 0;

        for (int pmId : pmIds) {

            String path = "/Volumes/simulation/nlp/data/20131120_preprocessed/"
                    + StructuredDirectory.getFilePath(pmId, "gz");
            if (new File(path).exists())
                exists++;
            total++;
        }
        printf("total {} exists {}", total, exists);
        // total 5554 exists 3624
    }
}
