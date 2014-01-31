package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;

import java.io.File;

import ch.epfl.bbp.io.FileExtensionFilter;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;
import ch.epfl.bbp.uima.xml.ConceptMapperParser;

public class AllBrainregionsSurfaceForms {

    public static void main(String[] args) throws Exception {

        File all = new File(LEXICA_ROOT + "resources/brainregions/");
        for (File cm : all.listFiles(new FileExtensionFilter("xml"))) {
            for (Concept c : ConceptMapperParser.parse(cm).values()) {
                for (String v : c.getVariants()) {
                    System.out.println(v);
                }
            }
        }
    }
}
