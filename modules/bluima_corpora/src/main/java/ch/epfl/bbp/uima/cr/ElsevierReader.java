package ch.epfl.bbp.uima.cr;

import static org.jdom2.Namespace.getNamespace;
import static org.jdom2.filter.Filters.element;
import static org.jdom2.filter.Filters.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.Text;
import org.jdom2.filter.AbstractFilter;
import org.jdom2.filter.Filter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;

import com.google.common.collect.Sets;

import de.julielab.jules.types.Header;

/**
 * Parsing Elsevier XML for text representation
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = {})
public class ElsevierReader extends AbstractFileReader {

    private ElsevierParser parser = new ElsevierParser();

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        fileExtensionFilter = "xml"; // overwrite
        super.initialize(context);
    }

    public void getNext(JCas jCas) throws IOException, CollectionException {
        File next = fileIterator.next();
        try {
            parser.parse(next, jCas);
        } catch (Exception e) {
            LOG.warn(e.getMessage());
            if (hasNext()) {
                getNext(jCas);
            } else {
                throw new IOException("could not parse " + next.getName(), e);
            }
        }
    }

    public static class ElsevierParser {
        SAXBuilder builder = new SAXBuilder();
        XMLOutputter xo = new XMLOutputter();
        Namespace def = getNamespace("def",
                "http://www.elsevier.com/xml/svapi/article/dtd");
        Namespace bk = getNamespace("bk", "http://www.elsevier.com/xml/bk/dtd");
        Namespace cals = getNamespace("cals",
                "http://www.elsevier.com/xml/common/cals/dtd");
        Namespace ce = getNamespace("ce",
                "http://www.elsevier.com/xml/common/dtd");
        Namespace dc = getNamespace("dc", "http://purl.org/dc/elements/1.1/");
        Namespace ja = getNamespace("ja", "http://www.elsevier.com/xml/ja/dtd");
        Namespace mml = getNamespace("mml",
                "http://www.w3.org/1998/Math/MathML");
        Namespace prism = getNamespace("prism",
                "http://prismstandard.org/namespaces/basic/2.0/");
        Namespace sa = getNamespace("sa",
                "http://www.elsevier.com/xml/common/struct-aff/dtd");
        Namespace sb_ = getNamespace("sb",
                "http://www.elsevier.com/xml/common/struct-bib/dtd");
        Namespace tb = getNamespace("tb",
                "http://www.elsevier.com/xml/common/table/dtd");
        Namespace xlink = getNamespace("xlink", "http://www.w3.org/1999/xlink");
        Namespace xocs = getNamespace("xocs",
                "http://www.elsevier.com/xml/xocs/dtd");
        Namespace xsi = getNamespace("xsi",
                "http://www.w3.org/2001/XMLSchema-instance");

        public ElsevierParser() {
            xo.setFormat(Format.getRawFormat());
        }

        public void parse(File xmlFile, JCas jCas)
                throws FileNotFoundException, JDOMException, IOException {

            final StringBuilder sb = new StringBuilder();
            final Document doc = builder.build(new FileInputStream(xmlFile));
            Element rootNode = doc.getRootElement();
            final Filter<Text> filter = new MyTextFilter("cross-ref",
                    "cross-refs", "label");

            // title
            XPathExpression<Text> titleXPath = XPathFactory.instance().compile(
                    "//dc:title/text()", filter, null, def, bk, cals, ce, dc,
                    ja, mml, prism, sa, sb_, tb, xlink, xocs, xsi);
            final String title = titleXPath.evaluateFirst(rootNode)
                    .getTextNormalize();
            sb.append(title + ". ");

            // doc type
            XPathExpression<Text> doctypeXPath = XPathFactory.instance()
                    .compile("//xocs:meta/xocs:document-type/text()", filter,
                            null, def, bk, cals, ce, dc, ja, mml, prism, sa,
                            sb_, tb, xlink, xocs, xsi);
            String doctype = doctypeXPath.evaluateFirst(rootNode)
                    .getTextNormalize();
            if (!doctype.equals("article")) {
                throw new IOException("wrong doctype: " + doctype);
            }

            // scopus-id
            XPathExpression<Text> scopusidXPath = XPathFactory.instance()
                    .compile("//def:scopus-id/text()", text(), null, def);
            String scopusid = scopusidXPath.evaluateFirst(rootNode)
                    .getTextNormalize();

            // pmId
            String pmid;
            try {
                XPathExpression<Text> pmidXPath = XPathFactory.instance()
                        .compile("//def:pubmed-id/text()", text(), null, def);
                pmid = pmidXPath.evaluateFirst(rootNode).getTextNormalize();
            } catch (Exception e) {
                pmid = scopusid;
                LOG.debug("no pubmed id for {}, falling back on scopus id {}",
                        xmlFile.getName(), scopusid);
            }
            LOG.trace("processing pmId {} file {}", pmid, xmlFile.getName());

            // abstract
            XPathExpression<Element> abstractXPath = XPathFactory
                    .instance()
                    .compile(
                            "//xocs:doc/xocs:serial-item/ja:article/ja:head/ce:abstract/child::*",
                            element(), null, def, bk, cals, ce, dc, ja, mml,
                            prism, sa, sb_, tb, xlink, xocs, xsi);
            for (Element section : abstractXPath.evaluate(rootNode)) {
                for (Content c : section.getDescendants(filter)) {

                    String parentName = ((Element) c.getParent()).getName();
                    String txt = xo.outputString((Text) c)
                            .replaceAll("[\r\n]", " ").replaceAll(" +", " ");

                    sb.append(txt);
                    if (parentName.equals("section-title")) {
                        sb.append(". ");
                    }
                }
            }

            // body
            XPathExpression<Element> sectionsXPath = XPathFactory
                    .instance()
                    .compile(
                            "//xocs:doc/xocs:serial-item/ja:article/ja:body/ce:sections/ce:section",
                            element(), null, bk, cals, ce, dc, ja, mml, prism,
                            sa, sb_, tb, xlink, xocs, xsi);
            for (Element section : sectionsXPath.evaluate(rootNode)) {
                for (Content c : section.getDescendants(text())) {

                    String parentName = ((Element) c.getParent()).getName();
                    String txt = xo.outputString((Text) c)
                            .replaceAll("[\r\n]", " ").replaceAll(" +", " ");

                    if (!parentName.equals("cross-ref")
                            && !parentName.equals("cross-refs")) {
                        sb.append(txt);
                    }
                    if (parentName.equals("section-title")) {
                        sb.append(". ");
                    }
                }
            }

            jCas.setDocumentText(sb.toString() //
                    .replaceAll(" ?\\( ? ?\\)", "") // parenth. from biblio
                    .replaceAll("  +", " ").trim());
            Header h = new Header(jCas);
            h.setDocId(pmid);
            h.setTitle(title);
            h.addToIndexes();
        }
    }

    /** Filters {@link Text}s whose parent equals elementsToFilter */
    static class MyTextFilter extends AbstractFilter<Text> {
        private static final long serialVersionUID = 200L;
        private Set<String> elementsToFilter;

        public MyTextFilter(String... elementsToFilter) {
            this.elementsToFilter = Sets.newHashSet(elementsToFilter);
        }

        @Override
        public Text filter(Object content) {
            if (content instanceof Text) {
                final Text txt = (Text) content;
                final String parentName = ((Element) txt.getParent()).getName();
                if (elementsToFilter.contains(parentName)) {
                    return null;
                } else {
                    return txt;
                }
            }
            return null;
        }
    }
}
