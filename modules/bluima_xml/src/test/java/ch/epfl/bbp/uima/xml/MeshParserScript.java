package ch.epfl.bbp.uima.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.xml.MeshParser;
import ch.epfl.bbp.uima.xml.mesh.Concept;
import ch.epfl.bbp.uima.xml.mesh.DescriptorRecord;
import ch.epfl.bbp.uima.xml.mesh.DescriptorRecordSet;
import ch.epfl.bbp.uima.xml.mesh.Term;

/**
 * To generate the lexicon from MeSH
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class MeshParserScript {
    Logger LOG = LoggerFactory.getLogger(MeshParserScript.class);

    @Test
    @Ignore
    public void test() throws Exception {

	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
		"mesh_lexicon.tsv")));

	File f = ResourceHelper.getFile("xml/mesh/desc2012.xml");
	DescriptorRecordSet ds = new MeshParser().parse(f);

	for (DescriptorRecord dr : ds.getDescriptorRecord()) {

	    String descriptorUI = dr.getDescriptorUI();
	    String descriptorName = dr.getDescriptorName().getString();

	    LOG.debug(descriptorName + " [" + descriptorUI + "]");

	    for (Concept c : dr.getConceptList().getConcept()) {

		for (Term t : c.getTermList().getTerm()) {
		    LOG.debug("  " + t.getString());
		    writer.append(descriptorUI + "\t" + descriptorUI.charAt(0)
			    + "\tMeSH\t" + t.getString());
		    writer.newLine();
		}
	    }
	}
	writer.close();
    }
}
