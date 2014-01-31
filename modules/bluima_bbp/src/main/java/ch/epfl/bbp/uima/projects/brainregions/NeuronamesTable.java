package ch.epfl.bbp.uima.projects.brainregions;

import static java.lang.Integer.parseInt;
import static org.python.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import ch.epfl.bbp.io.SVReader.CSVReader;
import ch.epfl.bbp.io.SVReader.TSVReader;
import ch.epfl.bbp.io.TextFileWriter;

public class NeuronamesTable {

    private static final int MIN_CNT = 100;

    public static void main(String[] args) throws Exception {

        String base = "/Users/richarde/Desktop/BBP_experiments/23_extract_brainregions/relations/20130706_neuronames_cooccurences_400kpdfs/";

        // load names
        Map<Integer, String> names = newHashMap();
        for (List<String> l : new CSVReader(
                new File(base + "brainregions.csv"), true)) {
            names.put(parseInt(l.get(0)), l.get(1));
        }
        System.out.println(names.size() + " names");

        // load counts
        // k1:from k2:to val:cnt
        TwoDHashMap<Integer, Integer, Integer> counts = new TwoDHashMap<Integer, Integer, Integer>();
        // to be able to iterate
        TreeSet<Integer> ids = new TreeSet<Integer>();
        for (List<String> line : new TSVReader(new File(base
                + "cooccurences.tsv"), true)) {
            // from to cnt
            int from = parseInt(line.get(0));
            int to = parseInt(line.get(1));
            int cnt = parseInt(line.get(2));
            if (cnt > MIN_CNT) {
                counts.set(from, to, cnt);
                ids.add(from);
                ids.add(to);
            }
        }
        System.out.println(ids.size() + " unique regions");

        // build table
        TextFileWriter w = new TextFileWriter("target/neuronames.html");
        w.addLine("<!DOCTYPE html><html><head><title></title></head><body><table>");
        // header
        w.addLine("<tr><td>.</td>");
        for (Integer header : ids) {
            w.addLine("<td>" + names.get(header) + "</td>");
        }
        w.addLine("</tr>");
        // body
        for (Integer from : ids) {
            w.addLine("<tr><td>" + names.get(from) + "</td>");

            for (Integer to : ids) {
                int val = counts.containsKey(from, to) ? counts.get(from, to)
                        : 0;
                
                w.addLine("<td>" + val + "</td>");
            }
            w.addLine("</tr>");
        }

        w.addLine("</table></body></html>");
        w.close();
    }
}
