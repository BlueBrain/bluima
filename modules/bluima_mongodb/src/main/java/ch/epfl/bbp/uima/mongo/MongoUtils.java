package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ID;

import com.mongodb.BasicDBObject;

/**
 * @author renaud.richardet@epfl.ch
 */
public class MongoUtils {

    public static void addFlag(int pmid, String flag, MongoConnection mongo) {
        BasicDBObject updateQuery = new BasicDBObject(ID, pmid + "");
        BasicDBObject fList = new BasicDBObject(MongoFieldMapping.FILTER,
                new BasicDBObject(flag, 1));
        mongo.coll.update(updateQuery, new BasicDBObject("$push", fList), true,
                false);
    }
}