package ch.epfl.bbp.uima.mongo;

import java.io.File;
import java.io.FileInputStream;

import ch.epfl.bbp.Progress;
import ch.epfl.bbp.io.LineReader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * Removes docs that have been flagged as: <code>
520191  WRONG_PDF
47063   TOO_SHORT_TXT
45321   NOT_ENGLISH
18044   EMPTY_TXT
</code>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RemoveInvalidDocs_Script {

    public static final String[] MONGO_FT_CONNECTION = { "128.178.187.248",
            "preprocessed_2", "20130327_preprocess_ft", "", "" };

    public static void main(String[] args) throws Exception {

        MongoConnection mongo = new MongoConnection(MONGO_FT_CONNECTION);
        File ns_pmIds = new File(
                "/Users/richarde/Desktop/BBP_experiments/27_cleanup_pm-ft_in_mongo/CheckFtAgainstAbstracts.log.s.cut.uniq");
        int[] readInts = LineReader.intsFrom(new FileInputStream(ns_pmIds));

        for (int pmid : readInts) {

            DBObject r = new BasicDBObject("_id", pmid + "");
            mongo.coll.remove(r);
            Progress.tick();
        }

        System.err.println("done :-)");
    }
}
