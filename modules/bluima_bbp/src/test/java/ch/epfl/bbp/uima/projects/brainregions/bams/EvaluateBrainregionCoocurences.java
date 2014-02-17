package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.BlueUimaHelper.PROJECTS_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;
import static org.python.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;

import ch.epfl.bbp.uima.RunPipeline;

public class EvaluateBrainregionCoocurences {

	private static Logger LOG = getLogger(RunPipeline.class);

	public static void main(String[] a) {

		String root = PROJECTS_ROOT
				+ "/extract_brainregions/20140210_eval_extractors/20140210_eval_";

		for (int i = 1; i <= 10; i++) {

			List<String> args = newArrayList("" + i, "target/model.zip");

			try {
				parse(new File(root + "eval_trigger.pipeline"), args).run();

				parse(new File(root + "train_SLK.pipeline"), args).run();

				parse(new File(root + "eval_SLK.pipeline"), args).run();
			} catch (Exception e1) {
				LOG.error("", e1);
			}
		}
	}
}
