package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.CasUtil.indexCovered;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronProperty;
import neuroner.NeuroNER.NeuronTrigger;

import org.apache.lucene.document.Field;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.julielab.jules.types.Sentence;

/**
 * Indexes every sentences and their corresponding neuron entities into
 * ElasticSearch.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class NeuronIndexer extends ElasticIndexer {

	// Lucene field definitions
	/** {@link Field} name for the whole ({@link Sentence} */
	public static final String FIELD_SENTENCE_TEXT = "sentence_text";
	/** {@link Field} name for the whole neuron ({@link Neuron} */
	public static final String FIELD_NEURON_TEXT = "neuron_text";
	/** {@link Field} name for the neuron type ({@link NeuronProperty} class) */
	public static final String FIELD_NEURON_TYPE = "neuron_type";
	/** {@link Field} name for neuron properties ( {@link NeuronProperty} */
	public static final String FIELD_PROPERTY_TEXT = "property_text";
	/** {@link Field} name for start of an annotation */
	public static final String FIELD_START = "start";
	/** {@link Field} name for sentence id, = start (begin) of sentence */
	public static final String FIELD_SENTENCE_ID = "sentence_id";
	/** {@link Field} name for PubMed id of sentence */
	public static final String FIELD_PUBMED_ID = "pm_id";
	/** {@link Field} name for end of an annotation */
	public static final String FIELD_END = "end";

	public static final String PARAM_ONLY_INDEX_SENTENCES_WITH_NEURONS = "onlyIndexNeurons";
	@ConfigurationParameter(name = PARAM_ONLY_INDEX_SENTENCES_WITH_NEURONS, defaultValue = "false", //
	mandatory = false, description = "whether to only index sentences that have neurons in it. else index all sentences.")
	private boolean onlyIndexNeurons;

	/** Indexes neuron and properties at the sentence level. */
	@Override
	protected List<IndexRequestBuilder> toRequest(JCas jCas, Client client,
			String indexName) throws IOException {
		List<IndexRequestBuilder> requests = newArrayList();

		String pmId = getHeaderDocId(jCas);

		try {
			// UIMA CAS indexes so that we build them once for all sentences.
			Map<AnnotationFS, Collection<AnnotationFS>> idxNeuron = indexCovered(
					jCas.getCas(), //
					getType(jCas, Sentence.class), getType(jCas, Neuron.class));
			Map<AnnotationFS, Collection<AnnotationFS>> idxNeuronProperties = indexCovered(
					jCas.getCas(), //
					getType(jCas, Neuron.class),
					getType(jCas, NeuronProperty.class));
			Map<AnnotationFS, Collection<AnnotationFS>> idxNeurons = indexCovered(
					jCas.getCas(), //
					getType(jCas, Neuron.class),
					getType(jCas, NeuronTrigger.class));

			for (Sentence s : select(jCas, Sentence.class)) {

				// only index sentences that contain a neuron
				if (!(onlyIndexNeurons && idxNeuron.get(s).isEmpty())) {

					int sentenceStart = s.getBegin();

					final XContentBuilder doc = jsonBuilder().startObject();
					doc.field(FIELD_PUBMED_ID, pmId);
					doc.field(FIELD_SENTENCE_TEXT, s.getCoveredText());
					doc.field(FIELD_SENTENCE_ID, sentenceStart);

					doc.startArray("neuron");

					for (AnnotationFS nwp_ : idxNeuron.get(s)) {
						Neuron nwp = (Neuron) nwp_;
						doc.startObject();
						doc.field(FIELD_NEURON_TEXT, nwp.getCoveredText());
						doc.field(FIELD_START, nwp.getBegin() - sentenceStart);
						doc.field(FIELD_END, nwp.getEnd() - sentenceStart);

						// properties
						doc.startArray("neuron_properties");
						// neuron(trigger), handle as it were a property
						NeuronTrigger n = ((NeuronTrigger) (idxNeurons.get(nwp)
								.iterator().next()));
						doc.startObject();
						doc.field(FIELD_NEURON_TYPE, n.getType().getShortName()
								.replaceAll("Prop$", ""));
						doc.field(FIELD_PROPERTY_TEXT, n.getCoveredText());
						doc.field(FIELD_START, n.getBegin() - sentenceStart);
						doc.field(FIELD_END, n.getEnd() - sentenceStart);
						doc.endObject(); // end 'n' (neuron trigger)

						// every NeuronProperty
						if (idxNeuronProperties.containsKey(nwp)) {
							for (AnnotationFS np : idxNeuronProperties.get(nwp)) {
								doc.startObject();
								doc.field(FIELD_NEURON_TYPE, np.getType()
										.getShortName().replaceAll("Prop$", ""));
								doc.field(FIELD_PROPERTY_TEXT,
										np.getCoveredText());
								doc.field(FIELD_START, np.getBegin()
										- sentenceStart);
								doc.field(FIELD_END, np.getEnd()
										- sentenceStart);
								doc.endObject();
							}
						}
						doc.endArray(); // end 'properties'
						doc.endObject(); // end nwp
					}
					doc.endArray(); // end 'neuron'

					String uid = pmId + "_" + sentenceStart; // uid for Lucene
					requests.add(client
							.prepareIndex(indexName, "sentence", uid)
							.setSource(doc));

					LOG.trace(doc.string());
				}
			}
		} catch (Exception e) {
			LOG.warn("could not index pmid" + pmId, e);
		}
		return requests;
	}
}
