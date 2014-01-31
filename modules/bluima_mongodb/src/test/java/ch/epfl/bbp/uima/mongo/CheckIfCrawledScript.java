package ch.epfl.bbp.uima.mongo;

import static java.lang.Integer.parseInt;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import ch.epfl.bbp.io.SVReader.TSVReader;

import com.mongodb.BasicDBObject;

/**
 * Check if the provided PmIds have already been crawled
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CheckIfCrawledScript {

    public static final String[] MONGO_FT_CONNECTION = { "128.178.187.248",
            "pubmed_uima", "pm_ft", "", "" };

    public static void main(String[] args) throws Exception {

        Iterator<List<String>> tsv = new TSVReader(
                new File(
                        "/Users/richarde/Desktop/BBP_experiments/16_parse_srikanth_tables/ColumnSynapticDynamics_v1.references.tsv"),
                true).iterator();

        MongoConnection mongo = new MongoConnection(MONGO_FT_CONNECTION);

        while (tsv.hasNext()) {

            try {
                int pmId = parseInt(tsv.next().get(6));
                System.out.print(pmId + "\t");
                if (mongo.coll.findOne(new BasicDBObject("_id", pmId + "")) == null) {
                    System.out.println("false");
                } else {
                    System.out.println("true");
                }
            } catch (Exception e) {
                System.out.println("NA");
            }
        }
    }
}
