package ch.epfl.bbp.uima.xml;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.xml.archivearticle3.Article;

public class ArchiveArticleParserTest {
    Logger LOG = LoggerFactory.getLogger(ArchiveArticleParserTest.class);

    @Test
    public void test() throws Exception {

	// http://www.ncbi.nlm.nih.gov/pmc/articles/PMC2586251/?tool=pubmed

	Article article = new PmcNxmlParser().parse(ResourceHelper
		.getInputStream("src/test/resources/pmc/33147.xml"));
	// "src/test/resources/pmc/e-67-o1696.xml"));
	// "src/test/resources/99846.nxml"));

	String text = PmcNxmlHelper.extractText(article);
	LOG.debug(text);
    }
}
