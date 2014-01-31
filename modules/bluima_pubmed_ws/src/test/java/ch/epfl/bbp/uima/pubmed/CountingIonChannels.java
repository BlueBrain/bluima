package ch.epfl.bbp.uima.pubmed;

import static ch.epfl.bbp.io.TextFileWriter.write;
import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.util.List;
import java.util.Set;

import ch.epfl.bbp.io.SVReader.TSVReader;

/**
 * For each IC from Channelpedia, query how many on PubMed.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CountingIonChannels {

    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();

        for (List<String> line : new TSVReader(new File(
                "src/test/resources/20130927_ionchannels.tsv"), false)) {

            try {

                String icName = line.get(0);
                System.out.println("query " + icName);
                Set<Integer> pmIds = newHashSet();
                for (String synonym : newHashSet(line)) {
                    pmIds.addAll(ps.searchIds(synonym));
                }
                System.out.println("uniques " + pmIds.size() + "\n");

                write(new File("target/ic_" + icName + ".txt"), pmIds);

            } catch (Exception e) {
                e.fillInStackTrace();
                ps = new PubmedSearch();
            }
        }

        System.out.println("done :-)");
    }
}
