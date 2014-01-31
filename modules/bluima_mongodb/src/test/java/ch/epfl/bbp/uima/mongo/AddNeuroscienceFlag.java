package ch.epfl.bbp.uima.mongo;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Set;

import ch.epfl.bbp.io.LineReader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * query: {"ftr.ns":1}
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AddNeuroscienceFlag {

    // FIXME set in global place
    public static final String[] MONGO_FT_CONNECTION = { "128.178.187.248",
            // "neuron", "coverage2", "", "" };
            "pubmed_uima", "pm_ft", "", "" };

    public static void main__(String[] args) throws Exception {

        File ns_pmIds = new File(
                System.getProperty("user.home")
                        + "/Dropbox/dev_shared/tmp2/rrrrrabbit_fetchlist_meshNeuron_SUS.txt");
        Set<Integer> ns_pmIds_set = new HashSet<Integer>();
        for (String line : new LineReader(new FileInputStream(ns_pmIds))) {
            ns_pmIds_set.add(Integer.parseInt(line));
        }
        System.out.println("done reading list");

        MongoConnection mongo = new MongoConnection(MONGO_FT_CONNECTION);
        // retrieve only _id
        BasicDBObject keys = new BasicDBObject();
        keys.put("pmid", 1);
        DBCursor it = mongo.coll.find(new BasicDBObject(), keys)
                .batchSize(1000);
        int i = 0, flagged = 0;
        while (it.hasNext()) {
            DBObject dbObject = (DBObject) it.next();
            int pmId = Integer.parseInt(dbObject.get("_id").toString());

            if (ns_pmIds_set.contains(pmId)) {

                MongoUtils.addFlag(pmId, "ns", mongo);

                flagged++;
            }
            if (i++ % 1000 == 0)
                System.err.println("updated " + i + "th, on " + pmId
                        + ", flagged:" + flagged);
        }
        System.err.println("END updated " + i + ", flagged:" + flagged);
    }

}
