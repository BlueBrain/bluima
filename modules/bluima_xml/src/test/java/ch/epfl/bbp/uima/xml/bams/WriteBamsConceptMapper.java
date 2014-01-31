package ch.epfl.bbp.uima.xml.bams;

import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.util.List;

import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

import com.google.common.collect.Sets;

/**
 * 
 * Write Bams ontology to UIMA conceptmapper format
 * 
 * @author renaud.richardet@epfl.ch
 */
public class WriteBamsConceptMapper {

    public static void main(String[] args) throws Exception {

        BamsOntology bo = parse();

        List<Concept> concepts = newArrayList();
        int i = 0;
        for (BrainPart br : bo.brainParts.values()) {
            concepts.add(new Concept(br.getName(), i++, Sets.newHashSet(br
                    .getName())));
        }
        writeConceptFile(new File("target/bams.xml"), concepts);
    }
}
