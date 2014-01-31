package ch.epfl.bbp.uima.projects.brainregions.bams;

import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.xml.bams.BamsOntologyParser.parse;
import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.xml.bams.BamsOntology;
import ch.epfl.bbp.uima.xml.bams.BrainPart;

public class FindSynonyms {

    public static void main(String[] args) throws Exception {

        JcasPipelineBuilder p = new JcasPipelineBuilder();
        p.add(getSentenceSplitter());
        p.add(getTokenizer());
        p.add(getConceptMapper("/onto_baseline/brainregion"));//
        // "bams/bams2013"));

        BamsOntology bamsOnto = parse();
        for (BrainPart bp : bamsOnto.getBrainParts().values()) {

            JCas jCas = getTestCas(bp.getName());
            p.process(jCas);

            // System.out.println(bp);
            boolean found = false;
            for (BrainRegionDictTerm br : select(jCas,
                    BrainRegionDictTerm.class)) {
                found = true;
                // System.out.println(" found: " + br.getCoveredText());
            }
            if (!found)
                System.err.println("not found\t" + bp);
        }
    }
}
