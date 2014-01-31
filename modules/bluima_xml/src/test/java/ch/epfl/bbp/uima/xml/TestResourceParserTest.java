package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;

import org.junit.Test;

import ch.epfl.bbp.uima.xml.testresources.Output;
import ch.epfl.bbp.uima.xml.testresources.Result;
import ch.epfl.bbp.uima.xml.testresources.UnitTests;

public class TestResourceParserTest {

    @Test
    public void test() throws Exception {

	InputStream is = this.getClass().getResourceAsStream(
		"/test_resources/example.xml");

	UnitTests tests = new TestResourceParser().parse(is);

	assertEquals(4, tests.getResult().size());

	Result result = tests.getResult().get(0);
	assertEquals("40 nM", result.getInput().getValue());

	assertEquals(2, result.getOutput().size());

	Output output = result.getOutput().get(0);
	assertEquals("40", output.getValue());
	assertEquals("value", output.getId());
    }
}
