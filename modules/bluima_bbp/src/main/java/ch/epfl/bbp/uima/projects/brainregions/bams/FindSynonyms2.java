package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.utils.ConceptFileWriter.writeConceptFile;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;
import ch.epfl.bbp.uima.xml.ConceptMapperParser;
import ch.epfl.bbp.uima.xml.bams.BamsOntology;
import ch.epfl.bbp.uima.xml.bams.BrainPart;
import ch.epfl.bbp.uima.xml.bams.Connection;

import com.google.common.collect.Sets;

public class FindSynonyms2 {

    public static void main(String[] args) throws Exception {

        Map<String, Concept> neuronames = ConceptMapperParser.parse(new File(
                LEXICA_ROOT + "resources/onto_baseline/brainregion.xml"));

        JcasPipelineBuilder p = new JcasPipelineBuilder();
        p.add(getSentenceSplitter());
        p.add(getTokenizer());
        p.add(getConceptMapper("/onto_baseline/brainregion"));//
        // "bams/bams2013"));

        BamsOntology bo = parse();

        // init freqs
        Map<BrainPart, Integer> freq = newHashMap();
        for (BrainPart bp : bo.getBrainParts().values()) {
            freq.put(bp, 0);
        }

        // count freqs
        for (Connection c : bo.getConnections()) {
            freq.put(c.getReceiver(), freq.get(c.getReceiver()) + 1);
            freq.put(c.getSender(), freq.get(c.getSender()) + 1);
        }

        List<Concept> newLexicon = newArrayList();

        for (Entry<BrainPart, Integer> bp : freq.entrySet()) {

            if (bp.getValue() == 0)
                continue;

            JCas jCas = getTestCas(bp.getKey().getName());
            p.process(jCas);

            // System.out.println(bp);
            BrainRegionDictTerm found = null;
            for (BrainRegionDictTerm br : select(jCas,
                    BrainRegionDictTerm.class)) {
                String txt = br.getCoveredText();

                if (txt.length() < (bp.getKey().getName().length() - 2))
                    continue;

                if (found == null)
                    found = br;
                else {
                    if (br.getCoveredText().length() > found.getCoveredText()
                            .length())
                        found = br;
                }
                // System.out.println(" found: " + br.getCoveredText());
            }
            // if (!found)
            // System.err.println("not found\t" + bp);

            String canonical = bp.getKey().getName();
            String id = bp.getKey().getName().replaceAll("\\W", "_");

            Set<String> variants = newHashSet(bp.getKey().getName());
            if (found != null) {
                variants = neuronames.get(found.getDictCanon()).getVariants();
            }

            newLexicon.add(new Concept(canonical, id, variants));
        }

        writeConceptFile(new File("target/bams2013_augmented.xml"), newLexicon);
    }
}
