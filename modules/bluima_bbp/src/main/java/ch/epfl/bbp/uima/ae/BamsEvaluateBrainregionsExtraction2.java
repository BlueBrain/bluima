package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.LexicaHelper.LEXICA_ROOT;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.Normalized;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.Original;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.Parent1;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.Parent2;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.Synonym;
import static ch.epfl.bbp.uima.ae.BamsEvaluateBrainregionsExtraction2.VariantType.SynonymNormalized;
import static ch.epfl.bbp.uima.xml.ConceptMapperParser.parse;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.python.google.common.collect.Maps.newHashMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import w.ch.epfl.bbp.nlp.br.normalize.Normalize;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.projects.brainregions.bams.FindSynonyms3;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.xml.bams.BamsOntology;
import ch.epfl.bbp.uima.xml.bams.BamsOntologyParser;
import ch.epfl.bbp.uima.xml.bams.BrainPart;

/**
 * compares BAMS gold against found BR NE (using all NERs, and normalizing using
 * French's Modifiers).
 * 
 * @author renaud.richardet@epfl.ch
 */
// makes things simpler to handle a single file
@OperationalProperties(multipleDeploymentAllowed = false)
public class BamsEvaluateBrainregionsExtraction2 extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(BamsEvaluateBrainregionsExtraction2.class);

    @ConfigurationParameter(name = BlueUima.PARAM_INPUT_DIRECTORY, mandatory = true)
    private String inputDirStr;
    private File inputDir;
    private int totalCnt = 0, totalFound = 0;

    private Normalize normalize;

    enum VariantType {
        Original(0), //
        Synonym(1), //
        Normalized(1), //
        Parent1(2), //
        @Deprecated
        SynonymNormalized(3), //
        Parent2(4);

        private int score;

        private VariantType(int score) {
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

    /** k: bamsEntry v: (k: all variants v: with their type) */
    Map<String, List<Variant>> bamsVariants = newHashMap();

    public static class Variant {
        String s;
        List<VariantType> _types = newArrayList();

        public Variant(String s, VariantType... types) {
            this.s = s;
            for (VariantType t : types)
                _types.add(t);
        }

        private int getScore() {
            int s = 0;
            for (VariantType t : _types)
                s += t.score;
            return s;
        }

        @Override
        public String toString() {
            return s + "=" + StringUtils.join(_types.toArray());
        }
    }

    private FindSynonyms3 findSynonyms3;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            normalize = new Normalize();
            findSynonyms3 = new FindSynonyms3();

            // load BAMS onto
            BamsOntology bamsOntology = BamsOntologyParser.parse();

            // load BAMS ConceptMapper, compute variants once and for all
            inputDir = new File(inputDirStr);
            checkArgument(inputDir.exists());

            for (String bamsEntry : parse(
                    new File(LEXICA_ROOT
                            + "resources/brainregions/bams2013.xml")).keySet()) {
                bamsEntry = bamsEntry.toLowerCase();

                List<Variant> variants = newArrayList();

                // ORIGINAL
                variants.add(new Variant(bamsEntry, Original));

                // PARENT
                BrainPart bp = null;
                for (BrainPart b : bamsOntology.getBrainParts().values()) {
                    if (b.getName().toLowerCase().equals(bamsEntry)) {
                        bp = b;
                        break;
                    }
                }
                if (bp == null)
                    throw new RuntimeException("not found: " + bamsEntry);
                //
                BrainPart partOf = bp.getIsPartOf();
                if (partOf != null) {

                    variants.add(new Variant(partOf.getName().toLowerCase(),
                            Parent1));
                    BrainPart partOf2 = partOf.getIsPartOf();
                    if (partOf2 != null) {
                        variants.add(new Variant(partOf2.getName(), Parent2));
                    }
                }

                // SYNONYMS
                for (String syn : findSynonyms3.getSynonyms(bamsEntry)) {
                    variants.add(new Variant(syn, Synonym));
                }

                // NORMALIZE
                for (String normalized : normalize
                        .processMentionString(bamsEntry)) {

                    variants.add(new Variant(normalized, Normalized));

                    // SYNONYM NORMALIZED
                    for (String synNorm : findSynonyms3.getSynonyms(normalized)) {
                        variants.add(new Variant(synNorm, Synonym, Normalized));
                    }
                }
                bamsVariants.put(bamsEntry, variants);
            }

            // TESTS
            for (Variant v : bamsVariants.get("sublingual nucleus")) {
                System.out.println("-- " + v);
            }

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);
        LOG.info("pmId: {}", pmId);

        try {
            //
            // add all br found on this CAS, normalize & synonym them!
            Map<String, VariantType> systemVariants = newHashMap();
            for (BrainRegionDictTerm br : select(jCas,
                    BrainRegionDictTerm.class)) {
                String rawSystem = br.getCoveredText().replaceAll("[\n\r]", "")
                        .toLowerCase();

                // ORIGINAL
                systemVariants.put(rawSystem, Original);

                // SYNONYMS
                for (String syn : findSynonyms3.getSynonyms(rawSystem)) {
                    if (systemVariants.containsKey(syn)) {
                        if (systemVariants.get(syn).getScore() > Synonym.score)
                            systemVariants.put(syn, Synonym);
                    } else
                        systemVariants.put(syn, Synonym);
                }

                // NORMALIZE
                for (String normalized : normalize
                        .processMentionString(rawSystem)) {

                    if (systemVariants.containsKey(normalized)) {
                        if (systemVariants.get(normalized).getScore() > Normalized.score)
                            systemVariants.put(normalized, Normalized);
                    } else
                        systemVariants.put(normalized, Normalized);

                    // SYNONYM NORMALIZED
                    for (String synNorm : findSynonyms3.getSynonyms(normalized)) {
                        if (!systemVariants.containsKey(synNorm))
                            systemVariants.put(synNorm, SynonymNormalized);
                    }
                }
            }
            for (Entry<String, VariantType> e : systemVariants.entrySet())
                LOG.info(e.toString());

            //
            // read gold annots (from BAMS)
            File bamsGold = new File(inputDir, pmId + "_bams_br.txt");
            checkArgument(bamsGold.exists(), "GOLD annot does not exist for "
                    + pmId + ", I was expecting " + bamsGold.getAbsolutePath());
            List<String> bamsGoldRegions = linesFrom(bamsGold.getAbsolutePath());

            int goldCnt = bamsGoldRegions.size(), foundCnt = 0;
            for (String gold : bamsGoldRegions) {
                LOG.info("gold: {}", gold);

                // pair:: k: score v: variants
                List<Pair<Integer, List<VariantType>>> found = newArrayList();

                theloop: for (Variant goldVariant : bamsVariants.get(gold
                        .toLowerCase())) {
                    LOG.info(" variant: {}", goldVariant);

                    for (Entry<String, VariantType> system : systemVariants
                            .entrySet()) {

                        if (system.getKey().equals(goldVariant.s)) {

                            // FOUND!!!

                            // score
                            List<VariantType> vs = goldVariant._types;
                            vs.add(system.getValue());
                            int score = 0;
                            for (VariantType v : vs) {
                                score += v.score;
                            }

                            found.add(Pair.of(score, vs));
                            break theloop;

                        }
                    }
                }
                if (!found.isEmpty()) {
                    foundCnt++;
                    LOG.info("FOUND\n");
                } else
                    LOG.info("NOT_FOUND\n");

            }
            LOG.info("gold: {} found: {} \n",
                    new Object[] { goldCnt, foundCnt });
            totalCnt += goldCnt;
            totalFound += foundCnt;

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
