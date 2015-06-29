package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.CasUtil.indexCovered;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import neuroner.NeuroNER.Neuron;
import neuroner.NeuroNER.NeuronProperty;
import neuroner.NeuroNER.NeuronTrigger;

import org.apache.lucene.document.Field;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import de.julielab.jules.types.AuthorInfo;
import de.julielab.jules.types.Header;

/**
 * Indexes every sentences and their corresponding neuron entities into
 * ElasticSearch.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class NeuronIndexer2 extends NeuronIndexer {

    // Lucene field definitions
    /** {@link Field} name for the whole abstract/document */
    public static final String FIELD_TEXT = "abstract_text";
    public static final String FIELD_AUTHORS = "authors";
    public static final String FIELD_PUBLISHED_DATE = "published_date";

    /** Indexes neuron and properties at the sentence level. */
    @Override
    protected List<IndexRequestBuilder> toRequest(JCas jCas, Client client,
            String indexName) throws IOException {
        List<IndexRequestBuilder> requests = newArrayList();

        String pmId = getHeaderDocId(jCas);

        try {
            Collection<Neuron> neurons = select(jCas, Neuron.class);

            // UIMA CAS indexes so that we build them once for all sentences.
            Map<AnnotationFS, Collection<AnnotationFS>> idxNeuronProperties = indexCovered(
                    jCas.getCas(), //
                    getType(jCas, Neuron.class),
                    getType(jCas, NeuronProperty.class));
            Map<AnnotationFS, Collection<AnnotationFS>> idxNeurons = indexCovered(
                    jCas.getCas(), //
                    getType(jCas, Neuron.class),
                    getType(jCas, NeuronTrigger.class));

            // only index abstracts that contain a neuron
            if (!(onlyIndexNeurons && neurons.isEmpty())) {

                final XContentBuilder doc = jsonBuilder().startObject();
                doc.field(FIELD_PUBMED_ID, pmId);
                doc.field(FIELD_SENTENCE_TEXT, jCas.getDocumentText());
                // authors & date
                try {
                    List<String> authors = new ArrayList<>();
                    Header header = selectSingle(jCas, Header.class);
                    for (int i = 0; i < header.getAuthors().size(); i++) {
                        AuthorInfo a = header.getAuthors(i);
                        authors.add(a.getForeName() + " " + a.getLastName());
                    }
                    doc.field(FIELD_AUTHORS, authors);

                    String pubDate = header.getCopyright();// 1976-01-16
                    if (pubDate.indexOf("-") > -1) {
                        doc.field(FIELD_PUBLISHED_DATE, pubDate);
                    }
                } catch (Exception e) {// nope
                    LOG.debug("failed to index authors or date for {}: {}",
                            pmId, e.toString());
                }

                // INDEX NEURONS
                doc.startArray("neuron");
                for (Neuron nwp : neurons) {
                    doc.startObject();
                    doc.field(FIELD_NEURON_TEXT, nwp.getCoveredText());
                    doc.field(FIELD_START, nwp.getBegin());
                    doc.field(FIELD_END, nwp.getEnd());

                    // properties
                    doc.startArray("neuron_properties");
                    // neuron(trigger), handle as it were a property
                    NeuronTrigger n = ((NeuronTrigger) (idxNeurons.get(nwp)
                            .iterator().next()));
                    doc.startObject();
                    doc.field(FIELD_NEURON_TYPE, getName(n));
                    doc.field(FIELD_PROPERTY_TEXT, n.getCoveredText());
                    doc.field(FIELD_START, n.getBegin());
                    doc.field(FIELD_END, n.getEnd());
                    doc.endObject(); // end 'n' (neuron trigger)

                    // every NeuronProperty
                    if (idxNeuronProperties.containsKey(nwp)) {
                        for (AnnotationFS np : idxNeuronProperties.get(nwp)) {
                            doc.startObject();
                            doc.field(FIELD_NEURON_TYPE, getName(np));
                            doc.field(FIELD_PROPERTY_TEXT, np.getCoveredText());
                            doc.field(FIELD_START, np.getBegin());
                            doc.field(FIELD_END, np.getEnd());
                            try {
                                String id = ((NeuronProperty) np)
                                        .getOntologyId();
                                if (id != null)
                                    doc.field(FIELD_ONTOLOGY_ID, id);
                            } catch (Exception e) {// nope
                            }
                            doc.endObject();
                        }
                    }
                    doc.endArray(); // end 'properties'
                    doc.endObject(); // end nwp
                }
                doc.endArray(); // end 'neuron'

                // INDEX SEPARATE PROPERTIES
                doc.startArray("all_neuron_properties");
                for (NeuronProperty np : select(jCas, NeuronProperty.class)) {
                    doc.startObject();
                    doc.field(FIELD_PROPERTY_TEXT, np.getCoveredText());
                    doc.field(FIELD_NEURON_TYPE, getName(np));
                    doc.field(FIELD_START, np.getBegin());
                    doc.field(FIELD_END, np.getEnd());
                    try {
                        String id = np.getOntologyId();
                        if (id != null)
                            doc.field(FIELD_ONTOLOGY_ID, id);
                    } catch (Exception e) {// nope
                    }
                    doc.endObject();
                }
                doc.endArray();

                String uid = pmId;// + "_" + sentenceStart; // uid for Lucene
                requests.add(client.prepareIndex(indexName, "abstract_", uid)
                        .setSource(doc));

                LOG.trace(doc.string());
            }

        } catch (Exception e) {
            LOG.warn("could not index pmid" + pmId, e);
        }
        return requests;
    }

    private static String getName(AnnotationFS a) {
        return a.getType().getShortName().replaceAll("Prop$", "").toLowerCase();
    }
}
