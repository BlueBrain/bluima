package ch.epfl.bbp.uima.xml;

import java.io.File;
import java.io.FileInputStream;

import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.XmlHelper;
import ch.epfl.bbp.uima.xml.archivearticle3.Article;
import ch.epfl.bbp.uima.xml.archivearticle3.Body;
import ch.epfl.bbp.uima.xml.archivearticle3.Sec;

public class PmcNxmlParserTest {

	@Test
	@Ignore
	public void testMatMet() throws Exception {

		Article article = new PmcNxmlParser().parse(new FileInputStream(
				new File(XmlHelper.XML_TEST_RESOURCES + "nxml/18446507.nxml")));

		Body body = article.getBody();
		if (body != null) {
			for (Sec sec : body.getSec()) {
				System.out.println(sec.getSecType()+"\t"+sec.getTitle().getContent().toString());
			}
		}
	}
}
