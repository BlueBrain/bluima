package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;

import ch.epfl.bbp.io.LineReader;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;

import com.google.common.collect.Sets;

/**
 * 
 * for {@link BrainRegionDictTerm}
 * 
 * 
 * @author richarde
 */
// makes things simpler to handle a single file
@OperationalProperties(multipleDeploymentAllowed = false)
public class BamsEvaluateBrainregionsExtraction extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(BamsEvaluateBrainregionsExtraction.class);

    @ConfigurationParameter(name = BlueUima.PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirStr;
    private File inputDir;
    private int totalCnt = 0, totalFound = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            inputDir = new File(inputDirStr);
            checkArgument(inputDir.exists());
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        try {
            File bamsGold = new File(inputDir, pmId + "_bams_br.txt");
            checkArgument(bamsGold.exists(), "GOLD annot does not exist for "
                    + pmId + ", I was expecting " + bamsGold.getAbsolutePath());
            List<String> bamsGoldRegions = linesFrom(bamsGold.getAbsolutePath());

            LOG.info("pmId: {}", pmId);

            Set<String> system = newHashSet();
            for (BrainRegionDictTerm br : select(jCas,
                    BrainRegionDictTerm.class)) {
                system.add(br.getCoveredText().replaceAll("\n", "")
                        .toLowerCase());
            }
            for (String s : system) {
                LOG.info(" {}", s);
            }

            int goldCnt = bamsGoldRegions.size(), found = 0;
            for (String gold : bamsGoldRegions) {
                if (system.contains(gold.toLowerCase())) {
                    found++;
                    LOG.debug("     found: " + gold);
                } else {
                    LOG.debug(" NOT found: " + gold);
                }
            }
            LOG.info("gold: {} found: {}\n", new Object[] {  goldCnt,
                    found });
            totalCnt += goldCnt;
            totalFound += found;

        } catch (FileNotFoundException e) {
            throw new AnalysisEngineProcessException();
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        LOG.info("TOTALLL gold: {} found: {}, ratio: {}", new Object[] {
                totalCnt, totalFound, (totalFound + 0d) / (totalCnt + 0d) });
    }
}
