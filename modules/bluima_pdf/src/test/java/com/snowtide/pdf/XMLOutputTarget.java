package com.snowtide.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pdfts.examples.XMLFormExport;

import com.snowtide.pdf.OutputHandler;
import com.snowtide.pdf.PDFTextStream;
import com.snowtide.pdf.PDFTextStreamConfig;
import com.snowtide.pdf.Page;
import com.snowtide.pdf.layout.Block;
import com.snowtide.pdf.layout.Region;
import com.snowtide.pdf.layout.Table;
import com.snowtide.pdf.layout.TextUnit;
import com.snowtide.util.logging.Log;
import com.snowtide.util.logging.LoggingRegistry;

/**
 * <p>
 * This class is an example {@link OutputHandler} implementation that builds up
 * a DOM XML model of extracted PDF content.
 * </p>
 * <p>
 * The full source code for this class is included in every PDFTextStream
 * distribution.
 * </p>
 * <p>
 * The aim of this {@link OutputHandler} implementation is to output a valid XML
 * document containing the text extracted from the PDF document, as well as
 * interesting structural information (i.e. where {@link Page} and {@link Block}
 * structures begin and end), as well as what text ranges are outputted using
 * bolded, italicized, and underlined fonts. This kind of information might be
 * particularly interesting to systems that perform some kind of semantic
 * analysis of a document's structure and significant textual passages (such as
 * a search engine or data mining system).
 * </p>
 * 
 * @version ©2004-2009 Snowtide Informatics Systems, Inc.
 */
public class XMLOutputTarget extends OutputHandler {
    private static final Log log = LoggingRegistry
            .getLog(XMLOutputTarget.class);

    private static final String ELT_BOLD = "bold";
    private static final String ELT_UNDERLINED = "underlined";
    private static final String ELT_ITALIC = "italic";
    private static final String ELT_TEXT = "text";
    private static final String ELT_STRUCKTHROUGH = "strikethrough";

    private final Document doc;
    private final Element root;

    // we keep this around so we know how far to walk up the DOM in closeText()
    private Element textEltParent;
    private Element currentElt;

    private String linebreak = PDFTextStreamConfig.getDefaultConfig()
            .getLinebreakString();
    private boolean isBold = false;
    private boolean isItalic = false;
    private boolean isUnderlined = false;
    private boolean isStruckThrough = false;

    private StringBuffer whitespace = new StringBuffer(512);

    /**
     * Creates a new <code>XMLOutputTarget</code>.
     * 
     * @throws IOException
     *             if an error occurs initializing a new DOM document
     */
    public XMLOutputTarget() throws IOException {
        try {
            doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .newDocument();
            doc.appendChild(currentElt = root = doc
                    .createElement("pdftsExtract"));
        } catch (Exception e) {
            throw new IOException(
                    "Configuration error encountered while initializing XML facilities: "
                            + e.getMessage());
        }
    }

    /**
     * Returns the DOM Document that this <code>XMLOutputTarget</code> is
     * building.
     */
    public Document getXMLDocument() {
        return doc;
    }

    /**
     * Returns the XML built by this <code>XMLOutputTarget</code> as a
     * <code>String</code>.
     */
    public String getXMLAsString() throws IOException {
        StringWriter s = new StringWriter();
        try {
            XMLFormExport.serializeXMLDocument(doc, s);
        } catch (TransformerException e) {
            log.error(
                    "Error occurred serializing extracted PDF form data to XML",
                    e);
            throw new IOException(
                    "Error occurred serializing extracted PDF form data to XML: "
                            + e.getMessage());
        }
        return s.toString();
    }

    private Element newContext(String tag) {
        return currentElt = (Element) currentElt.appendChild(doc
                .createElement(tag));
    }

    private void closeContext(String tag) {
        if (currentElt == root)
            throw new IllegalStateException(
                    "Cannot close context; current element is the document root");
        if (tag != null && !tag.equals(currentElt.getNodeName()))
            throw new IllegalStateException(
                    "Cannot close context; current element `"
                            + currentElt.getNodeName()
                            + "' does not match expected tag name `" + tag
                            + "'");
        currentElt = (Element) currentElt.getParentNode();
    }

    private void closeThroughTextContext(String tag) {
        while (true) {
            if (currentElt == root)
                throw new IllegalStateException(
                        "Cannot close context; current element is the document root");
            if (currentElt.getNodeName().equals(ELT_TEXT))
                return;
            String nodeName = currentElt.getNodeName();
            currentElt = (Element) currentElt.getParentNode();
            if (nodeName.equals(tag))
                return;
        }
    }

    private boolean isStyleContextCurrent(String tag) {
        Element elt = currentElt;
        while (true) {
            if (elt == root || elt.getNodeName().equals(ELT_TEXT))
                return false;
            if (elt.getNodeName().equals(tag))
                return true;
            elt = (Element) elt.getParentNode();
        }
    }

    private void closeText() {
        if (textEltParent == null)
            return;
        while (currentElt != textEltParent) {
            closeContext(null);
        }

        textEltParent = null;
        isBold = isUnderlined = isStruckThrough = isItalic = false;
    }

