package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.Protein;

public class MoleculeFilterTest {

	@Test
	public void testNaHCO() throws Exception {
		testTemplate("I like the color of NaHCO.", "NaHCO");
	}

	@Test
	public void testCaCL() throws Exception {
		testTemplate("I like the color of CaCL.", "CaCL");
	}

	@Test
	public void testCaCl2() throws Exception {
		testTemplate("I like the color of CaCl2.", "CaCl2");
	}

	@Test
	public void testKC1() throws Exception {
		testTemplate("I like the color of KC1.", "KC1");
	}

	@Test
	public void testNa3VO4() throws Exception {
		testTemplate("I like the color pf Na3VO4.", "Na3VO4");
	}

	private void testTemplate(String testSentence, String testedNE)
			throws Exception {
		JCas cas = getTokenizedTestCas(testSentence);
		int begin = testSentence.indexOf(testedNE);
		assertNotEquals("tested NE not in the tested sentence", begin, -1);

		Protein protAnnot = new Protein(cas, begin, begin + testedNE.length());
		protAnnot.addToIndexes();
		assertTrue("error while annotating protein", exists(cas, Protein.class));

		runPipeline(cas, createEngineDescription(MoleculeFilter.class));

		assertFalse("molecule still annotated as protein",
				exists(cas, Protein.class));
	}
}
