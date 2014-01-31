package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.commons.math3.util.Pair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;
import de.julielab.jules.types.Sentence;

/**
 * 
 * for {@link BrainRegionDictTerm}
 * 
 * <code>cd to 20130503_ExtractBrainregionsCoocurrences</code><br>
 * <code>cd . (if you ran it again...)</code><br>
 * <code>mysql -uroot</code><br>
 * <code>use brainregions_relations</code><br>
 * <code>LOAD DATA LOCAL INFILE 'br_abstract.load_data.txt' INTO TABLE relations FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (`pubmed_id`,`region_1_id`,`region_1_start`,`region_1_end`,`region_2_id`,`region_2_start`,`region_2_end`);</code>
 * <br>
 * 
 * @author richarde
 */
// makes things simpler to handle a single file
@Deprecated
@OperationalProperties(multipleDeploymentAllowed = false)
public class ExtractBrainregionsCoocurrences extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(ExtractBrainregionsCoocurrences.class);

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR, mandatory = true)
    protected String outputDir;

    private static final String PARAM_DOCUMENT_LEVEL = "document_level";
    @ConfigurationParameter(name = PARAM_DOCUMENT_LEVEL, defaultValue = "false",//
    description = "whether to compute coocurrences at the (whole) document level, too")
    protected boolean documentLevel;

    // LOAD_DATA
    protected LoadDataFileWriter sentenceLevelWriter;
    protected LoadDataFileWriter documentLevelWriter;

    // counts
    protected int docCnt = 0, brDocLevel = 0, brSentenceLevel = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            LOG.info("writing LOAD DATA file to {}", outputDir);
            new File(outputDir).mkdir();
            if (documentLevel) {
                documentLevelWriter = new LoadDataFileWriter(new File(outputDir
                        + "/br_abstract.load_data.txt"), "\t");
            }
            sentenceLevelWriter = new LoadDataFileWriter(new File(outputDir
                    + "/br_sentence.load_data.txt"), "\t");
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        // collect collocations at DOCUMENT level
        if (documentLevel) {
            Collection<BrainRegionDictTerm> br = select(jCas,
                    BrainRegionDictTerm.class);
            for (Pair<BrainRegionDictTerm, BrainRegionDictTerm> pair : combinaisonPairs(br)) {
                documentLevelWriter.addLoadLine((Object[]) getLoadline(pair,
                        pmId));
                brDocLevel++;
            }
        }

        // collect collocations at SENTENCE level
        for (Sentence s : JCasUtil.select(jCas, Sentence.class)) {

            List<BrainRegionDictTerm> brInSentence = selectCovered(jCas,
                    BrainRegionDictTerm.class, s);
            for (Pair<BrainRegionDictTerm, BrainRegionDictTerm> pair : combinaisonPairs(brInSentence)) {
                // LOG.debug("{}  ++ {}", pair.getKey().getCoveredText(), pair
                // .getValue().getCoveredText());
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
     * `pubmed_id`,`region_1_id`,`region_1_start`,`region_1_end`,`region_2_id`,`
     * region_2_start`,`region_2_end`
     */
    private String[] getLoadline(
            Pair<BrainRegionDictTerm, BrainRegionDictTerm> pair, int pmId) {

        BrainRegionDictTerm br1 = pair.getKey();
        BrainRegionDictTerm br2 = pair.getValue();

        return new String[] { pmId + "",//
                br1.getEntityId(), br1.getBegin() + "", br1.getEnd() + "", //
                br2.getEntityId(), br2.getBegin() + "", br2.getEnd() + "" };
    }

    /** @return all simple 2-combinaisons */
    public static List<Pair<BrainRegionDictTerm, BrainRegionDictTerm>> combinaisonPairs(
            Collection<BrainRegionDictTerm> br) {

        List<Pair<BrainRegionDictTerm, BrainRegionDictTerm>> pairs = newArrayList();
        // we need at least 2 elems to find pairs
        if (br == null || br.size() < 2)
            return pairs;

        // Create the initial vector
        ICombinatoricsVector<BrainRegionDictTerm> initialVector = Factory
                .createVector(br);

        // Create a simple combination generator to generate 2-combinations of
        // the initial vector
        Generator<BrainRegionDictTerm> gen = Factory
                .createSimpleCombinationGenerator(initialVector, 2);

        // get all possible combinations
        for (ICombinatoricsVector<BrainRegionDictTerm> combination : gen) {
            BrainRegionDictTerm br1 = combination.getValue(0);
            BrainRegionDictTerm br2 = combination.getValue(1);

            // filter if same text
            if (br1.getCoveredText().equals(br2.getCoveredText()))
                continue;

            int br1id = parseInt(br1.getEntityId());
            int br2id = parseInt(br2.getEntityId());
            // filter out "brain"
            if (br1id == 21 || br2id == 21)
                continue;

            if (br1id < br2id)
                pairs.add(new Pair<BrainRegionDictTerm, BrainRegionDictTerm>(
                        br1, br2));
            else
                pairs.add(new Pair<BrainRegionDictTerm, BrainRegionDictTerm>(
                        br2, br1));
        }

        return pairs;
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();

        try {
            if (documentLevel)
                documentLevelWriter.close();
            sentenceLevelWriter.close();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }

        LOG.debug("docCnt   " + docCnt);
        LOG.debug("brDocLevel " + brDocLevel);
        LOG.debug("brSentenceLevel " + brSentenceLevel);
    }
}
