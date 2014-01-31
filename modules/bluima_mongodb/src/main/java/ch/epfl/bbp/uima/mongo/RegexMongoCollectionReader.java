package ch.epfl.bbp.uima.mongo;

import java.io.IOException;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueUima;

import com.mongodb.BasicDBObject;

/**
 * Reads CASes from Mongo, uses a regex to query docs
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RegexMongoCollectionReader extends MongoCollectionReader {
	protected static final Logger LOG = LoggerFactory
			.getLogger(RegexMongoCollectionReader.class);

	@ConfigurationParameter(name = BlueUima.PARAM_FIELD_NAME, mandatory = true, //
	description = "the name of the field for the query")
	private String field = null;

	@ConfigurationParameter(name = BlueUima.PARAM_QUERY, mandatory = true,//
	description = "a regex on that field value")
	private String pattern;

	protected void initQuery(MongoConnection conn) throws IOException {

		BasicDBObject query = new BasicDBObject(field, Pattern.compile(pattern));
		cur = conn.coll.find(query).batchSize(1000);
	}

}
