package ch.epfl.bbp.uima.xml;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import ch.epfl.bbp.uima.XmlHelper;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

import com.google.common.collect.Sets;

/**
 * @author renaud.richardet@epfl.ch
 * 
 * 
 *         <pre>
 * <neuroName xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
 *     <concept brainInfoID="1" cNIDType="h" cNID="-2063754382" standardName="superior frontopolar gyrus" standardAcronym="FPGS">
 *         <brainInfoURL>http://braininfo.rprc.washington.edu/centraldirectory.aspx?ID=1</brainInfoURL>
 *         <synonyms>
 *             <synonym synonymLanguage="English" pubMedHits="0">superior frontopolar gyrus</synonym>
 *             <synonym synonymLanguage="English" pubMedHits="0">superior transverse frontopolar gyrus</synonym>
 *             <synonym synonymLanguage="Latin" pubMedHits="0">Gyrus frontopolaris transversus superior</synonym>
 *         </synonyms>
 * </pre>
 */
public class NeuronamesXmlParser {

	public static Map<String, Concept> parse() throws JDOMException,
			IOException {
		return parse(new File(XmlHelper.XML_RESOURCES
				+ "neuronames/NeuroNames.xml"));
	}

	public static Map<String, Concept> parse(File f) throws JDOMException,
			IOException {

		Map<String, Concept> concepts = newHashMap();

		InputStream corpusIs = new FileInputStream(f);

		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(corpusIs);
		Element rootNode = doc.getRootElement();

		Iterator<Element> cs = rootNode.getChildren().iterator();
		while (cs.hasNext()) {
			Element concept = cs.next();

			String id = concept.getAttributeValue("brainInfoID");
			String canonical = concept.getAttributeValue("standardName");
			canonical = canonical.replaceAll("\\(.*\\)$", "").trim();

			Set<String> variantStrings = Sets.newHashSet();

			List<Element> variants = concept.getChild("synonyms").getChildren();
			for (Element variant : variants) {
				if (variant.getAttribute("synonymLanguage").getValue()
						.matches("Latin|English")) {

					variantStrings.add(variant.getText()
							.replaceAll("\\(.*\\)$", "").trim());
				}
			}
			concepts.put(canonical, new Concept(canonical, id, variantStrings));
		}
		checkArgument(concepts.size() > 0, "empty concepts!");
		return concepts;
	}
}
