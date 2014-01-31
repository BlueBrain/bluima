package ch.epfl.bbp.uima.projects.brainregions;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static java.util.regex.Pattern.compile;
import static org.apache.commons.io.FileUtils.iterateFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * 
 * Needed for BrainNER, as it finds BR as words (not ids)
 * 
 * in: disparate entries from slurm<br>
 * out: a mapping from brainregion_name to id; the LOAD DATA files for
 * {@link AggregateCooccurences2}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AggregateCooccurences {

    static String base = "/Users/richarde/Desktop/20130515_malletNerOnPubmedAbstracts";

    static final Pattern p = compile("^(\\d+) (.+) (\\d+) (\\d+) (.+) (\\d+) (\\d+)$");

    public static void main(String[] args) throws IOException {

        Map<String, Integer> dict = newHashMap();

        for (String fileName : //
        newArrayList("br_abstract", "br_sentence")) {

            LoadDataFileWriter writer = new LoadDataFileWriter(new File(
                    fileName + "_aggregated"));

            Iterator<?> files = iterateFiles(new File(base), null, true);
            while (files.hasNext()) {
                File f = (File) files.next();
                if (f.getName().equals(fileName + ".load_data.txt")) {
                    System.out.println("process " + f.getName());

                    for (String load_line : new LineReader(new FileInputStream(
                            f))) {
                        // System.out.println("sss " + load_line);

                        Matcher m = p.matcher(load_line);
                        m.find();
                        int pmid = Integer.parseInt(m.group(1));
                        String r1 = m.group(2);
                        int r1s = Integer.parseInt(m.group(3));
                        int r1e = Integer.parseInt(m.group(4));
                        String r2 = m.group(5);
                        int r2s = Integer.parseInt(m.group(6));
                        int r2e = Integer.parseInt(m.group(7));
                        checkNotNull(r1);
                        checkNotNull(r2);
                        checkArgument(pmid > 0);
                        checkArgument(r1s > -1);
                        checkArgument(r1e > 0);
                        checkArgument(r2s > -1);
                        checkArgument(r2e > 0);

                        int r1Id = getId(r1, dict);
                        int r2Id = getId(r2, dict);
                        if (r1Id == r2Id) // skip if same
                            continue;

                        // respect id order
                        if (r1Id < r2Id)
                            writer.addLoadLine(new Object[] { //
                            pmid, r1Id, r1s, r1e, r2Id, r2s, r2e });
                        else
                            writer.addLoadLine(new Object[] { //
                            pmid, r2Id, r2s, r2e, r1Id, r1s, r1e });
                    }
                }
            }
            writer.close();
        }

        TextFileWriter w = new TextFileWriter("dict");
        for (Entry<String, Integer> e : dict.entrySet()) {
            w.addLine(e.getValue() + "\t" + e.getKey());
        }
        w.close();
    }

    private static int getId(String s, Map<String, Integer> dict) {
        if (dict.containsKey(s))
            return dict.get(s);
        else {
            int newId = dict.size();
            dict.put(s, newId);
            return newId;
        }
    }
}
