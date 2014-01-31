package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.isEmptyText;
import static ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator.freeMemory;
import static ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator.maxMemory;
import static java.lang.System.currentTimeMillis;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;

/**
 * Logs statistics about the progress of the pipeline to the console.
 * 
 * @author renaud.richardet@epfl.ch
 */
// because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class StatsAnnotatorPlus extends JCasAnnotator_ImplBase {
    private static final Logger LOG = LoggerFactory
            .getLogger(StatsAnnotatorPlus.class);

    public static final String PARAM_PRINT_EVERY = "printEvery";
    @ConfigurationParameter(name = PARAM_PRINT_EVERY, defaultValue = "1000", //
    description = "print every n doc")
    private int printEvery;

    private ThreadMXBean threadMXBean;
    private DecimalFormat df = new DecimalFormat("#.##");

    private int cnt;
    private Long start = null;
    private long lastProcess;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        start = null;
        cnt = 0;
        threadMXBean = ManagementFactory.getThreadMXBean();
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        if (start == null) // start now! (after init done)
            start = currentTimeMillis();

        // only consider non-empty documents
        if (!isEmptyText(jCas))
            cnt++;

        if (cnt % printEvery == 0) {

            String docs_s = "";
            double since_start = ((currentTimeMillis() - start + 0d) / 1000d);
            if (since_start > 0d)
                docs_s += df.format((cnt + 0d) / (since_start));

            double sinceLastProcess = ((currentTimeMillis() - lastProcess + 0d) / 1000d);
            if (sinceLastProcess > 0d)
                docs_s += "\t"
                        + df.format((printEvery + 0d) / (sinceLastProcess));
            lastProcess = currentTimeMillis();

            LOG.info(
                    "~STATS pmid:{}\t#docs:{}\tdoc\\s:{}\t#threads:{}\tfree_mem:{}\tmax_mem:{}",
                    new Object[] { getHeaderDocId(jCas), cnt, docs_s,
                            threadMXBean.getThreadCount(), freeMemory(),
                            maxMemory() });
        }
    }
}