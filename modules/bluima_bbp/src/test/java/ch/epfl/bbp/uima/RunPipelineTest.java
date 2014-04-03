package ch.epfl.bbp.uima;

import static org.apache.commons.io.FileUtils.iterateFiles;
import static org.junit.Assert.assertTrue;
import static org.python.google.common.collect.Lists.newArrayList;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UIMAException;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.laucher.Pipeline;
import ch.epfl.bbp.uima.laucher.PipelineScriptParser;

/**
 * @author renaud.richardet@epfl.ch
 */
public class RunPipelineTest {
	private static Logger LOG = getLogger(RunPipeline.class);

	@Test
	public void testParseAllPipelines() throws Exception {
		Iterator<File> it = FileUtils.iterateFiles(RunPipeline.PIPELINES,
				new String[] { "pipeline" }, true);

		boolean justListThem = false; // to speed error fixing...
		List<ParseException> exeptions = newArrayList();

		while (it.hasNext()) {
			File pipeline = it.next();
			try {
				PipelineScriptParser.parse(pipeline);
			} catch (ParseException e) {
				if (e.getMessage().indexOf("$") > -1) {
					System.err.println("ERROR parsing, maybe because of args "
							+ pipeline.getPath() + " " + e.getMessage());
				} else {
					ParseException pe = new ParseException("ERROR parsing "
							+ pipeline.getPath() + " " + e.getMessage(),
							e.getErrorOffset());
					if (justListThem)
						exeptions.add(pe);
					else
						throw pe;
				}
			}
		}
		if (justListThem) {
			System.err.println("\nFAULTY PIPELINES");
			for (ParseException pe : exeptions) {
				System.err.println(pe);
			}
		}
	}

	@Test
	@Ignore
	// FIXME not passing yet
	public void testAllExamplePipelines() throws Exception {
		runPipeline(new File(BlueUimaHelper.EXAMPLE_ROOT));
	}

	@Test
	public void testAllTestsPipelines() throws Exception {
		runPipeline(new File(BlueUimaHelper.TESTS_ROOT));
	}

	private static void runPipeline(File pipelineRoot) throws ParseException,
			IOException, UIMAException {

		assertTrue(pipelineRoot.exists());

		Iterator<File> it = iterateFiles(pipelineRoot,
				new String[] { "pipeline" }, true);
		while (it.hasNext()) {
			File script = it.next();
			LOG.debug("running script {} >----------------------------",
					script.getName());
			Pipeline pipeline = PipelineScriptParser.parse(script);
			pipeline.run();
		}
	}
}
