package ch.epfl.bbp.uima.projects.bluesearch;

import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.MethodDictTerm;

import com.google.common.collect.Lists;

/**
 * brain regions correspond to
 * http://braininfo.rprc.washington.edu/centraldirectory.aspx?ID={the id here}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class WriteResultsToFile extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(WriteResultsToFile.class);

    TextFileWriter writerRegion;
    TextFileWriter writerMethod;

    ArrayList<String> methods = Lists.newArrayList(//
            "method:FIELD POTENTIAL", //
            "method:REFLEX BEHAVIOR", //
            "method:PROTEIN LOCALIZATION", //
            "method:GENERAL ELECTROPHYSIOLOGICAL PROPERTIES", //
            "method:VOLTAGE CLAMP", //
            "method:LIFE CYCLE BEHAVIOR", //
            "method:PATCH CLAMP", //
            "method:LEARNING BEHAVIOR", //
            "method:PROTEIN INTERACTION", //
            "method:MEMORY BEHAVIOR", //
            "method:CURRENT CLAMP", //
            "method:AMPEROMETRY", //
            "method:SHARP ELECTRODE", //
            "method:SINGLE-UNIT RECORDING", //
            "method:SEQUENCING", //
            "method:CELLULAR RECONSTRUCTION", //
            "method:MOLECULE BINDING", //
            "method:GENE PROFILING", //
            "method:EXPLORATORY BEHAVIOR", //
            "method:PROTEIN IDENTIFICATION");

    @ConfigurationParameter(name = BlueUima.PARAM_OUTPUT_DIR, mandatory = true)
    private String outDir;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            writerRegion = new TextFileWriter(outDir + "/region.tsv");
            writerMethod = new TextFileWriter(outDir + "/method.tsv");
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    int i;

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = BlueCasUtil.getHeaderIntDocId(jCas);

        for (BrainRegionDictTerm br : select(jCas, BrainRegionDictTerm.class)) {
            writerRegion.addLine(pmId + "\t" + br.getBegin() + "\t"
                    + br.getEnd() + "\t" + br.getEntityId());
        }

        for (MethodDictTerm br : select(jCas, MethodDictTerm.class)) {

            try {
                int methodId = methods.indexOf(br.getEntityId());
                writerRegion.addLine(pmId + "\t" + br.getBegin() + "\t"
                        + br.getEnd() + "\t" + methodId);
            } catch (Exception e) {
                LOG.error("woopsss with " + br, e);
            }
        }
        if (i++ % 10000 == 0) {
            try {
                writerMethod.flush();
                writerRegion.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        try {
            writerRegion.close();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}