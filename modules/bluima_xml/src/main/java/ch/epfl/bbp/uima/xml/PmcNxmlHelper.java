package ch.epfl.bbp.uima.xml;

import java.util.List;

import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.xml.archivearticle3.Abstract;
import ch.epfl.bbp.uima.xml.archivearticle3.Article;
import ch.epfl.bbp.uima.xml.archivearticle3.ArticleId;
import ch.epfl.bbp.uima.xml.archivearticle3.Body;
import ch.epfl.bbp.uima.xml.archivearticle3.Bold;
import ch.epfl.bbp.uima.xml.archivearticle3.Italic;
import ch.epfl.bbp.uima.xml.archivearticle3.Monospace;
import ch.epfl.bbp.uima.xml.archivearticle3.P;
import ch.epfl.bbp.uima.xml.archivearticle3.Sc;
import ch.epfl.bbp.uima.xml.archivearticle3.Sec;
import ch.epfl.bbp.uima.xml.archivearticle3.Sub;
import ch.epfl.bbp.uima.xml.archivearticle3.Sup;
import ch.epfl.bbp.uima.xml.archivearticle3.Title;
import ch.epfl.bbp.uima.xml.archivearticle3.Xref;
import de.julielab.jules.types.AbstractText;
import de.julielab.jules.types.Section;

