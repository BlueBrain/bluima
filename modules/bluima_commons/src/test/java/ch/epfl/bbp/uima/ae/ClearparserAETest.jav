package ch.epfl.bbp.uima.ae;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.cleartk.syntax.dependency.clear.ClearParser;
import org.junit.Test;

import ch.epfl.bbp.utils.ResourceHelper;

public class ClearparserAETest {

    @Test
    public void test() throws Exception {

	String model = ResourceHelper.getFile(
		"models/clearparser_en_dep_bionlp.jar").getAbsolutePath();

	AnalysisEngineDescription parserAE = ClearParser.getDescription(model);
    }
}
