package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.PM_ID;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class UpdateMongePmids {

	// FIXME set in global place
	public static final String[] MONGO_FT_CONNECTION = { "128.178.187.248",
			"pubmed_uima", "pm_ft", "", "" };

	public static void main2(String[] args) throws Exception {

		MongoConnection mongo = new MongoConnection(MONGO_FT_CONNECTION);
		int cntSum = 0;
		for (int i = 0; i <= 99; i++) {
			int cnt = mongo.coll.find(
					(DBObject) JSON.parse("{ \"pmid\": {\"$gt\": \"" + i
							+ "\", \"$lt\": \"" + (i + 1) + "\" } }")).count();
			System.err.println(i + "\t" + cnt);
			cntSum += cnt;
		}
		System.err.println("sum " + cntSum);
	}

	public static void main(String[] args) throws Exception {

		MongoConnection mongo = new MongoConnection(MONGO_FT_CONNECTION);

		DBCursor it = mongo.coll.find().batchSize(1000);

		int i = 0;
		while (it.hasNext()) {
			DBObject dbObject = (DBObject) it.next();
			int pmId = Integer.parseInt(dbObject.get("_id").toString());
			// dbObject.put(MongoFieldMapping.PM_ID, pmId);
			mongo.coll.update(dbObject, new BasicDBObject("$set",
					new BasicDBObject(PM_ID, pmId)));
			// mongo.coll.apply(dbObject);
			if (i++ % 1000 == 0)
				System.err.println("updated " + pmId);
		}

	}
}
