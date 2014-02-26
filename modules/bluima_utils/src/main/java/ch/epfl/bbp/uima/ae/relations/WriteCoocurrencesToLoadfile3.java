package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_VERBOSE;
import static ch.epfl.bbp.uima.ae.relations.CooccurrencesEvaluationAnnotator.contains;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Lists.newLinkedList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.Prin;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * Extracts {@link Cooccurrence}s for
 * projects/extract_brainregions/20140221_slurm_extraction <br>
 * Format:<br>
 * 
 * <pre>
 * pmid, confidence, 
 * br1, br1_id, br1_start, br1_end, 
 * br2, br2_id, br2_start, br2_end, 
 * system1_topdown, system2_kernel, system3_rules, 
 * sentence_beg, sentence_end, snippet
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class WriteCoocurrencesToLoadfile3 extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(WriteCoocurrencesToLoadfile3.class);

    private static final String VIEW1 = "topdown", VIEW2 = "kernel",
            VIEW3 = "rules";

    private static final boolean DEBUG = false;

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    protected String outputFile;

    @ConfigurationParameter(name = PARAM_VERBOSE, defaultValue = "true", //
    mandatory = false, description = "Whether to write the snippet text.")
    protected boolean writeSnippet;

    protected LoadDataFileWriter writer;
    protected int cooccCnt = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            LOG.info("writing LOAD DATA file to {}", outputFile);
            writer = new LoadDataFileWriter(new File(outputFile), "\t");
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = getHeaderIntDocId(jCas);

        try {
            // REM: inefficient, but make it right...
            JCas view1 = jCas.getView(VIEW1), view2 = jCas.getView(VIEW2), view3 = jCas
                    .getView(VIEW3);
            final Collection<Cooccurrence> first = select(view1,
                    Cooccurrence.class);
            final Collection<Cooccurrence> second = select(view2,
                    Cooccurrence.class);
            final Collection<Cooccurrence> third = select(view3,
                    Cooccurrence.class);

            if (DEBUG) {
                System.out.println(pmId + " FIRST::");
                for (Cooccurrence c : first)
                    Prin.t(c);
                System.out.println(pmId + " SECOND::");
                for (Cooccurrence c : second)
                    Prin.t(c);
                System.out.println(pmId + " THIRD::");
                for (Cooccurrence c : third)
                    Prin.t(c);
            }

            final Collection<Cooccurrence> union = newLinkedList();
            for (Cooccurrence c : first)
                if (!contains(union, c))
                    union.add(c);
            for (Cooccurrence c : second)
                if (!contains(union, c))
                    union.add(c);
            for (Cooccurrence c : third)
                if (!contains(union, c))
                    union.add(c);

            for (Cooccurrence c : union) {
                write(jCas, pmId, c, contains(first, c), contains(second, c),
                        contains(third, c));
            }
        } catch (Exception e) {
            LOG.error("something went wrong with " + pmId, e);
        }
    }

    private void write(JCas jCas, int pmId, Cooccurrence c, boolean sys1,
            boolean sys2, boolean sys3) throws AnalysisEngineProcessException {
        List<Object> lines = newArrayList();
        lines.add(pmId);
        lines.add(c.getConfidence());

        lines.add(c.getFirstEntity().getCoveredText()
                .replaceAll("[\r\t\n]", ""));
        lines.add(((BrainRegionDictTerm) c.getFirstEntity()).getEntityId());
        lines.add(c.getFirstEntity().getBegin());
        lines.add(c.getFirstEntity().getEnd());

        lines.add(c.getSecondEntity().getCoveredText()
                .replaceAll("[\r\t\n]", ""));
        lines.add(((BrainRegionDictTerm) c.getSecondEntity()).getEntityId());
        lines.add(c.getSecondEntity().getBegin());
        lines.add(c.getSecondEntity().getEnd());

        // system output
        lines.add(sys1 ? "1" : "0");
        lines.add(sys2 ? "1" : "0");
        lines.add(sys3 ? "1" : "0");

        // snippet
        lines.add(c.getSnippetBegin());
        lines.add(c.getSnippetEnd());
        if (writeSnippet) {
            String snippet = WriteCoocurrencesToLoadfile2.snippet(jCas,
                    c.getSnippetBegin(), c.getSnippetEnd(), c.getFirstEntity(),
                    c.getSecondEntity());
            lines.add(snippet);
        }

        try {
            writer.addLoadLine(lines.toArray());
        } catch (Exception e) {
            LOG.warn("could not write Coocs for pmId " + pmId, e);
        }

        // FLUSH once in a while (quite frequently, actually)
        if (cooccCnt++ % 50 == 0) {
            try {
                LOG.debug("flushing at coocc\t{}", cooccCnt);
                writer.flush();
            } catch (IOException e) {
                throw new AnalysisEngineProcessException(e);
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        try {
            writer.close();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}
