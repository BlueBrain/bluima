package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.io.TsvUtils.loadIntString;
import static ch.epfl.bbp.io.TsvUtils.loadStringInt;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.BLUE_UTILS_ROOT;
import static ch.epfl.bbp.uima.BlueUima.RESOURCES_PATH;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.DOCUMENT_ELEMENT;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static com.google.common.collect.Sets.newHashSet;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.types.DocumentBlock;
import ch.epfl.bbp.uima.typesystem.ContentSection;
import ch.epfl.bbp.uima.utils.SnowballStemmer;

/**
 * /** Annotates {@link DocumentBlock}s with {@link ContentSection} like
 * Acknowlegments, Correspondance, etc. Uses lists of section titles.<br/>
 * Lists developped by analyzing a large document sample, with focus on
 * high-precision (at the cost of recall).
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(inputs = { DOCUMENT_ELEMENT })
public class SectionAnnotator extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(SectionAnnotator.class);

    // String[] MM_TRIGGERS = { "method", "materi and method", "experiment",
    // "materi  and  method", "case present" };
    // String[] MM_TRIGGERS2 = { "materi and method", "materi  and  method" };

    int totCnt = 0, foundCnt = 0;

    /** k: id, v: {@link ContentSection} key */
    private Map<Integer, String> categories;
    /** k: stemmed section title, v: key from categories map above */
    private Map<String, Integer> sectionTitles;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        try {
            String root = BLUE_UTILS_ROOT + RESOURCES_PATH + "sections/";
            categories = loadIntString(new FileInputStream(root
                    + "categories.tsv"), true);
            checkArgument(categories.size() > 5);
            sectionTitles = loadStringInt(new FileInputStream(root
                    + "titles_stemmed.tsv"), true);
            checkArgument(sectionTitles.size() > 30);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = getHeaderIntDocId(jCas);
        totCnt++;

        // COMPUTE AND RANK STATEGIES
        Set<Integer> strategyUppercase = newHashSet(), //
        strategyExactMatch = newHashSet(), //
        strategyStartsWith = newHashSet();
        Map<DocumentBlock, String> blocksCache = newLinkedHashMap(); // order
        for (DocumentBlock block : select(jCas, DocumentBlock.class)) {

            String cleanedBlockText = cleanBlockText(block.getCoveredText());
            if (cleanedBlockText == null)
                cleanedBlockText = "";
            blocksCache.put(block, cleanedBlockText);

            for (Entry<String, Integer> ent : sectionTitles.entrySet()) {
                String trigger = ent.getKey();
                int category = ent.getValue();
                if (cleanedBlockText.startsWith(trigger.toUpperCase())) {
                    strategyUppercase.add(category);

                }
                if (cleanedBlockText.toLowerCase().equals(trigger)) {
                    strategyExactMatch.add(category);
                }
                if (cleanedBlockText.toLowerCase().startsWith(trigger)) {
                    strategyStartsWith.add(category);
                }
            }
        }
        LOG.trace("STATS: pmid:{}\tUC:{}\tEQ:{}\tST:{}", new Object[] { pmId,
                strategyUppercase.size(), strategyExactMatch.size(),
                strategyStartsWith.size() });

        // NOW SELECT STRATEGY
        boolean found = false;
        if (strategyUppercase.size() > 2) {
            LOG.trace("TAKING STRATEGY UP(PERCASE) at cut "
                    + strategyUppercase.size());
            found = true;
            for (Entry<DocumentBlock, String> block : blocksCache.entrySet()) {
                for (Entry<String, Integer> ent : sectionTitles.entrySet()) {
                    String trigger = ent.getKey();
                    if (block.getValue().startsWith(trigger.toUpperCase())) {
                        String sectionIdStr = categories.get(ent.getValue());
                        block.getKey().setLabel(sectionIdStr);
                    }
                }
            }

        } else if (strategyExactMatch.size() >= 4) {
            LOG.trace("TAKING STRATEGY EX(ACT MATCH) at cut "
                    + strategyExactMatch.size());
            found = true;
            for (Entry<DocumentBlock, String> block : blocksCache.entrySet()) {
                for (Entry<String, Integer> ent : sectionTitles.entrySet()) {
                    String trigger = ent.getKey();
                    if (block.getValue().toLowerCase().equals(trigger)) {
                        String sectionIdStr = categories.get(ent.getValue());
                        block.getKey().setLabel(sectionIdStr);
                    }
                }
            }
        } else if (strategyStartsWith.size() >= 5) {
            LOG.trace("TAKING STRATEGY ST(ART WITH) at cut "
                    + strategyStartsWith.size());
            found = true;
            for (Entry<DocumentBlock, String> block : blocksCache.entrySet()) {
                for (Entry<String, Integer> ent : sectionTitles.entrySet()) {
                    String trigger = ent.getKey();
                    if (block.getValue().toLowerCase().startsWith(trigger)) {
                        String sectionIdStr = categories.get(ent.getValue());
                        block.getKey().setLabel(sectionIdStr);
                    }
                }
            }
        }

        if (found) {
            foundCnt++;
        } else {
            LOG.trace("COULD NOT LABEL: " + pmId
                    + " ********************************");
            for (DocumentBlock block : select(jCas, DocumentBlock.class)) {
                String cleanedBlockText = cleanBlockText(block.getCoveredText());
                LOG.trace("{}\t***{}", cleanedBlockText, block.getCoveredText());
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        LOG.trace("found\t{}\ttotal\t{}", foundCnt, totCnt);
    }

    private static SnowballStemmer stemmer = new SnowballStemmer();

    /**
     * Used to identify block "titles", like "Introduction","References"
     * 
     * @param the
     *            text from a {@link DocumentBlock}
     * @return the cleaned up 1st line of that block, if "valid". NOT
     *         lowercased.
     */
    public static String cleanBlockText(String text) {

        // only keep text longer that 5
        if (text == null || text.length() < 6)
            return null;
        // only keep text longer that 5
        if (text.length() < 6) {
            return null;
        }
        // keep only 1st line
        int firstReturn = text.indexOf("\n");
        if (firstReturn > -1) {
            text = text.substring(0, firstReturn);
        }
        // remove double spaces
        text = text.replaceAll(" +", " ").trim();

        // remove numbers at beg, tabs
        text = text.replaceAll("^\\d*", "").replaceAll("\t", " ").trim();
        // then, remove dot at beg
        if (text.length() > 0 && text.charAt(0) == '.') {
            text = text.substring(1).trim();
        }

        // check that at least 50% of chars are letters
        double letters = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isLetter(text.charAt(i)))
                letters++;
        }
        if ((letters / (text.length() + 0d)) < 0.5d) {
            return null;
        }

        // stemming
        String stemmed = "";
        for (String token : text.trim().split(" ")) {
            stemmed += stemmer.stem(token) + " ";
        }

        // trim at 30 chars
        String snippet = StringUtils.snippetize(stemmed.trim(), 30);
        return snippet.trim();
    }
}
