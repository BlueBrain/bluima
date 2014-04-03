package ch.epfl.bbp.uima.xml.bams;

import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static com.google.common.collect.Lists.newArrayList;

import java.io.File;
import java.util.List;
import java.util.Map;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

import com.google.common.collect.Maps;
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
            concepts.add(new Concept(br.getName(), "bams:" + i++, Sets
                    .newHashSet(br.getName())));
        }
        writeConceptFile(new File("target/bams2013.xml"), concepts);

        // WRITE ENTITES ////////////////////////////////////////////////////
        // mapping ids
        Map<BrainPart, Integer> mappingIds = Maps.newHashMap();
        int id = 0;
        for (BrainPart bp : bo.brainParts.values()) {
            mappingIds.put(bp, id++);
        }

        TextFileWriter entW = new TextFileWriter("target/bams_entites.tsv");
        entW.addLine("id\tname\tabbr\tis_part_of\tequals");
        for (BrainPart br : bo.brainParts.values()) {

            String partOf = "", equivalent = "";
            if (br.getIsPartOf() != null) {
                partOf = mappingIds.get(br.getIsPartOf()) + "";
            }
            if (br.getEquivalentClass() != null) {
                equivalent = mappingIds.get(br.getEquivalentClass()) + "";
            }
            entW.addLine(mappingIds.get(br) + "\t" + br.getName() + "\t"
                    + br.getAbbr() + "\t" + partOf + "\t" + equivalent);
        }
        entW.close();

        // WRITE RELS ////////////////////////////////////////////////////
        TextFileWriter relsW = new TextFileWriter("target/bams_rels.tsv");
        relsW.addLine("sender\treceiver\tstrength\ttechnique");

        for (Connection conn : bo.connections) {
            relsW.addLine(mappingIds.get(conn.getSender()) + "\t" + //
                    mappingIds.get(conn.getReceiver()) + "\t" + //
                    conn.getStrength().name() + "\t" + //
                    conn.getTechnique().replaceAll("[\t\n\r]", " ")//
            );
        }
        relsW.close();
    }
}
