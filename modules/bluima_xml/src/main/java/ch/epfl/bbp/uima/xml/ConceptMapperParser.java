package ch.epfl.bbp.uima.xml;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.conceptMapper.ConceptMapper;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ch.epfl.bbp.uima.utils.ConceptFileWriter;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

/**
 * 
 * Parses a lexicon in {@link ConceptMapper} xml file format. Useful when
 * further processing a lexicon.
 * 
 * @see ConceptFileWriter
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ConceptMapperParser {

    /**
     * @param file
     *            xml atlas file
     * @return a Map {k: id, v: {@link Concept} }
     */
    public static Map<String, Concept> parse(File f) throws JDOMException,
            IOException {

        Map<String, Concept> concepts = newHashMap();

        InputStream corpusIs = new FileInputStream(f);

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(corpusIs);
        Element rootNode = doc.getRootElement();

        Iterator<Element> tokens = rootNode.getChildren().iterator();
        while (tokens.hasNext()) {
            Element token = tokens.next();

            // <token canonical="primary cell culture cell" ref_id="CL:0000001">
            // <variant base="primary cell culture cell" />

            String id = token.getAttributeValue("ref_id");
            String canonical = token.getAttributeValue("canonical");

            Set<String> variantStrings = newHashSet();
            List<Element> variants = token.getChildren();
            for (Element variant : variants) {
                variantStrings.add(variant.getAttributeValue("base").trim());
            }
            concepts.put(id, new Concept(canonical, id, variantStrings));
        }
        checkArgument(concepts.size() > 0, "empty concepts!");
        return concepts;
    }
}
