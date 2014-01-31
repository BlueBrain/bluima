package ch.epfl.bbp.uima.elasticsearch;

import static com.google.common.collect.Maps.newHashMap;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;

public class I2 {

    public static void main(String[] args) throws Exception {

        // on startup
        Node node = nodeBuilder().client(true)
                .clusterName("elasticsearch_framesearch").node();
        Client client = node.client();

        // index(client);
         indexBulk(client);
        
        // on shutdown
        node.close();
    }

    private static void indexBulk(Client client) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        // either use client#prepare, or use Requests# to directly build
        // index/delete requests
        bulkRequest.add(client.prepareIndex("twitter", "tweet", "3").setSource(
                jsonBuilder().startObject().field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "trying out Elastic Search 3")
                        .endObject()));

        bulkRequest.add(client.prepareIndex("twitter", "tweet", "2").setSource(
                jsonBuilder().startObject().field("user", "kimchy")
                        .field("postDate", new Date())
                        .field("message", "another post").endObject()));

        BulkResponse bulkResponse = bulkRequest.execute().actionGet();
        if (bulkResponse.hasFailures()) {
            // process failures by iterating through each bulk response item
        }

    }
}
