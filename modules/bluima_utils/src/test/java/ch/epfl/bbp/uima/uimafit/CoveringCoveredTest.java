package ch.epfl.bbp.uima.uimafit;

import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;
import static org.apache.uima.fit.util.JCasUtil.selectCovering;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Punctuation;
import de.julielab.jules.types.Chemical;
import de.julielab.jules.types.Protein;
import de.julielab.jules.types.Token;

public class CoveringCoveredTest {

	@Test
	public void test() throws Exception {

		JCas jCas = UimaTests.getTestCas("aaaaabbbbbccccc");

		Token t = new Token(jCas, 5, 10);
		t.addToIndexes();
		assertEquals("bbbbb", t.getCoveredText());

		// perfect cover

		Protein p = new Protein(jCas, 5, 10);
		p.addToIndexes();

		assertEquals(1, selectCovered(Protein.class, t).size());
		assertEquals(1, selectCovering(jCas, Protein.class, 5, 10).size());

		// over cover

		DictTerm d = new DictTerm(jCas, 4, 10);
		d.addToIndexes();

		assertEquals(0, selectCovered(DictTerm.class, t).size());
		assertEquals(1, selectCovering(jCas, DictTerm.class, 5, 10).size());
		assertEquals(0,
				selectCovered(org.apache.uima.conceptMapper.DictTerm.class, t)
						.size());
		assertEquals(
				1,
				selectCovering(jCas,
						org.apache.uima.conceptMapper.DictTerm.class, 5, 10)
						.size());

		// under cover

		Chemical c = new Chemical(jCas, 6, 9);
		c.addToIndexes();

		assertEquals(1, selectCovered(Chemical.class, t).size());
		assertEquals(0, selectCovering(jCas, Chemical.class, 5, 10).size());

		// chevauchant

		Punctuation pt = new Punctuation(jCas, 3, 9);
		pt.addToIndexes();

		assertEquals(0, selectCovered(Punctuation.class, t).size());
		assertEquals(0, selectCovering(jCas, Punctuation.class, 5, 10).size());
	}
}
