package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import ch.epfl.bbp.io.SVReader.CSVReader;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

/**
 * Generates UIMA lexicon files from csv files for 4 different brain region
 * taxonomies.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BrainLexiconConceptMapperBuilder {

    public static void main(String[] args) throws IOException {

        String ROOT_FILES = LEXICA_ROOT + "src/test/resources/NeuroNames/";

        for (String lexica : new String[] { "Dong", "Hof", "Paxinos", "Swanson" }) {

            List<Concept> concepts = newArrayList();

            File f = new File(ROOT_FILES + lexica + ".csv");
            checkArgument(f.exists());

            for (List<String> line : new CSVReader(f, true)) {
                String name = line.get(0);
                String nnId = line.get(4);
                concepts.add(new Concept(//
                        name, lexica + ":" + nnId, newHashSet(name)));
            }

            writeConceptFile(new File(LEXICA_ROOT + "resources/brainregions/"
                    + lexica.toLowerCase() + ".xml"), concepts);

            System.out.println("done " + lexica);
        }
    }
}
