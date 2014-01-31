package ch.epfl.bbp.uima.projects.dca;

import static ch.epfl.bbp.io.LineReader.intsFrom;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.ArrayUtils.toObject;
import static org.apache.commons.lang.StringUtils.join;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.uima.UIMAException;

import ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator;
import ch.epfl.bbp.uima.laucher.Pipeline;

public class TopicsForLabPeoplePdfsNs {

    public static void main2(String[] args) throws Exception {

        String base = "/Users/richarde/Desktop/BBP_experiments/18_txt2PmId/people_stats/";
        List<String> users = newArrayList("dan");// "shruti", "srikanth",
        // "toypaper_zotero", "eilif", "martin_zotero", "henry");

        for (String user : users) {
            System.out.println("\n\n888888888\n" + user + "\n\n");

            File scriptF = new File(
                    "src/test/resources/copy_to_release/projects/dca/exploit_pubmed_ns.pipeline");

            String dcaModel = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/dca/20130309_preprocess_pubmed_ns.pipeline/dca";
            String pmIds = join(toObject(intsFrom(new FileInputStream(base
                    + user + "_pmids.txt"))), ",");
            String outDir = base + "20130310_" + user + "_ns2/";

            run(scriptF, dcaModel, pmIds, outDir);
        }
    }

    public static void main(String[] args) throws Exception {

        File scriptF = new File(
                "src/test/resources/copy_to_release/projects/dca/exploit_pubmed_ns.pipeline");

        String dcaModel = "/Volumes/HDD2/ren_data/dev_hdd/bluebrain/9_lda/dca/20130309_preprocess_pubmed_ns.pipeline/dca";
        String pmIds = "12, 14";
        String outDir = "/Users/richarde/20130311_dca_layer1";
        run(scriptF, dcaModel, pmIds, outDir);
    }

    private static void run(File scriptF, String dcaModel, String pmIds,
            String outDir) throws ParseException, IOException, UIMAException {

        new File(outDir).mkdirs();

        Pipeline p = parse(scriptF, newArrayList(dcaModel, pmIds, outDir));
        p.run();

        GarbageCollectorAnnotator.runGC();
    }
}
