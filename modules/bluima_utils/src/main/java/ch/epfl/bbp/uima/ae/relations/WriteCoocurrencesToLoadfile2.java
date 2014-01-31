package ch.epfl.bbp.uima.ae.relations;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_COOCCURRENCE_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FIRST_ANNOT_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SECOND_ANNOT_TYPE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_VERBOSE;
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
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueCasUtil.Position;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.utils.LoadDataFileWriter;

/**
 * Extracts {@link Cooccurrence}s. Snippet on same file.
 * 
 * Format varies, but has the general structure:
 * <ul>
 * <li>pmId</li>
 * <li>confidence</li>
 * <li>e1_id</li>
 * <li>e1_start</li>
 * <li>e1_end</li>
 * <li>(e1_type)</li>
 * <li>e2_id</li>
 * <li>e2_start</li>
 * <li>e2_end</li>
 * <li>(e2_type)</li>
 * <li>snippet_begin (= enclosing scope begin)</li>
 * <li>snippet_end (= enclosing scope end)</li>
 * <li>(snippet)</li>
 * </ul>
 * 
 * 
 * Output can be loaded into a db, e.g.:<br/>
 * <code>cd to {outputFileDir}</code><br>
 * <code>cd . (if you ran it again...)</code><br>
 * <code>mysql -uroot --local-infile</code><br>
 * <code>use {outputFile}</code><br>
 * <code>LOAD DATA LOCAL INFILE '{outputFile}' INTO TABLE relations FIELDS TERMINATED BY ' ' LINES TERMINATED BY '\n' (`list`,`of`,`your`,`fields`, ...);</code>
 * <br>
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class WriteCoocurrencesToLoadfile2 extends JCasAnnotator_ImplBase {

    protected static Logger LOG = LoggerFactory
            .getLogger(WriteCoocurrencesToLoadfile2.class);

    private static final String NULL = "NULL";

    @ConfigurationParameter(name = PARAM_OUTPUT_FILE, mandatory = true)
    protected String outputFile;

    @ConfigurationParameter(name = PARAM_FIRST_ANNOT_TYPE, mandatory = false, defaultValue = NULL, //
    description = "an optional id (String, int, whatever) to identify the type of the first annotation.")
    protected String annot1Type;

    @ConfigurationParameter(name = PARAM_SECOND_ANNOT_TYPE, mandatory = false, defaultValue = NULL, //
    description = "an optional id (String, int, whatever) to identify the type of the second annotation.")
    protected String annot2Type;

    @ConfigurationParameter(name = PARAM_COOCCURRENCE_TYPE, mandatory = false, //
    description = "Whether to filter on a given type of cooccurrence. If none provided, selects ALL cooccurrences.")
    protected String cooccurrenceType;

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
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        for (Cooccurrence c : select(jCas, Cooccurrence.class)) {
            // filter cooccurrence type
            if (cooccurrenceType != null
                    && !c.getCooccurrenceType().equals(cooccurrenceType)) {
                continue;
            } else {

                List<Object> lines = newArrayList();
                lines.add(pmId);
                lines.add(c.getConfidence());
                for (String firstId : c.getFirstIds().toArray()) {
                    String line = (firstId == null) ? NULL : firstId
                            .replaceAll("[\r\t\n]", "");
                    lines.add(line);
                }
                lines.add(c.getFirstEntity().getBegin());
                lines.add(c.getFirstEntity().getEnd());
                if (!annot1Type.equals(NULL))
                    lines.add(annot1Type);
                for (String secondId : c.getSecondIds().toArray()) {
                    String line = (secondId == null) ? NULL : secondId
                            .replaceAll("[\r\t\n]", "");
                    lines.add(line);
                }
                lines.add(c.getSecondEntity().getBegin());
                lines.add(c.getSecondEntity().getEnd());
                if (!annot2Type.equals(NULL))
                    lines.add(annot2Type);

                // snippet
                lines.add(c.getSnippetBegin());
                lines.add(c.getSnippetEnd());
                if (writeSnippet) {
                    String snippet = snippet(jCas, c.getSnippetBegin(),
                            c.getSnippetEnd(), c.getFirstEntity(),
                            c.getSecondEntity());
                    lines.add(snippet);
                }

                try {
                    writer.addLoadLine(lines.toArray());
                } catch (Exception e) {
                    LOG.warn("could not write Coocs '" + annot1Type + "'<-->'"
                            + annot2Type + "' for pmId " + pmId, e);
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
        }
    }

    public static String snippet(JCas jCas, int snippetBegin, int snippetEnd,
            Annotation a1, Annotation a2) {

        final String T_BEGIN = "<strong class=\"";
        final String T_END = "</strong>";

        String s = jCas.getDocumentText().substring(snippetBegin, snippetEnd);
        String sError = s;

        try {

            int relA1Begin = a1.getBegin() - snippetBegin;
            int relA1End = a1.getEnd() - snippetBegin;
            int relA2Begin = a2.getBegin() - snippetBegin;
            int relA2End = a2.getEnd() - snippetBegin;

            // highlight
            Position position = BlueCasUtil.isBefore(a1, a2);
            if (position == Position.before) {
                // start w/ a2 (at the end)
                s = s.substring(0, relA2End) + T_END + s.substring(relA2End);
                s = s.substring(0, relA2Begin) + T_BEGIN
                        + a2.getClass().getSimpleName() + "\">"
                        + s.substring(relA2Begin);
                // now with a1
                s = s.substring(0, relA1End) + T_END + s.substring(relA1End);
                s = s.substring(0, relA1Begin) + T_BEGIN
                        + a1.getClass().getSimpleName() + "\">"
                        + s.substring(relA1Begin);

            } else if (position == Position.after) {
                // start w/ a1 (at the end)
                s = s.substring(0, relA1End) + T_END + s.substring(relA1End);
                s = s.substring(0, relA1Begin) + T_BEGIN
                        + a1.getClass().getSimpleName() + "\">"
                        + s.substring(relA1Begin);
                // now with a2
                s = s.substring(0, relA2End) + T_END + s.substring(relA2End);
                s = s.substring(0, relA2Begin) + T_BEGIN
                        + a2.getClass().getSimpleName() + "\">"
                        + s.substring(relA2Begin);

            } else {// overlap LATER
                LOG.warn("don't know how to highlight snippet that overlaps, {}"
                        + getHeaderDocId(jCas));
            }

            return s.replaceAll("[\r\t\n]", "");

        } catch (Exception e) {
            LOG.warn(
                    "Could not extract snippet on pmid {} beg {} end {} a1 {} a2 {}",
                    new Object[] { getHeaderDocId(jCas), snippetBegin,
                            snippetEnd, To.string(a1), To.string(a2) });
            return sError;
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
        LOG.debug("TOTAL cooccurrences\t{}\t[{}]", cooccCnt, cooccurrenceType);
    }
}
