package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.PM_ID;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * Reads CASes from Mongo, with pmid between certain values
 * 
 * @author renaud.richardet@epfl.ch
 */
public class BetweenMongoCollectionReader extends MongoCollectionReader {
	protected static final Logger LOG = LoggerFactory
			.getLogger(BetweenMongoCollectionReader.class);
	@ConfigurationParameter(name = PARAM_BETWEEN, description = "specifies a range of pubmed_id, e.g. {13,17} --> 13 <= pubmed_id <= 17")
	private int[] btw;

	protected void initQuery(MongoConnection conn) throws IOException {

		checkArgument(btw.length == 2, "PARAM_BETWEEN should be length 2, "
				+ btw);
		checkArgument(btw[0] < btw[1],
				"PARAM_BETWEEN[0] should be < PARAM_BETWEEN[1], " + btw);

		cur = conn.coll.find(
				(DBObject) JSON.parse(//
						"{ \"" + PM_ID + "\": {\"$gt\": " + btw[0]
								+ ", \"$lt\": " + btw[1] + " } }"))//
				.batchSize(1000);
	}
}
