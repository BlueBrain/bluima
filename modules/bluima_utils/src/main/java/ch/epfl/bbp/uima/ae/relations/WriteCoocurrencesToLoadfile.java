package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.File;
import java.io.IOException;
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

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * Extracts cooccurences
 * 
 * Format:<br/>
 * `pubmed_id`,`annot_1_id`,`annot_1_start`,`annot_1_end`,` annot_2_id`,`
 * annot_2_start`,`annot_2_end`
 * 
 * Import:<br/>
 * <code>cd to {outputFileDir}</code><br>
 * <code>cd . (if you ran it again...)</code><br>
 * <code>mysql -uroot --local-infile</code><br>
 * <code>use {outputFile}</code><br>
 * <code>LOAD DATA LOCAL INFILE '{outputFile}' INTO TABLE relations FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (`pubmed_id`,`sentence_id`,`region_1_name`,`region_1_start`,`region_1_end`,`region_2_name`,`region_2_start`,`region_2_end`, `snippet`);</code>
 * <br>
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class WriteCoocurrencesToLoadfile extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory
            .getLogger(WriteCoocurrencesToLoadfile.class);

    public static final String[] FIELDS = { "pubmed_id", "annot_1_id",
            "annot_1_start", "annot_1_end", "annot_2_id", " annot_2_start",
            "annot_2_end" };

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    protected String outputFile;

    public static final String PARAM_WRITE_SNIPPETS = "writeSnippets";
    @ConfigurationParameter(name = PARAM_WRITE_SNIPPETS, mandatory = false, defaultValue = "true")
    protected boolean writeSnippets;

    protected LoadDataFileWriter writer;
    protected LoadDataFileWriter snippetWriter;
    protected int docCnt = 0, cooccCnt = 0;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            // writer
            LOG.info("writing LOAD DATA file to {}", outputFile);
            writer = new LoadDataFileWriter(new File(outputFile), "\t", FIELDS);
            if (writeSnippets)
                snippetWriter = new LoadDataFileWriter(new File(outputFile
                        + ".snippet"), "\t");

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        for (Cooccurrence c : select(jCas, Cooccurrence.class)) {

            List<Object> line = newArrayList();
            line.add(pmId);
            for (String firstId : c.getFirstIds().toArray()) {
                line.add(firstId.replaceAll("[\r\t\n]", ""));
            }
            line.add(c.getFirstEntity().getBegin());
            line.add(c.getFirstEntity().getEnd());
            for (String secondId : c.getSecondIds().toArray()) {
                line.add(secondId.replaceAll("[\r\t\n]", ""));
            }
            line.add(c.getSecondEntity().getBegin());
            line.add(c.getSecondEntity().getEnd());
            writer.addLoadLine(line.toArray());

            if (writeSnippets) {
                String snippet = jCas.getDocumentText()
                        .substring(c.getSnippetBegin(), c.getSnippetEnd())
                        .replaceAll("[\r\t\n]", "");
                snippetWriter.addLoadLine(snippet);
            }

            cooccCnt++;
        }

        // FLUSH once in a while
        if (++docCnt % 100 == 0) {
            try {
                LOG.debug("flushing at docCnt\t{}\tcoocc\t{}", docCnt, cooccCnt);
                writer.flush();
                if (writeSnippets)
                    snippetWriter.flush();
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
            if (writeSnippets)
                snippetWriter.flush();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
        LOG.debug("TOTAL docCnt\t{}\tcoocc\t{}", docCnt, cooccCnt);
    }
}
