package ch.epfl.bbp.uima.xml;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.xml.pubmed.Abstract;
import ch.epfl.bbp.uima.xml.pubmed.MedlineCitation;
import ch.epfl.bbp.uima.xml.pubmed.MedlineCitationSet;
import ch.epfl.bbp.uima.xml.pubmed.PubmedArticle;
import ch.epfl.bbp.uima.xml.pubmed.PubmedArticleSet;

public class PubmedXmlParser {
    Logger LOG = LoggerFactory.getLogger(PubmedXmlParser.class);

    private Unmarshaller unmarshaller;

    private static JAXBContext jcSing = null;

    private JAXBContext getSingleton() throws JAXBException {
        if (jcSing == null)
            jcSing = JAXBContext.newInstance(Abstract.class.getPackage()
                    .getName());
        return jcSing;
    }

    public PubmedXmlParser() throws JAXBException {
        unmarshaller = getSingleton().createUnmarshaller();
    }

    public PubmedArticleSet parse(InputStream is) throws FileNotFoundException,
            JAXBException {

        try {
            return (PubmedArticleSet) unmarshaller.unmarshal(is);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    //
    // public List<MedlineCitation> parse2(File f) throws FileNotFoundException,
    // JAXBException {
    // List<MedlineCitation> res = new ArrayList<MedlineCitation>();
    //
    // BufferedInputStream fis = new BufferedInputStream(
    // new FileInputStream(f));
    // try {
    // Object o = unmarshaller.unmarshal(fis);
    // if (o instanceof PubmedArticleSet) {
    // PubmedArticleSet p = (PubmedArticleSet) o;
    // for (Object os : p.getPubmedArticleOrPubmedBookArticle()) {
    // if (os instanceof PubmedArticle) {
    // PubmedArticle art = (PubmedArticle) os;
    // res.add(art.getMedlineCitation());
    // }
    // }
    // } else if (o instanceof MedlineCitationSet) {
    // MedlineCitationSet m = (MedlineCitationSet) o;
    // for (MedlineCitation mc : m.getMedlineCitation()) {
    // res.add(mc);
    // }
    // }
    //
    // } finally {
    // try {
    // fis.close();
    // } catch (IOException e) {// nope
    // }
    // System.gc();
    // System.gc();
    // System.gc();
    // }
    // return res;
    // }

    public PubmedArticle parseAsArticle(InputStream is) {
        try {
            PubmedArticleSet parse = parse(is);
            return (PubmedArticle) parse.getPubmedArticleOrPubmedBookArticle()
                    .get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /** Parse Medline gzipped archives */
    public List<MedlineCitation> parseAsArticles(InputStream is) {
        try {
            MedlineCitationSet parse = (MedlineCitationSet) unmarshaller
                    .unmarshal(is);
            return parse.getMedlineCitation();
        } catch (Exception e) {
            LOG.warn("could not parse article ", e);
            return null;
        } finally {
            IOUtils.closeQuietly(is);
        }
    }
}
