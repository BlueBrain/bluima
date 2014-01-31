package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.xml.testcase.Result;
import ch.epfl.bbp.uima.xml.testcase.TestSuiteCorpus;

public class TestSuiteCorpusParserTest {

    @Test
    public void test() throws Exception {

	InputStream is = ResourceHelper.getInputStream("testcases/example.xml");
	TestSuiteCorpus testSuite = new TestSuiteCorpusParser().parse(is);

	assertEquals(2, testSuite.getResult().size());

	Result result = testSuite.getResult().get(0);
	assertEquals(2, result.getOutput().size());
	assertEquals("Using this calibration", result.getInput()
		.getRawContent().subSequence(0, 22));
	// TODO more tests
    }
}
