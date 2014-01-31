package ch.epfl.bbp.uima.pubmed.mesh;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Set;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.pubmed.PubmedSearch;

public class Fetch_NSIds_From_MeshList__Script {

    public static void main__(String[] args) throws Exception {

        TextFileWriter writer = new TextFileWriter(new File(
                "target/20130309_ns_mesh_ids.txt"));

        PubmedSearch searcher = new PubmedSearch();
        for (String q : new LineReader(new FileInputStream(
                "src/main/resources/mesh_onto.txt"))) {

            System.out.println("searching for " + q);
            for (Integer pmId : searcher.searchIds("" + q + "[mesh]")) {
                writer.addLine(pmId + "");
            }
            writer.flush();
        }
        writer.close();
        System.out.println("done :-)");
    }

    public static void main_(String[] args) throws Exception {

        PubmedSearch searcher = new PubmedSearch();
        for (String mesh : linesFrom(new FileInputStream(
                "src/main/resources/mesh_onto_curated.txt"))) {
            List<Integer> ids = searcher.searchIds("" + mesh + "[mesh]");
            System.out.println(mesh + "\t" + ids.size());
        }
        System.out.println("done :-)");
    }

    public static void main(String[] args) throws Exception {

        PubmedSearch searcher = new PubmedSearch();
        Set<Integer> ids = newHashSet();
        for (String mesh : linesFrom(new FileInputStream(
                "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/mesh_terms_from_bbp-people/hist_lt_10_curated.txt"))) {
            ids.addAll(searcher.searchIds("" + mesh.split("\t")[1] + "[mesh]"));
            System.out.println(mesh + "\t" + ids.size());
        }

        System.out.println("done :-)");
        for (Integer i : ids) {
            System.out.println(i);
        }
    }
}
