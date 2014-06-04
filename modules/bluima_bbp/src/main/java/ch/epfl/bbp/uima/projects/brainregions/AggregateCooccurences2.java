package ch.epfl.bbp.uima.projects.brainregions;

import static java.lang.Integer.parseInt;
import static java.lang.Math.log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * 
 * in: LOAD_DATA from ExtractBrainregionsCoocurrences <br>
 * out: aggregated LOAD DATA
 * 
 * `pubmed_id`,`region_1_id`,`region_1_start`,`region_1_end`,`region_2_id`,`
 * region_2_start`,`region_2_end`
 * 
 * @author richarde
 */
public class AggregateCooccurences2 {

    // static String level = "abstract";
    // static String level = "sentence";
    // static String base = "20130503_ExtractBrainregionsCoocurrences";
    // static String base = "20130504_ExtractBrainregionsCoocurrences";
    // static String in = base + "/br_" + level + ".load_data.txt";
    // static String out = base + "/br_" + level + "_aggregate.load_data.txt";

    static String in = "/nfs4/bbp.epfl.ch/scratch/richarde/20130704_brainregion_ft_400k_3ners/neuronames/";
    static String out = in + "/aggregate.load_data.txt";

    public static void main(String[] args) throws IOException {

        // read file and aggregate

        TwoDHashMap<Integer, Integer, Histogram<Integer>> aggregate = new TwoDHashMap<Integer, Integer, Histogram<Integer>>();

        for (int batchId = 0; batchId < 767; batchId++) {
            System.out.println("batch " + batchId);

            int cnt = 0;
            for (String line : new LineReader(new FileInputStream(//
                    in + batchId + "/br_sentence.load_data.txt"))) {

                // parse
                String[] splits = line.split("\t");
                int region_1_id = parseInt(splits[1]);
                int region_2_id = parseInt(splits[4]);
                int region_1_start = parseInt(splits[2]);
                int region_1_end = parseInt(splits[3]);
                int region_2_start = parseInt(splits[5]);
                int region_2_end = parseInt(splits[6]);

                int distance;
                if (region_1_start < region_2_start) {
                    distance = region_2_start - region_1_end;
                } else
                    distance = region_1_start - region_2_end;

                // region ordered by lowest id first
                int firstRegionId, secondRegionId;
                if (region_1_id < region_2_id) {
                    firstRegionId = region_1_id;
                    secondRegionId = region_2_id;
                } else {
                    firstRegionId = region_2_id;
                    secondRegionId = region_1_id;
                }

                // getOrElse create hist, then update
                Histogram<Integer> hist;
                if (aggregate.containsKey(firstRegionId, secondRegionId))
                    hist = aggregate.get(firstRegionId, secondRegionId);
                else
                    hist = new Histogram<Integer>();

                hist.add(distance);

                aggregate.set(firstRegionId, secondRegionId, hist);
                cnt++;
            }
            System.out.println("  aggregated " + cnt + " entries");
        }

        // write aggregate
        System.out.println("writing...");
        // long entries = 0, totalCount = 0;
        LoadDataFileWriter writer = new LoadDataFileWriter(new File(out));
        for (Integer firstRegionId : aggregate.keySet()) {
            for (Entry<Integer, Histogram<Integer>> entry : aggregate
                    .getInnermap(firstRegionId).entrySet()) {
                Histogram<Integer> hist = entry.getValue();

                // write score
                double score = getScore(hist.getMap());
                writer.addLoadLine(new Object[] { firstRegionId,
                        entry.getKey(), hist.totalSize(), score });

                // entries++;
                // for (Entry<Integer, Long> histE : hist.getMap().entrySet()) {
                // totalCount+= histE.getKey()*h
                // }
            }
        }
        writer.close();
    }

    /**
     * @param map
     *            <distance, counts>
     * @return the weighted average of the distance
     */
    private static double getScore(Map<Integer, Long> map) {
        double factorCount = 0;
        for (Entry<Integer, Long> entry : map.entrySet()) {
            int distance = entry.getKey();
            long counts = entry.getValue();
            factorCount += counts / log(distance + 1);
        }
        return factorCount;
    }
}
