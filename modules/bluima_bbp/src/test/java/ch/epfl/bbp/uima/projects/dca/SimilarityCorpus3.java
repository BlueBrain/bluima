package ch.epfl.bbp.uima.projects.dca;

import static ch.epfl.bbp.MapUtils.sortByValue;
import static ch.epfl.bbp.io.LineReader.intsFrom;
import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.Double.parseDouble;
import static org.python.google.common.collect.Maps.newHashMap;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.bbp.io.TextFileWriter;
import edu.emory.mathcs.backport.java.util.Arrays;

/**
 * takes output of 'mpupd -m 0.0' and computes cosine similarity.
 * 
 * TODO update ids, too
 * 
 * @author richarde
 */
public class SimilarityCorpus3 {

    public static void main(String[] args) throws Exception {

        final String ROOT = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/1_dca/models/20140504_dca-1m_ns/";
        final String idsFile = ROOT + "1m_ns.pmids.txt";
        final int NR_TOPICS = 1000;
        final String DOCTOPICS = ROOT + "trained__1000/1m_ns.doctopics";
        final int THE_PMID = 18077435;

        // read ids
        Map<Integer, Integer> pmid2lineid = newHashMap();
        int[] pmids = intsFrom(idsFile);
        for (int i = 0; i < pmids.length; i++) {
            pmid2lineid.put(pmids[i], i);
        }

        // read topics
        Map<Integer, double[]> pmid2topicdist = newHashMap();
        List<String> doctopics = linesFrom(DOCTOPICS);
        for (int l = 0; l < doctopics.size(); l++) {
            int pmId = pmids[l];

            String line = doctopics.get(l);
            String[] split = line.split(":  ");
            checkEquals(2, split.length);
            String[] distStr = split[1].split(" ");
            checkEquals(NR_TOPICS, distStr.length);
            double[] topicdist = new double[distStr.length];
            for (int i = 0; i < distStr.length; i++) {
                topicdist[i] = parseDouble(distStr[i]);
            }
            pmid2topicdist.put(pmId, topicdist);
        }
        checkEquals(pmid2lineid.size(), pmid2topicdist.size(),
                "doc cnt should match");
        final double[] theTopicDist = pmid2topicdist.get(THE_PMID);
        System.out.println(Arrays.toString(theTopicDist));

        checkNotNull(theTopicDist);
        System.out.println("done reading");

        Map<Integer, Double> pmid_distances = newHashMap();
        int progress = 0;
        for (Entry<Integer, double[]> pmid_pz : pmid2topicdist.entrySet()) {
            double dist = jensenShannonDivergence(pmid_pz.getValue(),
                    theTopicDist);
            pmid_distances.put(pmid_pz.getKey(), dist);
            if (progress++ % 1000 == 0) {
                System.out.println("progress " + progress);
            }
        }

        // sort and output
        TextFileWriter w = new TextFileWriter("out.txt");
        int topN = 100, i = 0;
        for (Entry<Integer, Double> pmid_distance : sortByValue(pmid_distances)
                .entrySet()) {
            int pmid = pmid_distance.getKey();
            w.addLine(pmid + "\t" + pmid_distance.getValue());
            System.out.println(pmid + "\t"
                    + Arrays.toString(pmid2topicdist.get(pmid)));
            if (i++ > topN)
                break;
        }
        w.close();
        System.out.println("done :-)");
    }

    // public static void main(String[] args) {
    // double[] d1 = { 0, 0.2, 0.3, 0.5 };
    // double[] d2 = { 0, 0.1, 0.5, 0.4 };
    // System.out.println(jensenShannonDivergence(d1, d2));
    // }

    /**
     * Returns the Jensen-Shannon divergence.
     */
    public static double jensenShannonDivergence(final double[] p1,
            final double[] p2) {
        assert (p1.length == p2.length);
        double[] average = new double[p1.length];
        for (int i = 0; i < p1.length; ++i) {
            average[i] += (p1[i] + p2[i]) / 2;
        }
        return (klDivergence(p1, average) + klDivergence(p2, average)) / 2;
    }

    public static final double log2 = Math.log(2);

    /**
     * Returns the KL divergence, K(p1 || p2).
     * 
     * The log is w.r.t. base 2.
     * <p>
     * 
     * *Note*: If any value in <tt>p2</tt> is <tt>0.0</tt> then the
     * KL-divergence is <tt>infinite</tt>. Limin changes it to zero instead of
     * infinite.
     * 
     */
    public static double klDivergence(final double[] p1, final double[] p2) {

        double klDiv = 0.0;

        for (int i = 0; i < p1.length; ++i) {
            if (p1[i] == 0) {
                continue;
            }
            if (p2[i] == 0.0) {
                continue;
            } // Limin

            klDiv += p1[i] * Math.log(p1[i] / p2[i]);
        }

        return klDiv / log2; // moved this division out of the loop -DM
    }
}
