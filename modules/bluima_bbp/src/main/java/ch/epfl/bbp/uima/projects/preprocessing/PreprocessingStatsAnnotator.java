package ch.epfl.bbp.uima.projects.preprocessing;

import static com.google.common.collect.Lists.newArrayList;
import static java.lang.System.currentTimeMillis;
import static org.apache.commons.lang.StringUtils.join;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.python.google.common.collect.Maps;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.types.Keep;
import de.julielab.jules.types.Header;

/**
 * Util to inspect quality of preprocessing, and trace back anomalies back to
 * the original pmid.
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class PreprocessingStatsAnnotator extends JCasAnnotator_ImplBase {

    // k: word, v: pair{ cnt, {list of pmids where word appear, max 20}};
    // (treemap => sorted lexicographically)
    static final Map<String, Pair<Integer, ArrayList<String>>> words = Maps
            .newTreeMap();

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // int pmId = getHeaderIntDocId(jCas);
        String pmId = JCasUtil.selectSingle(jCas, Header.class).getSource();

        // with keeps
        for (Keep sp : select(jCas, Keep.class)) {
            String ttext = sp.getNormalizedText().toLowerCase();

            // with Tokens:
            // for (Token t : select(jCas, Token.class)) {
            // String ttext = t.getCoveredText().toLowerCase();

            int cnt = 1;
            ArrayList<String> pmIds;
            if (words.containsKey(ttext)) {
                cnt += words.get(ttext).getLeft();
                pmIds = words.get(ttext).getRight();
            } else {
                pmIds = newArrayList();
            }

            if (pmIds.size() < 3) {
                pmIds.add(pmId);
            }
            words.put(ttext, Pair.of(cnt, pmIds));
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {

        try {
            TextFileWriter writer = new TextFileWriter(new File(
                    "target/preprocessing_" + currentTimeMillis() + ".tsv"));

            for (Entry<String, Pair<Integer, ArrayList<String>>> en : words
                    .entrySet()) {
                writer.addLine(en.getKey().replaceAll("[\r\n]", "") + "\t"
                        + en.getValue().getLeft() + "\t"
                        + join(en.getValue().getRight(), "\t"));
            }
            writer.close();

        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
    // public static void main(String[] args) throws Exception {
    //
    // File scriptFile = new File(
    // PROJECTS_ROOT
    // +
    // "preprocessing/20130905_validate_token_preprocessing_for_lda/20130905_validate_token_preprocessing_for_lda.pipeline");
    // for (int i = 0; i < 100; i++) {
    // Launcher.runPipeline(scriptFile, Lists.newArrayList("" + i));
    // }
    //
    // }
}