package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.BrainRegion;
import de.julielab.jules.types.Sentence;

/**
 * For {@link BrainRegion}
 * 
 * <code>cd to 20130503_ExtractBrainregionsCoocurrences</code><br>
 * <code>cd . (if you ran it again...)</code><br>
 * <code>mysql -uroot</code><br>
 * <code>use brainregions_relations</code><br>
 * <code>LOAD DATA LOCAL INFILE 'br_abstract.load_data.txt' INTO TABLE relations FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (`pubmed_id`,`region_1_name`,`region_1_start`,`region_1_end`,`region_2_name`,`region_2_start`,`region_2_end`);</code>
 * <br>
 * 
 * @author renaud.richardet@epfl.ch
 */
// makes things simpler to handle a single file
@Deprecated
@OperationalProperties(multipleDeploymentAllowed = false)
public class ExtractBrainregionsCoocurrences2 extends
        ExtractBrainregionsCoocurrences {
    private static Logger LOG = LoggerFactory
            .getLogger(ExtractBrainregionsCoocurrences2.class);

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        // collect collocations at DOCUMENT level
        if (documentLevel) {
            Collection<BrainRegion> br = select(jCas, BrainRegion.class);
            for (Pair<BrainRegion, BrainRegion> pair : combinaisonPairs2(br)) {
                documentLevelWriter.addLoadLine((Object[]) getLoadline(pair,
                        pmId));
                brDocLevel++;
            }
        }

        // collect collocations at SENTENCE level
        for (Sentence s : JCasUtil.select(jCas, Sentence.class)) {

            List<BrainRegion> brInSentence = selectCovered(jCas,
                    BrainRegion.class, s);
            for (Pair<BrainRegion, BrainRegion> pair : combinaisonPairs2(brInSentence)) {
                // LOG.debug("{}  ++ {}", pair.getKey().getCoveredText(), pair
                //       .getValue().getCoveredText());
                sentenceLevelWriter.addLoadLine((Object[]) getLoadline(pair,
                        pmId));
                brSentenceLevel++;
            }
        }

        // FLUSH once in a while
        if (++docCnt % 100000 == 0) {
            try {
                LOG.debug("flushing after docCnt   " + docCnt);
                LOG.debug("brDocLevel " + brDocLevel);
                LOG.debug("brSentenceLevel " + brSentenceLevel);

                if (documentLevel)
                    documentLevelWriter.flush();
                sentenceLevelWriter.flush();
            } catch (IOException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    /**
     * `pubmed_id`,`region_1_name,`region_1_start`,`region_1_end`,`region_2_name
     * `,` region_2_start`,`region_2_end`
     */
    private String[] getLoadline(Pair<BrainRegion, BrainRegion> pair, int pmId) {

        BrainRegion br1 = pair.getKey();
        BrainRegion br2 = pair.getValue();

        return new String[] {
                pmId + "",//
                br1.getCoveredText().replaceAll("(\\r|\\n|\\t)", ""),
                br1.getBegin() + "",
                br1.getEnd() + "",//
                br2.getCoveredText().replaceAll("(\\r|\\n|\\t)", ""),
                br2.getBegin() + "", br2.getEnd() + "" };
    }

    /** @return all simple 2-combinaisons */
    public static List<Pair<BrainRegion, BrainRegion>> combinaisonPairs2(
            Collection<BrainRegion> br) {

        List<Pair<BrainRegion, BrainRegion>> pairs = newArrayList();
        // we need at least 2 elems to find pairs
        if (br == null || br.size() < 2)
            return pairs;

        // Create the initial vector
        ICombinatoricsVector<BrainRegion> initialVector = Factory
                .createVector(br);

        // Create a simple combination generator to generate 2-combinations of
        // the initial vector
        Generator<BrainRegion> gen = Factory.createSimpleCombinationGenerator(
                initialVector, 2);

        // get all possible combinations
        for (ICombinatoricsVector<BrainRegion> combination : gen) {
            BrainRegion br1 = combination.getValue(0);
            BrainRegion br2 = combination.getValue(1);

            // filter if same text
            if (br1.getCoveredText().equals(br2.getCoveredText()))
                continue;

            pairs.add(new Pair<BrainRegion, BrainRegion>(br1, br2));
        }

        return pairs;
    }
}
