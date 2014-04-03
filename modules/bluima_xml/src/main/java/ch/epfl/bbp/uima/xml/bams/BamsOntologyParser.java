package ch.epfl.bbp.uima.xml.bams;

import static ch.epfl.bbp.uima.xml.bams.Connection.Strength.fromString;
import static java.lang.Math.max;
import static org.jdom2.Namespace.getNamespace;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;

import ch.epfl.bbp.uima.xml.bams.Reference.Type;

/**
 * Parser for BAMS ontology (in XML format)
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BamsOntologyParser {
    private static Logger LOG = getLogger(BamsOntologyParser.class);

    // TODO
    public static final File onto = new File(
            "/Volumes/HDD2/ren_data/data_hdd/ontologies/BAMS/bams_ontology_2013-07-08_04-54-43.xml");

    public final static String TYPE_CONNECTION = "http://brancusi1.usc.edu/RDF/connection";
    public final static String TYPE_BRAINPART = "http://brancusi1.usc.edu/RDF/brainPart";
    public final static String TYPE_HIERARCHY = "http://brancusi1.usc.edu/RDF/hierarchy";
    public final static String TYPE_TOPOLOGICALRELATION = "http://brancusi1.usc.edu/RDF/topologicalRelation";
    public final static String TYPE_REFERENCE = "http://brancusi1.usc.edu/RDF/reference";
    public final static String TYPE_NOMENCLATURE = "http://brancusi1.usc.edu/RDF/nomenclature";
    public final static String TYPE_COLLATOR = "http://brancusi1.usc.edu/RDF/collator";
    public final static String TYPE_GROSSCONSTITUENT = "http://brancusi1.usc.edu/RDF/grossConstituent";

    public final static Namespace OWL = getNamespace("http://www.w3.org/2002/07/owl#");
    public final static Namespace RDF = getNamespace("http://www.w3.org/1999/02/22-rdf-syntax-ns#");
    public final static Namespace BAMS = getNamespace("http://brancusi1.usc.edu/RDF/");

    enum Hierarchy {
        hasPart, equivalentClass, overlap, isContained, covers, disjointWith;
    }

    public static BamsOntology parse() throws JDOMException, IOException {
        return parse(onto);
    }

    public static BamsOntology parse(File ontology) throws JDOMException,
            IOException {

        BamsOntology bo = new BamsOntology();

        InputStream corpusIs = new FileInputStream(ontology);

        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(corpusIs);
        Element rootNode = doc.getRootElement();

        // REFERENCES ///////////////////////////////////////////
        List<Element> nodes = rootNode.getChildren("Description", RDF);
        Iterator<Element> rdfIt = nodes.iterator();
        while (rdfIt.hasNext()) {
            Element el = rdfIt.next();

            String type = el.getChild("type", RDF)
                    .getAttribute("resource", RDF).getValue();

            if (type.equals(TYPE_REFERENCE)) {
                String nodeId = el.getAttributeValue("nodeID", RDF);
                String author = getVal(el, "author");
                String booktitle = getVal(el, "booktitle");
                String name = getVal(el, "name");
                String number = getVal(el, "number");
                String pages = getVal(el, "pages");
                String title = getVal(el, "title");
                String url = getVal(el, "url");
                String volume = getVal(el, "volume");
                String workspace = getVal(el, "workspace");
                String year = getVal(el, "year");
                String bamsType = el.getChild("type", BAMS)
                        .getAttribute("resource", RDF).getValue();
                // System.out.println(bamsType + "\t" + url);

                bo.references.put(
                        nodeId,
                        new Reference().setNodeId(nodeId).setAuthor(author)
                                .setBooktitle(booktitle).setName(name)
                                .setNumber(number).setPages(pages)
                                .setTitle(title).setType(Type.fromNs(bamsType))
                                .setUrl(url).setVolume(volume)
                                .setWorkspace(workspace).setYear(year));
            }
        }

        // BRAINPART ///////////////////////////////////////////
        rdfIt = nodes.iterator();
        while (rdfIt.hasNext()) {
            Element el = rdfIt.next();

            String type = el.getChild("type", RDF)
                    .getAttribute("resource", RDF).getValue();

            if (type.equals(TYPE_BRAINPART)) {
                String id = el.getAttributeValue("about", RDF);
                String abbr = getVal(el, "abbreviation");
                String name = getVal(el, "name");
                String referenceId = el.getChild("reference", BAMS)
                        .getAttribute("nodeID", RDF).getValue();
                String grossConstituent = getVal(el, "grossConstituent");
                String description = getVal(el, "description");

                Reference reference = bo.references.get(referenceId);

                bo.brainParts.put(
                        id,
                        new BrainPart().setAbbr(abbr).setName(name)
                                .setDescription(description)
                                .setReference(reference)
                                .setGrossConstituent(grossConstituent));
            }
        }

        // HIERARCHY *and* TOPOLOGICAL RELATION ////////////////////////
        // 7175 hasPart
        // 707 equivalentClass
        // 149 overlap
        // 145 isContained
        // 7 covers
        // 6 disjointWith
        rdfIt = nodes.iterator();
        while (rdfIt.hasNext()) {
            Element el = rdfIt.next();

            String type = el.getChild("type", RDF)
                    .getAttribute("resource", RDF).getValue();

            if (type.equals(TYPE_HIERARCHY)
                    || type.equals(TYPE_TOPOLOGICALRELATION)) {

                String class1 = el.getChild("class1", BAMS)
                        .getAttribute("resource", RDF).getValue();
                String class2 = el.getChild("class2", BAMS)
                        .getAttribute("resource", RDF).getValue();

                String relRaw = el.getChild("OntologyProperty", OWL)
                        .getAttribute("resource", RDF).getValue();
                String rel = relRaw.substring(max(//
                        relRaw.lastIndexOf('/'), relRaw.lastIndexOf('#')) + 1);

                BrainPart reg1 = bo.brainParts.get(class1);
                BrainPart reg2 = bo.brainParts.get(class2);

                if (rel.equals("hasPart")) {
                    reg2.setIsPartOf(reg1);
                } else if (rel.equals("equivalentClass")) {
                    reg2.setEquivalentClass(reg1);
                }
                // TODO parse more rels
                // System.err.println(rel + "\t" + reg1.getName() + "\t"
                // + reg2.getName());
            }
        }

        // CONNECTION ///////////////////////////////////////////
        rdfIt = nodes.iterator();
        while (rdfIt.hasNext()) {
            Element el = rdfIt.next();

            String type = el.getChild("type", RDF)
                    .getAttribute("resource", RDF).getValue();

            if (type.equals(TYPE_CONNECTION)) {
                Element refN = el.getChild("reference", BAMS);
                String referenceId = (refN == null) ? null : refN.getAttribute(
                        "nodeID", RDF).getValue();
                String description = getVal(el, "description");
                String receiverId = el.getChild("receiver", BAMS)
                        .getAttribute("resource", RDF).getValue();
                String senderId = el.getChild("sender", BAMS)
                        .getAttribute("resource", RDF).getValue();
                String strength = getVal(el, "strength_label");
                String technique = getVal(el, "technique");

                Reference reference = bo.references.get(referenceId);
                BrainPart receiver = bo.brainParts.get(receiverId);
                BrainPart sender = bo.brainParts.get(senderId);

                sender.addReceiver(receiver);
                receiver.addSender(sender);

                bo.connections.add(new Connection().setReceiver(receiver)
                        .setTechnique(technique).setDescription(description)
                        .setReference(reference).setSender(sender)
                        .setStrength(fromString(strength)));
            }
        }

        LOG.debug(
                "Parsed BAMS ontology with {} brainparts, {} connections, {} references",
                new Object[] { bo.brainParts.size(), bo.connections.size(),
                        bo.references.size() });
        return bo;

    }

    private static String getVal(Element el, String nodeName) {
        try {
            return el.getChild(nodeName, BAMS).getValue();
        } catch (Exception e) {
            // System.out.println(nodeName+" "+e);
            return null;
        }
    }
}
