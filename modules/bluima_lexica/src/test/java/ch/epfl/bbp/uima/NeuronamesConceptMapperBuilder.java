package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;
import ch.epfl.bbp.uima.xml.NeuronamesXmlParser;

/**
 * Generates neuronames.xml UIMA lexicon file from Neuronames xml ontology.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class NeuronamesConceptMapperBuilder {

    public static void main(String[] args) throws Exception {

        Map<String, Concept> parse = NeuronamesXmlParser.parse();

        List<Concept> c = newArrayList(parse.values());

        Collections.sort(c, new Comparator<Concept>() {
            @Override
            public int compare(Concept c1, Concept c2) {
                return new Integer(c1.getId()).compareTo(new Integer(c2.getId()));
            }
        });

        writeConceptFile(new File(LEXICA_ROOT
                + "resources/brainregions/neuronames.xml"), c);
    }
}
