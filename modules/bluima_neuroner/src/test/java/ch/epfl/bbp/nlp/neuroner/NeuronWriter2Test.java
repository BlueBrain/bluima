package ch.epfl.bbp.nlp.neuroner;

import static ch.epfl.bbp.io.LineReader.linesFrom;
import static ch.epfl.bbp.uima.BlueCasUtil.setDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.List;

import neuroner.NeuroNER.Morphology;
import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronProperty;
import neuroner.NeuroNER.NeuronTrigger;
import neuroner.NeuroNER.Size;

import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

public class NeuronWriter2Test {

    @Test
    @Ignore //FIXME neuroner types
    public void test() throws Exception {

        JCas jCas = getTokenizedTestCas("This is a pyramidal neuron. And a pyramidal blah and a large glia cell.");
        setDocId(jCas, 17);

        createAnnot(jCas, Morphology.class, 10, 19, "pyramidal");
        createAnnot(jCas, NeuronTrigger.class, 20, 26, "neuron");
        createAnnot(jCas, Neuron.class, 10, 26,
                "pyramidal neuron");

        // alone, should not get printed
        createAnnot(jCas, Morphology.class, 34, 43, "pyramidal");

        createAnnot(jCas, Size.class, 55, 60, "large");
        createAnnot(jCas, NeuronProperty.class, 61, 65, "glia");
        createAnnot(jCas, NeuronTrigger.class, 66, 70, "cell");
        createAnnot(jCas, Neuron.class, 55, 70, "large glia cell");

        String tmp = new File("target/NeuronWriterTest2_" + currentTimeMillis()
                + ".tsv").getAbsolutePath();
        runPipeline(jCas,
                createEngine(NeuronWriter2.class, PARAM_OUTPUT_FILE, tmp));

        List<String> output = linesFrom(tmp);
        assertEquals(5, output.size());
        assertEquals("17\t1\t1\t10\t19\tMorphology\tpyramidal", output.get(0));
        assertEquals("17\t2\t2\t66\t70\tNeuron\tcell", output.get(4));
    }
}
