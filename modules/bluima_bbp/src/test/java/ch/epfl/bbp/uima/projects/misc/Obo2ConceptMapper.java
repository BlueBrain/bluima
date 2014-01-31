package ch.epfl.bbp.uima.projects.misc;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import ch.epfl.bbp.io.TextFileWriter;

import uk.ac.cam.ch.wwmm.oscar.obo.OBOOntology;
import uk.ac.cam.ch.wwmm.oscar.obo.OntologyTerm;

public class Obo2ConceptMapper {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws Exception {

        OBOOntology oboOntology = new OBOOntology();
        oboOntology.read(new File(
                "/Volumes/HDD2/ren_data/data_hdd/ontologies/OBO/sao.obo"));

        for (Entry<String, OntologyTerm> a : oboOntology.getTerms().entrySet()) {
            System.out.println(a);
        }
    }

}
