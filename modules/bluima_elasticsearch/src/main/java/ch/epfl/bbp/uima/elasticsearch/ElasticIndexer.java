package ch.epfl.bbp.uima.elasticsearch;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static org.apache.uima.fit.util.JCasUtil.indexCovered;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.elasticsearch.common.settings.ImmutableSettings.settingsBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkItemResponse.Failure;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Cooccurrence;
import ch.epfl.bbp.uima.types.DictTerm;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Protein;
import de.julielab.jules.types.Sentence;

/**
 * Indexes every sentences into ElasticSearch, a RESTful search engine server.
 * Also indexes some entities and co-occurrences.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class ElasticIndexer extends JCasAnnotator_ImplBase {
    protected static Logger LOG = LoggerFactory.getLogger(ElasticIndexer.class);

    private static final int FLUSH_EVERY = 100;
    public static final String FIELD_TYPE = "type_";
    public static final String FIELD_ID = "id";
    public static final String FIELD_TEXT = "text";

    public static final String PARAM_HOST = "host";
    @ConfigurationParameter(name = PARAM_HOST, defaultValue = "localhost", //
    mandatory = false, description = "the ES host name, use '128.178.187.160' for desktop")
    protected String host;

    public static final String PARAM_PORT = "port";
    @ConfigurationParameter(name = PARAM_PORT, defaultValue = "9300", //
    mandatory = false, description = "the ES port name, defaults to 9300")
    protected int port;

    public static final String PARAM_CLUSTER_NAME = "clusterName";
    @ConfigurationParameter(name = PARAM_CLUSTER_NAME, description = "the ES cluster name")
    protected String clusterName;

    public static final String PARAM_INDEX_NAME = "indexName";
    @ConfigurationParameter(name = PARAM_INDEX_NAME, description = "the ES index name")
    protected String indexName;

    protected Client client;
    protected BulkRequestBuilder bulkRequest;

    @SuppressWarnings("resource")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        Settings settings = settingsBuilder().put("cluster.name", clusterName)
                .build();
        client = new TransportClient(settings)
                .addTransportAddress(new InetSocketTransportAddress(host, port));
        if (((TransportClient) client).connectedNodes().size() == 0) {
            throw new RuntimeException(
                    "Could not connect to ES cluster, using host: " + host);
        }
        bulkRequest = client.prepareBulk();
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            for (IndexRequestBuilder request : toRequest(jCas, client,
                    indexName)) {
                bulkRequest.add(request);
            }
            maybeFlushBulk();
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }

    private int cnt = 0;

    private void maybeFlushBulk() {
        if (++cnt % FLUSH_EVERY == 0) {
            long start = currentTimeMillis();
            int sentencesCnt = bulkRequest.numberOfActions();
            flushBulk();
            bulkRequest = client.prepareBulk(); // new one
            LOG.debug("flushed index  with {} sentences, took {}ms", //
                    sentencesCnt, (currentTimeMillis() - start));
        }
    }

    private void flushBulk() {
        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            for (BulkItemResponse r : bulkResponse.getItems()) {
                LOG.error(r.getFailureMessage());
                Failure failure = r.getFailure();
                // e.g. when ES server is overloaded
                throw new ElasticsearchException(failure.toString());
            }
        }
    }

    protected List<IndexRequestBuilder> toRequest(JCas jCas, Client client,
            String indexName) throws IOException {
        List<IndexRequestBuilder> requests = newArrayList();

        int pmId = getHeaderIntDocId(jCas);

        // entities indexes **********************
        Map<Sentence, Collection<Protein>> protIdx = indexCovered(jCas,
                Sentence.class, Protein.class);
        Map<Sentence, Collection<BrainRegion>> brIdx = indexCovered(jCas,
                Sentence.class, BrainRegion.class);
        Map<Sentence, Collection<DictTerm>> dictTermIdx = indexCovered(jCas,
                Sentence.class, DictTerm.class);
        Map<Sentence, Collection<Measure>> measuresIdx = indexCovered(jCas,
                Sentence.class, Measure.class);
        Map<Sentence, Collection<Cooccurrence>> coocIdx = indexCovered(jCas,
                Sentence.class, Cooccurrence.class);
        // end entities indexes **********************

        for (Sentence s : select(jCas, Sentence.class)) {
            int sentenceId = s.getBegin();

            XContentBuilder doc = jsonBuilder().startObject();
            doc.field(FIELD_TEXT, s.getCoveredText());
            doc.field("pmId", pmId);
            doc.field("sentenceId", sentenceId);

            // entities **********************
            doc.startArray("entities");
            if (dictTermIdx.containsKey(s)) {
                for (DictTerm dt : dictTermIdx.get(s)) {
                    doc.startObject();
                    Pair<String, Integer> id = getId(dt);
                    doc.field(FIELD_TYPE, id.getKey());
                    doc.field(FIELD_ID, id.getValue());
                    doc.field("start", dt.getBegin());
                    doc.field("end", dt.getEnd());
                    doc.endObject();
                }
            }
            if (protIdx.containsKey(s)) {
                for (Protein prot : protIdx.get(s)) {
                    doc.startObject();
                    doc.field(FIELD_TYPE, "protein");
                    doc.field("id_string", prot.getCoveredText());// TODO get id
                    doc.field("start", prot.getBegin());
                    doc.field("end", prot.getEnd());
                    doc.endObject();
                }
            }
            if (brIdx.containsKey(s)) {
                for (BrainRegion br : brIdx.get(s)) {
                    doc.startObject();
                    doc.field(FIELD_TYPE, "brainer");
                    doc.field("id_string", br.getCoveredText());// TODO get id
                    doc.field("start", br.getBegin());
                    doc.field("end", br.getEnd());
                    doc.endObject();
                }
            }
            if (measuresIdx.containsKey(s)) {
                for (Measure m : measuresIdx.get(s)) {
                    if (m.getUnit() != null) { // only when units
                        doc.startObject();
                        doc.field(FIELD_TYPE, "measure");
                        // TODO get id
                        doc.field("id_string", m.getNormalizedUnit());
                        doc.field("start", m.getBegin());
                        doc.field("end", m.getEnd());
                        doc.endObject();
                    }
                }
            }
            doc.endArray();
            // end entities **********************

            // cooccurrences **********************
            doc.startArray("cooccurrences");
            if (coocIdx.containsKey(s)) {
                for (Cooccurrence cooc : coocIdx.get(s)) {
                    // FIXME for now only aba br-br
                    if (cooc.getCooccurrenceType().equals("br-aba-syn-jsre")) {
                        doc.startObject();
                        doc.field(FIELD_TYPE, cooc.getCooccurrenceType());
                        doc.field("start1", cooc.getFirstEntity().getBegin());
                        doc.field("end1", cooc.getFirstEntity().getEnd());
                        // FIXME this is hardcoded for br-br coocs
                        Pair<String, Integer> id1 = getId(cooc.getFirstEntity());
                        doc.field("type1", id1.getKey());
                        doc.field("id1", id1.getValue());
                        // // FIXME ids should be split in type, [int] id
                        // for (int i = 0; i < cooc.getFirstIds().size(); i++) {
                        // doc.field("first_id_" + i, cooc.getFirstIds(i));
                        // }
                        doc.field("start2", cooc.getSecondEntity().getBegin());
                        doc.field("end2", cooc.getSecondEntity().getEnd());
                        // for (int i = 0; i < cooc.getSecondIds().size(); i++)
                        // {
                        // doc.field("second_id_" + i, cooc.getSecondIds(i));
                        // }
                        // doc.field("second_id", getId(cooc.getFirstEntity()));
                        // FIXME this is hardcoded for br-br coocs
                        Pair<String, Integer> id2 = getId(cooc
                                .getSecondEntity());
                        doc.field("type2", id2.getKey());
                        doc.field("id2", id2.getValue());

                        doc.field("confidence", cooc.getConfidence());
                        doc.endObject();
                    }
                }
            }
            doc.endArray();
            // end cooccurrences **********************

            // System.out.println(doc.string());

            String id = pmId + "_" + sentenceId;
            requests.add(client.prepareIndex(indexName, "sentence", id)
                    .setSource(doc));
        }
        return requests;
    }

    /** e.g. aba:123 or subcell:5883 */
    static Pair<String, Integer> getId(Annotation annot) {

        if (annot instanceof DictTerm) {
            DictTerm dt = (DictTerm) annot;
            String[] split = dt.getEntityId().split(":");
            return Pair.of(split[0], parseInt(split[1]));

            // } else if (annot instanceof Measure) {
            // Measure m = (Measure) annot;
            // return Pair.of(""+m.getNormalizedUnit(), parseInt(split[1]));

        } else
            throw new RuntimeException("NOT IMPLEMENTED xxx");// FIXME
    }

    @Override
    public void destroy() {
        super.destroy();
        if (bulkRequest.numberOfActions() > 0) {
            flushBulk();
        }
        client.close();
    }
}
