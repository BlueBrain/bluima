package ch.epfl.bbp.uima.projects.dca;

import static ch.epfl.bbp.MapUtils.sortByValue;
import static ch.epfl.bbp.io.LineReader.intsFrom;
import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static com.google.common.base.Preconditions.checkNotNull;
import static edu.emory.mathcs.backport.java.util.Collections.max;
import static edu.emory.mathcs.backport.java.util.Collections.min;
import static java.lang.Double.parseDouble;
import static org.python.google.common.collect.Maps.newHashMap;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;

import org.la4j.vector.dense.BasicVector;
import org.la4j.vector.sparse.CompressedVector;
import org.la4j.vector.sparse.SparseVector;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;

/**
 * takes output of 'mpupd -m 0.0' and computes cosine similarity.
 * 
 * TODO update ids, too
 * 
 * @author richarde
 */
public class SimilarityCorpus4 {

    public static void main(String[] args) throws Exception {

        final String ROOT = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/1_dca/models/20140504_dca-1m_ns/";
        final String idsFile = ROOT + "1m_ns.pmids.txt";
        final int NR_TOPICS = 3000;
        final String DOCTOPICS = ROOT + "trained__" + NR_TOPICS
                + "/1m_ns.doctopics";
        
        //final int[] THE_PMIDS = { 18077435, 16453205 };// Stephanie
        final int[] THE_PMIDS = { 17140740, 17507914, 12369254 };// Kamila

        // read ids
        Map<Integer, Integer> pmid2lineid = newHashMap();
        int[] pmids = intsFrom(idsFile);
        for (int i = 0; i < pmids.length; i++) {
            pmid2lineid.put(pmids[i], i);
        }

        // read topics
        Map<Integer, SparseVector> pmid2topicdist = newHashMap();

        int lineNr = 0;
        for (String line : new LineReader(new FileInputStream(DOCTOPICS))) {
            int pmId = pmids[lineNr];
            lineNr++;

            String[] split = line.split(":  ");
            checkEquals(2, split.length);
            String[] distStr = split[1].split(" ");
            checkEquals(NR_TOPICS, distStr.length);
            double[] topicdist = new double[distStr.length];
            for (int i = 0; i < distStr.length; i++) {
                topicdist[i] = parseDouble(distStr[i]);
            }
            pmid2topicdist.put(pmId, new CompressedVector(topicdist));
        }
        checkEquals(pmid2lineid.size(), pmid2topicdist.size(),
                "doc cnt should match");
        System.out.println("done reading");

        for (int thePmId : THE_PMIDS) {

            System.out.println("###########################################");
            final BasicVector theTopicDist = new BasicVector(
                    pmid2topicdist.get(thePmId));
            System.out.println(thePmId + "\t" + theTopicDist);
            checkNotNull(theTopicDist);
            sparsePrint(theTopicDist);

            Map<Integer, Double> pmid_distances = newHashMap();
            int progress = 0;
            for (Entry<Integer, SparseVector> pmid_pz : pmid2topicdist
                    .entrySet()) {

                final BasicVector pz_normalized = new BasicVector(
                        pmid_pz.getValue());

                double dist = jensenShannonDivergence(pz_normalized.toArray(),
                        theTopicDist.toArray());
                // double dist = Similarity.calculateSimilarity(
                // pz_normalized.toArray(), theTopicDist.toArray(),
                // Similarity.COSINE);
                pmid_distances.put(pmid_pz.getKey(), dist);
                if (progress++ % 10000 == 0) {
                    System.out.println("progress " + progress);
                }
            }

            // sort and output
            int topN = 100, i = 0;
            TextFileWriter w = new TextFileWriter("z" + NR_TOPICS + "_pmid"
                    + thePmId + "_topN" + topN + ".txt");
            for (Entry<Integer, Double> pmid_distance : sortByValue(
                    pmid_distances).entrySet()) {
                int pmid = pmid_distance.getKey();
                w.addLine(pmid + "\t" + pmid_distance.getValue());
                System.out.println(pmid + "\t" + pmid_distance.getValue()
                        + "\t" + pmid2topicdist.get(pmid));
                if (i++ > topN)
                    break;
            }

            System.out.println("min: " + min(pmid_distances.values()));
            System.out.println("max: " + max(pmid_distances.values()));

            w.close();
        }
        System.out.println("done :-) ");
    }

    private static void sparsePrint(BasicVector theTopicDist) {

        double[] array = theTopicDist.toArray();
        for (int i = 0; i < array.length; i++) {
            if (array[i] != 0.0)
                System.out.print(i + ":" + array[i] + "\t");
        }
        System.out.println();
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
