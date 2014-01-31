package ch.epfl.bbp.uima.elasticsearch;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;

public class I3 {

    public static void main(String[] args) throws Exception {

        Node node = nodeBuilder().client(true)
                .clusterName("elasticsearch_framesearch").node();
        Client client = node.client();

        BulkRequestBuilder bulkRequest = client.prepareBulk();
        XContentBuilder doc = jsonBuilder().startObject();

        doc.field("text", "la la li la la");
        doc.field("pmId", 121);
        doc.field("sentenceId", 121243);

        // entities **********************
        doc.startArray("entities");

        doc.startObject();
        doc.field("type_", "aba");
        doc.field("id", 8);
        doc.field("start", 12);
        doc.field("end", 13);
        doc.endObject();

        doc.startObject();
        doc.field("type_", "aba");
        doc.field("id", 9);
        doc.field("start", 13);
        doc.field("end", 14);
        doc.endObject();
        doc.endArray();

        bulkRequest.add(client.prepareIndex("test2", "sentence", "3456")
                .setSource(doc));
        bulkRequest.execute().actionGet();
        node.close();
    }
}
