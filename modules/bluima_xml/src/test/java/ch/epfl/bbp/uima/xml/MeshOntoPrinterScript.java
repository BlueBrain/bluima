package ch.epfl.bbp.uima.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import ch.epfl.bbp.ResourceHelper;
import ch.epfl.bbp.uima.xml.MeshParser;
import ch.epfl.bbp.uima.xml.mesh.DescriptorRecord;
import ch.epfl.bbp.uima.xml.mesh.DescriptorRecordSet;
import ch.epfl.bbp.uima.xml.mesh.TreeNumber;

/**
 * 
 * from http://en.wikipedia.org/wiki/List_of_MeSH_codes
 * 
 * <pre>
 *     A - Anatomy
 *         A08 --- nervous system
 * 
 *     C - Diseases
 *         C10 --- nervous system diseases
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class MeshOntoPrinterScript {

    public static void main(String[] args) throws Exception {

	BufferedWriter writer = new BufferedWriter(new FileWriter(new File(
		"mesh_onto.txt")));

	File f = ResourceHelper.getFile("xml/mesh/desc2012.xml");
	DescriptorRecordSet ds = new MeshParser().parse(f);

	for (DescriptorRecord dr : ds.getDescriptorRecord()) {

	    String descriptorUI = dr.getDescriptorUI();
	    String descriptorName = dr.getDescriptorName().getString();

	    // System.out.println(descriptorName + " [" + descriptorUI + "]");

	    if (dr.getTreeNumberList() != null) {

		for (TreeNumber tn : dr.getTreeNumberList().getTreeNumber()) {

		    String tnv = tn.getvalue();

		    if (tnv.startsWith("A08") || tnv.startsWith("C10")) {

			System.out.println("--" + descriptorName + " " + tnv);
			writer.append(descriptorName);
			writer.newLine();
		    }
		}
	    }

	}
	writer.close();
    }
}