/**
 * Helper for NXML archived articles from PMC (http://www.ncbi.nlm.nih.gov/pmc/)
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class PmcNxmlHelper {

    /**
     * Iterates recursively over all objects of this article, and tries to
     * extract its components (text, figures, tables, etc) into Annotations
     */
    public static void extract(Article article, JCas jCas) {

        StringBuilder sb = new StringBuilder();

        List<Abstract> abstracts = article.getFront().getArticleMeta()
                .getAbstract();
        int abstractStart = sb.length();
        for (Abstract abstrct : abstracts) {
            for (Sec sec : abstrct.getSec()) {
                processTextContent(sec.getAddressOrAlternativesOrArray(), sb,
                        true);
            }
        }
        AbstractText abstractA = new AbstractText(jCas, abstractStart,
                sb.length());
        abstractA.addToIndexes();
        sb.append('\n');

        Body body = article.getBody();
        if (body != null) {
            for (Sec sec : body.getSec()) {

                Section secA = new Section(jCas);
                secA.setBegin(sb.length());

                int titleStart = sb.length();
                Title title = sec.getTitle();
                if (title != null && title.getContent() != null) {
                    processTextContent(title.getContent(), sb, true);
                }
                de.julielab.jules.types.Title titleA = new de.julielab.jules.types.Title(
                        jCas, titleStart, sb.length());
                titleA.addToIndexes();
                sb.append('\n');

                processContent(sec.getAddressOrAlternativesOrArray(), sb, jCas);

                secA.setEnd(sb.length());
                secA.addToIndexes();
            }
        }
        jCas.setDocumentText(sb.toString());
    }

    private static void processContent(List<Object> contents, StringBuilder sb,
            JCas jCas) {
        // TODO
        processTextContent(contents, sb, true);
    }

    /**
     * Iterates recursively over all objects of this article, and tries to
     * extract its text
     * 
     * @param article
     * @return the extracted text from this article
     */
    public static String extractText(Article article) {

        StringBuilder sb = new StringBuilder();

        // extract abstract
        List<Abstract> abstracts = article.getFront().getArticleMeta()
                .getAbstract();
        for (Abstract abstrct : abstracts) {
            for (Sec sec : abstrct.getSec()) {
                processTextContent(sec.getAddressOrAlternativesOrArray(), sb,
                        true);
            }
        }
        sb.append('\n');

        // extract text
        Body body = article.getBody();
        if (body != null) {
            for (Sec sec : body.getSec()) {

                Title title = sec.getTitle();
                if (title != null && title.getContent() != null) {
                    processTextContent(title.getContent(), sb, true);
                    sb.append('\n');
                }
                processTextContent(sec.getAddressOrAlternativesOrArray(), sb,
                        false);
            }
        }
        return removeNoise(sb.toString());
    }

    /** removing noise, could be improved... */
    private static String removeNoise(String s) {
        return s.replace("   ", " ").replace("  ", " ")//
                // caused by omitting xref tags
                .replace(" , ", " ").replace(" ( )", "")//
                .replace("&#x000a0;", " ").replace(" ", " ")// nbsp
                .replace("[ – ]", " ").replace("[ ]", "");//
    }

    private static void processTextContent(List<Object> contents,
            StringBuilder sb, boolean addSpace) {

        for (Object o : contents) {
            if (o instanceof P) {
                P p = (P) o;
                processTextContent(p.getContent(), sb, true);
                sb.append('\n');

            } else if (o instanceof Italic) {
                Italic i = (Italic) o;
                processTextContent(i.getContent(), sb, true);
            } else if (o instanceof Bold) {
                Bold b = (Bold) o;
                processTextContent(b.getContent(), sb, true);
            } else if (o instanceof Monospace) {
                Monospace m = (Monospace) o;
                processTextContent(m.getContent(), sb, true);

            } else if (o instanceof Sup) {
                // TODO use UTF-8 superscript?
                // http://www.fileformat.info/info/unicode/char/2082/index.htm
                Sup s = (Sup) o;
                processTextContent(s.getContent(), sb, false);
            } else if (o instanceof Sub) {
                Sub s = (Sub) o;
                processTextContent(s.getContent(), sb, false);
            } else if (o instanceof Sc) {// SmallCapital
                Sc s = (Sc) o;
                processTextContent(s.getContent(), sb, false);

            } else if (o instanceof Xref) {
                // TODO skipping Xref for now
                // e.g. life-threatening infection<xref ref-type="bibr"
                // rid="B4">4</xref>-<xref ref-type="bibr" rid="B6">6</xref> and
                // have commonly
                // (in Yonsei_Med_J_2006_Dec_31_47(6)_782-786.tar.gz)
                // Xref x = (Xref) o;
                // processContent(x.getContent(), sb);

            } else if (o instanceof String) {
                sb.append(o);
                if (addSpace)
                    sb.append(' ');

            } else {

                // SupplementaryMaterial
                // NamedContent
                // TableWrap

                // FIXME
                // class ch.epfl.bbp.nlp.xml.archivearticle3.Sc
                // class ch.epfl.bbp.nlp.xml.archivearticle3.TableWrap
                // class ch.epfl.bbp.nlp.xml.archivearticle3.Fig
                // class ch.epfl.bbp.nlp.xml.archivearticle3.Sc
                // ch.epfl.bbp.nlp.xml.archivearticle3.Sup
                // class ch.epfl.bbp.nlp.xml.archivearticle3.ExtLink
                // ch.epfl.bbp.nlp.xml.archivearticle3.Bold
                // ch.epfl.bbp.nlp.xml.archivearticle3.ExtLink
                // ch.epfl.bbp.nlp.xml.archivearticle3.Fig
                // ch.epfl.bbp.nlp.xml.archivearticle3.InlineFormula
                // ch.epfl.bbp.nlp.xml.archivearticle3.Sc
                // ch.epfl.bbp.nlp.xml.archivearticle3.Sub
                // ch.epfl.bbp.nlp.xml.archivearticle3.Sup
                // ch.epfl.bbp.nlp.xml.archivearticle3.SupplementaryMaterial
                // ch.epfl.bbp.nlp.xml.archivearticle3.TableWrap

                // FIXME System.err.println("extraction not implemented for "
                // + o.getClass());
            }
        }
    }

    /**
     * @param article
     * @return the pubmed id of this article, or null
     */
    public static Integer extractPmid(Article article) {
        try {
            List<ArticleId> ids = article.getFront().getArticleMeta()
                    .getArticleId();
            for (ArticleId id : ids) {
                if (id.getPubIdType().equals("pmid")) {
                    return Integer.parseInt(id.getContent());
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    /**
     * @param article
     * @return the doi string of this article, or null
     */
    public static String extractDoi(Article article) {
        try {
            List<ArticleId> ids = article.getFront().getArticleMeta()
                    .getArticleId();
            for (ArticleId id : ids) {
                if (id.getPubIdType().equals("doi")) {
                    return id.getContent();
                }
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }

    public static String extractTitle(Article article) {// FIXME
        try {
            StringBuilder sb = new StringBuilder();
            List<Object> content = article.getFront().getArticleMeta()
                    .getTitleGroup().getArticleTitle().getContent();
        } catch (Exception e) {
            System.err.println(e);
        }
        return null;
    }
}