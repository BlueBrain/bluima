package ch.epfl.bbp.uima.projects.dca;

import static ch.epfl.bbp.MissingUtils.printf;
import static ch.epfl.bbp.Progress.printProgressEvery;
import static ch.epfl.bbp.io.LineReader.linesFrom;
import static java.lang.Integer.parseInt;
import static org.python.google.common.collect.Sets.newHashSet;

import java.io.FileInputStream;
import java.util.Set;

import ch.epfl.bbp.io.LineReader;

public class SimilarityCorpusPrintSelected {

    public static void main(String[] args) throws Exception {

        Set<Integer> pmids = newHashSet();
        for (String l : linesFrom("/Volumes/HDD2/ren_scratch/Dropbox/frontiers/eval/evaluate_16453205/eval_corpus_16453205.pmid_scores.tsv")) {
            pmids.add(parseInt(l.split("\t")[0]));
        }
        printf("loaded {} pmids", pmids.size());

        int i = 0;
        for (String l : new LineReader(new FileInputStream(
                "z3000_pmid16453205_topN10000000.txt"))) {
            int pmid = parseInt(l.substring(0, l.indexOf("\t")).trim());
            if (pmids.contains(pmid)) {
                System.out.println("at line " + i + ":: " + l);
            }
            printProgressEvery(100000);
            i++;
        }
    }
}
