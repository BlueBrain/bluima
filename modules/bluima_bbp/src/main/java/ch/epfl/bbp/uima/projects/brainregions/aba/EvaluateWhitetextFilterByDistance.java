package ch.epfl.bbp.uima.projects.brainregions.aba;

import static ch.epfl.bbp.uima.BlueUimaHelper.SCRIPT_ROOT;

import java.io.File;

import ch.epfl.bbp.uima.laucher.Pipeline;
import ch.epfl.bbp.uima.laucher.PipelineScriptParser;

import com.google.common.collect.Lists;

public class EvaluateWhitetextFilterByDistance {

    public static void main(String[] args) throws Exception {

       // for (int dist = 222220; dist < 222222200; dist++) {

        int dist = 10000000;
            Pipeline p = PipelineScriptParser
                    .parse(new File(
                            SCRIPT_ROOT
                                    + "projects/extract_brainregions/20131128_evaluate_cooc-extractors_agains_whitetext/20131128_filter_by_distance.pipeline"),
                            Lists.newArrayList(dist + ""));
            p.run();
        //}
    }

}
