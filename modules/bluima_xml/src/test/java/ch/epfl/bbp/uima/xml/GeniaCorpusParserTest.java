package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.xml.genia.Article;
import ch.epfl.bbp.uima.xml.genia.Set;

public class GeniaCorpusParserTest {
    Logger LOG = LoggerFactory.getLogger(GeniaCorpusParserTest.class);

    @Test
    public void test() throws Exception {

	InputStream is = this.getClass().getResourceAsStream(
		"/GENIAcorpus3.02.xml/test_sample.xml");

	Set parse = new GeniaCorpusParser().parse(is);

	assertEquals(1, parse.getArticle().size());

	Article a = parse.getArticle().get(0);
	assertNotNull(a);
    }
}
