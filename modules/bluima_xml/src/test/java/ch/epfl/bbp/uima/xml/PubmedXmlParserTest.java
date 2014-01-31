package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;

import java.io.InputStream;
import java.util.List;

import org.junit.Test;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.xml.pubmed.MedlineCitation;
import ch.epfl.bbp.uima.xml.pubmed.PubmedArticle;

// TODO requires internet access...
public class PubmedXmlParserTest {

    @Test
    public void testParsePubmedXml() throws Exception {

        InputStream is = ResourceHelper
                .getInputStream("pubmed_abstracts/16205164.xml");
        PubmedArticle art = new PubmedXmlParser().parseAsArticle(is);

        assertEquals(
                "High rate of joint capsule matrix turnover in chronic human elbow contractures.",
                art.getMedlineCitation().getArticle().getArticleTitle()
                        .getvalue());
        assertEquals("16205164", art.getMedlineCitation().getPMID().getvalue());
    }

    @Test
    public void testParsePubmedXmls() throws Exception {

        InputStream is = ResourceHelper
                .getInputStream("pubmed_abstracts/medline13n0787.xml");
        List<MedlineCitation> arts = new PubmedXmlParser().parseAsArticles(is);
        assertEquals(3, arts.size());
        assertEquals(
                "An unusual clinical manifestation in a factor IX deficient patient: orbital haemorrhage without trauma.",
                arts.get(0).getArticle().getArticleTitle().getvalue());
        assertEquals("23419111", arts.get(0).getPMID().getvalue());
    }
}
