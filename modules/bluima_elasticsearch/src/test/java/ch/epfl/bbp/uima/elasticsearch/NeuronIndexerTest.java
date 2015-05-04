package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;
import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_CLUSTER_NAME;
import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_HOST;
import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_INDEX_NAME;
import static ch.epfl.bbp.uima.elasticsearch.ElasticIndexer.PARAM_PORT;
import static ch.epfl.bbp.uima.elasticsearch.NeuronIndexer.PARAM_ONLY_INDEX_SENTENCES_WITH_NEURONS;
import static ch.epfl.bbp.uima.testutils.UimaTests.createAnnot;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescriptionFromPath;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import neuroner.NeuroNER.BrainRegionProp;
import neuroner.NeuroNER.Layer;
import neuroner.NeuroNER.Missing;
import neuroner.NeuroNER.Morphology;
import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronProperty;
import neuroner.NeuroNER.NeuronTrigger;

import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.ae.DotSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.KeepLargestAnnotationAnnotator;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.ae.output.AnnotationTypeWriter2;
import ch.epfl.bbp.uima.cr.OneDocPerLineReader2;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import ch.epfl.bbp.uima.uimafit.SimplePipelineBuilder;

@Ignore
public class NeuronIndexerTest {

	@Test
	public void test() throws Exception {

		// INDEXING
		String indexName = "test_index" + System.currentTimeMillis();
		String clusterName = "elasticsearch_neuroner";

		JCas jCas = getTestCas("many layer V prefrontal cortical pyramidal neurons");
		BlueCasUtil.setDocId(jCas, 2);

		createAnnot(jCas, Layer.class, 5, 12, "layer V");
		createAnnot(jCas, BrainRegionProp.class, 13, 32, "prefrontal cortical");
		createAnnot(jCas, Morphology.class, 33, 42, "pyramidal");
		createAnnot(jCas, NeuronTrigger.class, 43, 50, "neurons");
		createAnnot(jCas, Neuron.class, 5, 50,
				"layer V prefrontal cortical pyramidal neurons");

		JcasPipelineBuilder builder = new JcasPipelineBuilder(jCas);
		builder.add(NaiveSentenceSplitterAnnotator.class);
		builder.add(NeuronIndexer.class, PARAM_INDEX_NAME, indexName,
				PARAM_CLUSTER_NAME, clusterName);
		builder.process(true);
	}

	@Test
	public void testIgnoreSentencesWithoutNeuron() throws Exception {

		// INDEXING
		String indexName = "neuroner_20150504_test";
		String clusterName = "elasticsearch_neuroner";
		String host = "128.178.xx";

		JCas jCas = getTestCas("Many layer V prefrontal cortical pyramidal neurons. Another sentence here.");
		BlueCasUtil.setDocId(jCas, 2);

		createAnnot(jCas, Layer.class, 5, 12, "layer V");
		createAnnot(jCas, BrainRegionProp.class, 13, 32, "prefrontal cortical");
		createAnnot(jCas, Morphology.class, 33, 42, "pyramidal");
		createAnnot(jCas, NeuronTrigger.class, 43, 50, "neurons");
		createAnnot(jCas, Neuron.class, 5, 50,
				"layer V prefrontal cortical pyramidal neurons");

		JcasPipelineBuilder builder = new JcasPipelineBuilder(jCas);
		builder.add(DotSentenceSplitterAnnotator.class);
		builder.add(NeuronIndexer.class, PARAM_INDEX_NAME, indexName,
				PARAM_CLUSTER_NAME, clusterName, PARAM_HOST, host,
				PARAM_ONLY_INDEX_SENTENCES_WITH_NEURONS, true);
		builder.process(true);
	}

	@Test
	public void test2() throws Exception {

		// INDEXING
		String indexName = "my_test";
		String clusterName = "elasticsearch_neuroner";

		JCas jCas = getTestCas("many layer 3 glia neurons");
		BlueCasUtil.setDocId(jCas, 4);

		createAnnot(jCas, Layer.class, 5, 12, "layer 3");
		createAnnot(jCas, BrainRegionProp.class, 13, 17, "glia");
		createAnnot(jCas, NeuronTrigger.class, 18, 25, "neurons");
		createAnnot(jCas, Neuron.class, 5, 25, "layer 3 glia neurons");

		JcasPipelineBuilder builder = new JcasPipelineBuilder(jCas);
		builder.add(NaiveSentenceSplitterAnnotator.class);
		builder.add(NeuronIndexer.class, PARAM_INDEX_NAME, indexName,
				PARAM_CLUSTER_NAME, clusterName);
		builder.process(true);
	}

	@Test
	public void testRuta() throws Exception {

		String indexName = "neuroner_20141029";
		String clusterName = "elasticsearch_neuroner_dev";
		String host = "localhost";// "128.178.187.160";
		int port = 9300;// 9301;

		String overlap = "It is concluded that the P2X3 receptor subunit is expressed in specific functional groups of neurons; the major types are excitatory and inhibitory muscle motor neurons, ascending interneurons and cholinergic secretomotor neurons.";

		PipelineBuilder builder = new SimplePipelineBuilder(
				// createReaderDescription(TextArrayReader.class,
				// BlueUima.PARAM_INPUT,overlap));
				createReaderDescription(OneDocPerLineReader2.class,
						PARAM_INPUT_FILE,
						// "/Volumes/HDD2/ren_data/data_hdd/__mycorpora/1m_ns/1m_ns.abstracts_sample.tsv"));
						"/Volumes/HDD2/ren_data/data_hdd/__mycorpora/1m_ns/1m_ns.abstracts.tsv"));

		builder.add(createEngineDescriptionFromPath("/Users/richarde/dev/bluebrain/git/neuroNER/descriptor/neuroner/NeuroNEREngine.xml"));
		builder.add(KeepLargestAnnotationAnnotator.class,
				PARAM_ANNOTATION_CLASS, Neuron.class.getName());
		builder.add(KeepLargestAnnotationAnnotator.class,
				PARAM_ANNOTATION_CLASS, NeuronProperty.class.getName());

		builder.add(OpenNlpHelper.getSentenceSplitter());
		builder.add(NeuronIndexer.class, PARAM_INDEX_NAME, indexName,
				PARAM_CLUSTER_NAME, clusterName, PARAM_HOST, host, PARAM_PORT,
				port);

		// print Missings
		builder.add(AnnotationTypeWriter2.class, PARAM_ANNOTATION_CLASS,
				Missing.class, PARAM_OUTPUT_FILE,
				"target/20141028_Missings.tsv");

		builder.add(createEngineDescription(StatsAnnotatorPlus.class,
				PARAM_PRINT_EVERY, 1000));
		builder.process();
	}
}
