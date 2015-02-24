package ch.epfl.bbp.uima.pdf.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FloatArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.AbbreviationExpander;
import ch.epfl.bbp.uima.AbbreviationExpander.Abbrev;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.cr.AbstractFileReader;
import ch.epfl.bbp.uima.pdf.BBlock;
import ch.epfl.bbp.uima.pdf.BDocument;
import ch.epfl.bbp.uima.pdf.BLine;
import ch.epfl.bbp.uima.pdf.BlockHandler;
import ch.epfl.bbp.uima.pdf.cleanup.HyphenRemover;
import ch.epfl.bbp.uima.types.DataTable;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.types.DocumentLine;
import ch.epfl.bbp.uima.types.DocumentPage;

import com.snowtide.pdf.PDFTextStream;

import de.julielab.jules.types.Header;
import de.julielab.jules.types.Section;
import edu.psu.seersuite.extractors.tableextractor.extraction.PdfBoxParser;
import edu.psu.seersuite.extractors.tableextractor.extraction.TableExtractor;
import edu.psu.seersuite.extractors.tableextractor.model.Table;

/**
 * Extracts the text of a PDF file using Snowtide's PDFTextSteam
 * (http://www.snowtide.com/) and optionally the tables found in the PDF using
 * TableSeer (http://sourceforge.net/projects/tableseer/).
 * 
 * LATER implement Snowtide parser in TableSeer
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
public class PdfCollectionReader extends AbstractFileReader {
    private static Logger LOG = LoggerFactory
            .getLogger(PdfCollectionReader.class);

    public static final String COMPONENT_ID = PdfCollectionReader.class
            .getName();

    public static final String PARAM_EXTRACT_TABLES = "extractTables";
    @ConfigurationParameter(name = PARAM_EXTRACT_TABLES, defaultValue = "false", //
    description = "whether to extract tables")
    private boolean extractTables;

    public static final String PARAM_EXPAND_ABBREVIATIONS = "expandAbbrevs";
    @ConfigurationParameter(name = PARAM_EXPAND_ABBREVIATIONS, defaultValue = "false", //
    description = "whether to expand Abbreviations")
    private boolean expandAbbrevs;

    public static final String PARAM_EXTRACT_REFERENCES = "extractReferences";
    @ConfigurationParameter(name = PARAM_EXTRACT_REFERENCES, defaultValue = "false", //
    description = "whether to extract references")
    private boolean extractReferences;

    private TableExtractor tableExtractor;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        fileExtensionFilter = "pdf";// overwrite filter
        super.initialize(context);

        try {
            if (extractTables) {
                tableExtractor = new TableExtractor();
                tableExtractor.setParser(new PdfBoxParser());
            }

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void getNext(JCas jcas) throws IOException, CollectionException {
        File f = fileIterator.next();
        Header header = new Header(jcas);
        // .* removes the tmp part
        header.setDocId(f.getName().replaceAll("\\.pdf.*", ""));
        header.setSource(f.getAbsolutePath());
        header.addToIndexes();

        PDFTextStream pdf = new PDFTextStream(f);
        BlockHandler blueHandler = new BlockHandler();
        pdf.pipe(blueHandler);
        pdf.close();

        extractText(jcas, blueHandler.getDoc(), header.getDocId(),
                expandAbbrevs);

        if (extractTables)
            extractTables(tableExtractor, f, jcas);

        // printHtml(jcas, new File("target/" + header.getDocId() + ".html"));
    }

    /**
     * Adds block annotations
     * 
     * @param docId
     * @param expandAbbrevs
     */
    public static void extractText(JCas jcas, BDocument bDocument,
            String docId, boolean expandAbbrevs) {

        StringBuffer sb = new StringBuffer("");
        int currBegin = 0, currPageId = 0;

        DocumentPage currPage = new DocumentPage(jcas);
        currPage.setBegin(0);

        Set<Abbrev> abbrevs = null;
        if (expandAbbrevs) {
            // pass over the text to find abbrevs
            StringBuffer abbrevSb = new StringBuffer("");
            for (BBlock block : bDocument.getBlocks()) {
                abbrevSb.append(cleanupText(block, docId));
            }
            abbrevs = AbbreviationExpander.getAbbrevs(abbrevSb.toString());
        }

        for (BBlock block : bDocument.getBlocks()) {

            String text = cleanupText(block, docId);
            if (expandAbbrevs) {
                text = AbbreviationExpander.expand(text, abbrevs);
            }

            // add lines
            int currLineBegin = currBegin;
            for (BLine bLine : block.getLines()) {
                String lineText = bLine.getText();
                int lineLength = lineText.replaceAll(" +", " ").length();
                // LATER watch out: begin,end is ~approximate~
                // TODO especially because of abbrev expansion!
                DocumentLine annLine = new DocumentLine(jcas, currLineBegin,
                        currLineBegin + lineLength);
                currLineBegin += lineLength;
                annLine.setX(bLine.getRegion().x);
                annLine.setY(bLine.getRegion().y);
                annLine.setHeight(bLine.getRegion().height);
                annLine.setWidth(bLine.getRegion().width);
                annLine.setPageId(block.getPageId());
                annLine.setBlock(block.getId());
                annLine.setLineText(lineText); // LATER a workaround
                annLine.setBeginnings(new FloatArray(jcas, bLine
                        .getBeginnings().size()));
                for (int i = 0; i < bLine.getBeginnings().size(); i++) {
                    annLine.setBeginnings(i, bLine.getBeginnings().get(i));
                }
                annLine.setEndings(new FloatArray(jcas, bLine.getEndings()
                        .size()));
                for (int i = 0; i < bLine.getEndings().size(); i++) {
                    annLine.setEndings(i, bLine.getEndings().get(i));
                }
                annLine.addToIndexes();
            }

            // add block
            DocumentBlock annBlock = new DocumentBlock(jcas, currBegin,
                    currBegin + text.length());
            currBegin += text.length();
            sb.append(text);

            annBlock.setElementId(block.getId());
            annBlock.setX(block.getRegion().x);
            annBlock.setY(block.getRegion().y);
            annBlock.setHeight(block.getRegion().height);
            annBlock.setWidth(block.getRegion().width);
            annBlock.setHasBold(block.isHasBold());
            annBlock.setHasManyFontsizes(block.isHasManyFontsizes());
            annBlock.setMedianFontsize(block.getMedianFontsize());
            annBlock.setPageId(block.getPageId());
            annBlock.addToIndexes();

            // update page
            if (block.getPageId() > currPageId) {
                currPage.setEnd(currBegin);
                currPage.setPageId(currPageId);
                currPage.addToIndexes();

                currPage = new DocumentPage(jcas);
                currPage.setBegin(currBegin);
                currPageId = block.getPageId();
            }
        }

        jcas.setDocumentText(sb.toString());
    }

    private static String cleanupText(BBlock block, String docId) {

        String text = block.getText().trim();

        // Normalize (ligatures, accents)
        text = Normalizer.normalize(text, Form.NFKC);

        // Hyphens
        text = HyphenRemover.dehyphenate(text, docId);

        // Line feeds
        text = text.trim().replaceAll("[\\r\\n]+", " ");

        // Normalize quotes
        text = text.replaceAll("''", "\"");
        text = text.replaceAll("``", "\"");

        // Mult spaces
        text = text.trim().replaceAll(" +", " ");

        // important when serializing to xmi
        text = StringUtils.stripNonValidXMLCharacters(text);

        return text + "\r\n";
    }

    static void extractTables(TableExtractor extractor, File f, JCas jcas) {
        try {
            ArrayList<Table> tables = extractor.extract(f, null);
            if (tables != null) {
                for (Table table : tables) {

                    DataTable dt = new DataTable(jcas);
                    dt.setTableId(table.getOrder());
                    dt.setPageNumber(table.getPageNumber());
                    // dt.setRowCount(table.getRowNumber());
                    dt.setRowCount(table.getTableBody().size());
                    dt.setColumnCount(table.getColumnNumber());
                    dt.setCaption(table.getCaption());
                    dt.setReferenceText(table.getRefTextList());

                    ArrayList<String> tHeadings = table.getHeading();
                    dt.setHeadings(new StringArray(jcas, tHeadings.size()));
                    for (int i = 0; i < tHeadings.size(); i++) {
                        dt.setHeadings(i, tHeadings.get(i));
                    }

                    ArrayList<String> tBody = table.getTableBody();
                    dt.setBody(new StringArray(jcas, tBody.size()));
                    for (int i = 0; i < tBody.size(); i++) {
                        dt.setBody(i, tBody.get(i));
                    }
                    dt.addToIndexes();
                }
            }
        } catch (Throwable t) {
            LOG.warn("cannot extract tables from {}: {}", f.getAbsolutePath(),
                    t);
        }
    }

    private static final Pattern REFERENCES = Pattern
            .compile("^(?:REFERENCES|References)");

    static boolean extractReferencesNaively(JCas jCas) {

        // match potential sections
        List<Section> sections = newArrayList();
        Matcher m = REFERENCES.matcher(jCas.getDocumentText());
        while (m.find()) {
            Section section = new Section(jCas, m.start(), m.end());
            section.setSectionType(BlueUima.SECTION_TYPE_REFERENCES);
            sections.add(section);
        }

        if (sections.size() == 1) {
            sections.get(0).addToIndexes();
            return true;
        }
        return false;
    }

    public static void printHtml(JCas jcas, File file) {
        try {
            Document dom = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder().newDocument();

            Element htmlElt = dom.createElement("html");
            dom.appendChild(htmlElt);
            Element head = dom.createElement("head");
            htmlElt.appendChild(head);

            addElem(head, "meta", "http-equiv", "Content-Type", "content",
                    "text/html; charset=utf-8");
            addElem(head,
                    "script",
                    "src",
                    "http://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js",
                    "type", "text/javascript");

            for (DocumentBlock b : JCasUtil.select(jcas, DocumentBlock.class)) {

                String color = "blue";

                // references
                if (!selectCovered(jcas, Section.class, b).isEmpty()) {
                    color = "red";
                }

                int maxY = (int) (b.getY() + b.getHeight());
                Map<String, String> attrs = newHashMap();
                double top = (b.getPageId() + 1) * 800 - maxY;
                attrs.put("style", "font-size: 10px;position:absolute;top:"
                        + (int) top + ";left:" + (int) b.getX() + ";height:"
                        + (int) b.getHeight() + ";width:" + (int) b.getWidth()
                        + ";border:1px solid " + color);
                htmlElt.appendChild(buildTextElt(dom, "span",
                        b.getCoveredText(), attrs));
            }

            // http://stackoverflow.com/a/4166237/125617
            // Element bf = dom.createElement("script");
            // bf.setAttribute("src", "jquery.boxfit.js");
            // bf.setAttribute("type", "text/javascript");
            // htmlElt.appendChild(bf);

            // Element boxfit = dom.createElement("script");
            // boxfit.appendChild(dom
            // .createTextNode("//<![CDATA[ \n"
            // + "$(document).ready(function () {\n"
            // + "$('.boxfit').each(function ( i, box ) {\n"
            // + "    var width = $( box ).width(),\n"
            // // +
            // "        html = '<span style=\\\"white-space:nowrap\\\"></span>',\n"
            // + "        html = '<span></span>',\n"
            // + "        line = $( box ).wrapInner( html ).children()[ 0 ],\n"
            // + "        n = 100;\n"
            // + "    $( box ).css( 'font-size', n );\n"
            // + "    while ( $( line ).width() > width ) {\n"
            // + "        $( box ).css( 'font-size', --n );\n"
            // + "    }\n"
            // + "    $( box ).text( $( line ).text() );\n"
            // + "});\n"//
            // + "});//]]"));
            // boxfit.setAttribute("type", "text/javascript");
            // htmlElt.appendChild(boxfit);

            Writer out = new OutputStreamWriter(new FileOutputStream(file));
            serializeXMLDocument(dom, out);
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Writes the given Document to the given Writer using a no-op XSL transformation.
     */
    public static void serializeXMLDocument (Document doc, Writer output) throws TransformerException {
        // serialize XML document using identity transformation
        Transformer trans = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(output);
        trans.transform(source, result);
    }

    private static Element addElem(Element addTo, String name,
            String... attribs) {
        Element el = addTo.getOwnerDocument().createElement(name);
        for (int i = 0; i < attribs.length; i = i + 2) {
            el.setAttribute(attribs[i], attribs[i + 1]);
        }
        addTo.appendChild(el);
        return el;
    }

    private static Element buildTextElt(Document doc, String elttype,
            String contents, Map<String, String> attributes) {
        Element te = doc.createElement(elttype);
        if (contents != null && contents.length() > 0)
            te.appendChild(doc.createTextNode(contents));
        if (attributes != null) {
            for (String a : attributes.keySet()) {
                te.setAttribute(a, attributes.get(a));
            }
        }
        return te;
    }

}
