package ch.epfl.bbp.uima.pubmed;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import ch.epfl.bbp.io.SVReader.TSVReader;

public class FetchSrikantReferencesScript {
    public static void main(String[] args) throws Exception {

        PubmedSearch ps = new PubmedSearch();
        CitationParser citationParser = new CitationParser();

        Iterator<List<String>> source = new TSVReader(new File(
                "src/test/resources/ColumnSynapticDynamics v1.refs"), false)
                .iterator();

        PrintWriter sink = new PrintWriter(new File("out.tsv"));

        while (source.hasNext()) {
            List<String> entry = source.next();
            System.out.println("processing entry" + entry.get(0));

            sink.append(entry.get(0) + "\n"); // 1st is title
            for (int i = 1 /* skip 1st */; i < entry.size(); i++) {
                String reference = entry.get(i);
                sink.append(reference + "\t");
                System.out.println(">>processing ref " + reference);
                try {
                    Citation cit = citationParser.parse(reference);
                    if (cit != null) {
                        sink.append(cit.getTitle() + "\t");
                        sink.append(cit.getYear() + "\t");
                        List<Integer> pmIds = ps.searchIds(cit.getTitle());
                        // TODO if multiple matches, do string comp with title
                        if (pmIds.size() == 1) {
                            sink.append(pmIds.get(0) + "\t");
                        }
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
                sink.append("\n");
            }
            sink.append("\n");
            sink.flush();
        }
        sink.close();
    }
}
