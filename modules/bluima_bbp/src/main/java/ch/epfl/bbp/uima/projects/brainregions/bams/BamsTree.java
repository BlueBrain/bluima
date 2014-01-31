package ch.epfl.bbp.uima.projects.brainregions.bams;

import static org.python.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import ch.epfl.bbp.io.SVReader.TSVReader;

public class BamsTree {

    static Map<Integer, Set<Integer>> elems = newHashMap();
    static Map<Integer, String> names = newHashMap();

    public static void addE(int id, int parent) {
        Set<Integer> s = (elems.containsKey(parent)) ? elems.get(parent)
                : new TreeSet<Integer>();
        s.add(id);
        elems.put(parent, s);
    }

    static int maxInd;

    public static void mprint(int id, int indent) {

        maxInd = Math.max(maxInd, indent);

        System.out.println(StringUtils.repeat(" ", indent) + id + "\t"
                + names.get(id));
        // print("<tr class=\"level_{} \" id=\"11\"><td>{}</td></tr>", indent,
        // id);
        if (elems.containsKey(id)) {
            for (Integer child : elems.get(id)) {
                mprint(child, indent + 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {

        addE(20, 1);
        addE(21, 1);
        addE(134, 1);
        addE(143, 1);
        addE(886, 1);
        addE(969, 1);

        String f = "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/tree/swanson-98.parts_s_cleaned.tsv";
        for (List<String> e : new TSVReader(new File(f), true)) {
            int id = Integer.parseInt(e.get(0));
            int parent = Integer.parseInt(e.get(3));
            addE(id, parent);

            // name
            String name = e.get(1);
            String abrev = e.get(2);
            names.put(id, name + "\t" + abrev);
        }

        mprint(1, 0);
        System.out.println("maxInd" + maxInd);
    }
}
