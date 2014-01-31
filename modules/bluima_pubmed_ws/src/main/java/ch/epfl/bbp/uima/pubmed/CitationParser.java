package ch.epfl.bbp.uima.pubmed;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Parses citations using Freecite webservice
 * 
 * @see http://freecite.library.brown.edu/welcome/api_instructions
 * @author renaud.richardet@epfl.ch
 * 
 */
// TODO parse multiple citations at a time
public class CitationParser {

    private HttpClient client;
    final static String url = "http://freecite.library.brown.edu/citations/create";

    public CitationParser() {
        client = new HttpClient();
    }

    public Citation parse(String citationStr) throws HttpException,
            IOException, ParserConfigurationException, SAXException {

        PostMethod method = new PostMethod(url);
        method.addRequestHeader("accept", "text/xml");
        method.addParameter("citation", citationStr);

        int statusCode = client.executeMethod(method);

        if (statusCode != -1) {
            InputStream in = method.getResponseBodyAsStream();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory
                    .newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(in);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName("citation");

            // for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                String isValid = element.getAttribute("valid");
                if (isValid.equals("true")) {
                    Citation citation = new Citation();

                    citation.setTitle(getValue("title", element));
                    citation.setYear(new Integer(getValue("year", element)));

                    NodeList authors = element.getElementsByTagName("author");
                    for (int j = 0; j < authors.getLength(); j++) {
                        Node author = authors.item(j);
                        citation.addAuthor(author.getTextContent());
                    }
                    return citation;
                }
            }
        }
        return null;
    }

    private static String getValue(String tag, Element element) {
        NodeList nodes = element.getElementsByTagName(tag).item(0)
                .getChildNodes();
        Node node = (Node) nodes.item(0);
        return node.getNodeValue();
    }
}
