package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.BlueUima.TEST_RESOURCES_PATH;
import static ch.epfl.bbp.uima.ElasticsearchHelper.ELASTICSEARCH_ROOT;
import static ch.epfl.bbp.uima.laucher.PipelineScriptParser.parse;

import java.io.File;

import org.junit.Ignore;
import org.junit.Test;

public class ElasticIndexerTest {

	@Test
	@Ignore
	// FIXME embed ES for testing?
	public void test() throws Exception {

		parse(
				new File(ELASTICSEARCH_ROOT + TEST_RESOURCES_PATH
						+ "pipelines/preproc.pipeline")).run();
	}
}
