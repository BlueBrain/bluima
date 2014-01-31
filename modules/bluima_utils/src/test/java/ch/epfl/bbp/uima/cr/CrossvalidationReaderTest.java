package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;
import static ch.epfl.bbp.uima.cr.CrossvalidationReader.PARAM_MODE_EVAL;
import static ch.epfl.bbp.uima.cr.CrossvalidationReader.PARAM_SEED;
import static ch.epfl.bbp.uima.cr.CrossvalidationReader.PARAM_SLICE;
import static ch.epfl.bbp.uima.cr.CrossvalidationReader.PARAM_SPLITS;
import static ch.epfl.bbp.uima.cr.CrossvalidationReader.PARAM_WRAPPED_CR;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
// FIXME
@OperationalProperties(multipleDeploymentAllowed = false)
public class CrossvalidationReaderTest extends JCasAnnotator_ImplBase {

    List<String> testCollected = newArrayList();

    @Test
    public void test() throws Exception {

        List<String> testInput = newArrayList();
        for (int i = 0; i < 100; i++) {
            testInput.add(i + "_");
        }

        String outdir = "target/test1";
        new File(outdir).mkdirs();

        Object[] wrappedCollectionReader = { TextArrayReader.class,
                PARAM_INPUT, testInput.toArray(new String[testInput.size()]) };

        runPipeline(createReader(CrossvalidationReader.class, JULIE_TSD,//
                PARAM_WRAPPED_CR, wrappedCollectionReader,//
                PARAM_OUTPUT_DIR, outdir,//
                PARAM_SEED, 1,//
                PARAM_SPLITS, 10,//
                PARAM_SLICE, 1,//
                PARAM_MODE_EVAL, true),
                createEngine(CrossvalidationReaderTest.class));

        System.out.println(Arrays.toString(testCollected.toArray()));
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        testCollected.add(jCas.getDocumentText());
    }
}