    private Element openText() {
        if (textEltParent == null) {
            textEltParent = currentElt;
            return newContext(ELT_TEXT);
        } else {
            return currentElt;
        }
    }

    private void normalizeStyleElts(boolean bold, boolean italic,
            boolean underline, boolean struckThrough) {
        if (isBold && !bold)
            closeThroughTextContext(ELT_BOLD);
        if (isItalic && !italic)
            closeThroughTextContext(ELT_ITALIC);
        if (isUnderlined && !underline)
            closeThroughTextContext(ELT_UNDERLINED);
        if (isStruckThrough && !struckThrough)
            closeThroughTextContext(ELT_STRUCKTHROUGH);

        if (bold && (!isBold || !isStyleContextCurrent(ELT_BOLD)))
            newContext(ELT_BOLD);
        if (underline
                && (!isUnderlined || !isStyleContextCurrent(ELT_UNDERLINED)))
            newContext(ELT_UNDERLINED);
        if (struckThrough
                && (!isStruckThrough || !isStyleContextCurrent(ELT_STRUCKTHROUGH)))
            newContext(ELT_STRUCKTHROUGH);
        if (italic && (!isItalic || !isStyleContextCurrent(ELT_ITALIC)))
            newContext(ELT_ITALIC);

        isItalic = italic;
        isBold = bold;
        isUnderlined = underline;
        isStruckThrough = struckThrough;
    }

    public void textUnit(TextUnit tu) {
        openText();

        normalizeStyleElts(tu.getFont().isBold(), tu.getFont().isItalic(),
                tu.isUnderlined(), tu.isStruckThrough());

        String s;
        if (tu.getCharacterSequence() == null) {
            // no mapping, append direct character code conversion
            // skip all control characters -- they'll cause all sorts of
            // problems (readers signalling EOF early, etc)
            int cc = tu.getCharCode();
            if (cc < 32) {
                return;
            } else {
                s = Character.toString((char) cc);
            }
        } else {
            // found mapping, append mapped characters
            s = new String(tu.getCharacterSequence());
        }
        currentElt.appendChild(doc.createTextNode(s));
    }

    private static void writeCoordsAsAttrs(Region r, Element context) {
        context.setAttribute("xpos", Float.toString(r.xpos()));
        context.setAttribute("ypos", Float.toString(r.ypos()));
        context.setAttribute("width", Float.toString(r.width()));
        context.setAttribute("height", Float.toString(r.height()));
    }

    private String getStringOf(String s, int cnt) {
        whitespace.delete(0, whitespace.length());
        for (int i = 0; i < cnt; i++)
            whitespace.append(s);
        return whitespace.toString();
    }

    public void spaces(int spaceCnt) {
        openText().appendChild(doc.createTextNode(getStringOf(" ", spaceCnt)));
    }

    public void linebreaks(int linebreakCnt) {
        openText().appendChild(
                doc.createTextNode(getStringOf(linebreak, linebreakCnt)));
    }

    public void startBlock(Block block) {
        closeText();
        Element e = newContext("block");
        if (block instanceof Table)
            e.setAttribute("type", "table");
        writeCoordsAsAttrs(block, e);
    }

    public void endBlock(Block block) {
        closeText();
        closeContext("block");
    }

    public void startPDF(String pdfName, File pdfFile) {
        newContext("pdf").setAttribute("name", pdfName);
    }

    public void endPDF(String pdfName, File pdfFile) {
        closeText();
        closeContext("pdf");
    }

    public void startPage(Page page) {
        linebreak = page.getConfig().getLinebreakString();
        closeText();
        newContext("page").setAttribute("number",
                Integer.toString(page.getPageNumber()));
    }

    public void endPage(Page page) {
        closeText();
        closeContext("page");
    }

    /**
     * A main method suitable for using this class' functionality from the
     * command line. All of the command-line arguments will be taken to be paths
     * to input PDF documents; each PDF documents will be opened by
     * {@link PDFTextStream}, and its content piped through a
     * <code>XMLOutputTarget</code> instance. Each PDF's extracted content is
     * then written to a ".xml" file in the same directory as the input
     * document.
     */
    public static void main(String[] args) throws Exception {
        for (int i = 0; i < args.length; i++) {
            File src = new File(args[i]);
            if (!src.exists())
                System.out.println("No such file: " + args[i]);
            if (!src.canRead())
                System.out.println("Cannot read file (check permissions): "
                        + args[i]);
            File destFile = new File(args[i] + ".xml");

            PDFTextStream stream = new PDFTextStream(src);

            XMLOutputTarget tgt = new XMLOutputTarget();
            stream.pipe(tgt);

            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(destFile), "UTF-8");
            writer.write(tgt.getXMLAsString());
            writer.flush();
            writer.close();

            System.out.println("Wrote text extracted from " + args[i] + " to "
                    + destFile.getAbsolutePath() + " as UTF-8 encoded XML.");
        }
    }
}
