package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_CLUSTER_NAME;
import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_INDEX_NAME;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import neuroner.NeuroNER.BrainRegionProp;
import neuroner.NeuroNER.Layer;
import neuroner.NeuroNER.Morphology;
import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronTrigger;

import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import de.julielab.jules.types.AuthorInfo;
import de.julielab.jules.types.Header;

@Ignore
public class NeuronIndexer2Test {

    @Test
    public void test() throws Exception {

        // INDEXING
        String indexName = "test_index" + System.currentTimeMillis();
        String clusterName = "elasticsearch_neuroner_dev";

        JCas jCas = getTestCas("many layer V prefrontal cortical pyramidal neurons");

        Header header = createAnnot(jCas, Header.class, 0, 0);
        header.setDocId("17");
        header.setCopyright("1976-01-16");
        FSArray slots = new FSArray(jCas, 2);
        AuthorInfo ai = new AuthorInfo(jCas);
        ai.setForeName("fn1");
        ai.setLastName("ln1");
        slots.set(0, ai);
        AuthorInfo ai2 = new AuthorInfo(jCas);
        ai2.setForeName("fn2");
        ai2.setLastName("ln2");
        slots.set(1, ai2);
        header.setAuthors(slots);

        createAnnot(jCas, Layer.class, 5, 12, "layer V");
        createAnnot(jCas, BrainRegionProp.class, 13, 32, "prefrontal cortical");
        createAnnot(jCas, Morphology.class, 33, 42, "pyramidal").setOntologyId(
                "testOntoId");
        createAnnot(jCas, NeuronTrigger.class, 43, 50, "neurons");
        createAnnot(jCas, Neuron.class, 5, 50,
                "layer V prefrontal cortical pyramidal neurons");

        JcasPipelineBuilder builder = new JcasPipelineBuilder(jCas);
        builder.add(NaiveSentenceSplitterAnnotator.class);
        builder.add(NeuronIndexer2.class, PARAM_INDEX_NAME, indexName,
                PARAM_CLUSTER_NAME, clusterName);
        builder.process(true);
    }

}
