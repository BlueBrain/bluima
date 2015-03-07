package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.uima.UIMAException;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import de.julielab.jules.types.pubmed.Header;

/**
 * Removes the given annotations from Mongo's CASes. Use it as a collection
 * reader, with no annotator (see
 * {@link MongoCollectionRemover#removeAnnotations()}).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoCollectionRemover extends JCasCollectionReader_ImplBase {
	protected static final Logger LOG = LoggerFactory
			.getLogger(MongoCollectionRemover.class);

	@ConfigurationParameter(name = PARAM_DB_CONNECTION, mandatory = true, //
	description = "host, dbname, collectionname, user, pw")
	private String[] db_connection;
	private DBCollection coll;
	private DBCursor cur;

	public static final String PARAM_DELETE_ANNOTATIONS = "delete_annotations";
	@ConfigurationParameter(name = PARAM_DELETE_ANNOTATIONS, mandatory = true)
	private String[] annotationsToDelete;
	private Set<String> keysToDelete;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		// check deleteAnnotations
		keysToDelete = new HashSet<String>();
		checkArgument(annotationsToDelete.length > 0,
				"please provide at least some delete annotations");

		for (String da : annotationsToDelete) {

			if (MongoFieldMapping.ALL_MAPPINGS_KEYS.contains(da)) {
				keysToDelete
						.add(MongoFieldMapping.ALL_MAPPINGS.get(da).shortName);
			} else {
				throw new ResourceInitializationException(
						NO_RESOURCE_FOR_PARAMETERS, new Object[] {
								" --> no MongoFieldMapping for annotation "
										+ da, null });
			}
		}

		try {
			MongoConnection conn = new MongoConnection(db_connection);
			coll = conn.coll;
			cur = conn.coll.find().batchSize(1000);
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {
		return cur.hasNext();
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {

		DBObject doc = cur.next();

		Header h = new Header(jCas);
		h.setDocId(doc.get("_id").toString());
		h.addToIndexes();

		Map<String, Integer> dbDeleteKeys = new HashMap<String, Integer>();
		for (String deleteKeyName : keysToDelete) {
			if (doc.containsField(deleteKeyName))
				dbDeleteKeys.put(deleteKeyName, 1);
		}

		// insert all dbLists
		BasicDBObject updateQuery = new BasicDBObject("_id", doc.get("_id")
				.toString());
		BasicDBObject updateCommands = new BasicDBObject();
		updateCommands.put("$unset", dbDeleteKeys);
		coll.update(updateQuery, updateCommands, false, false);
	}

	@Override
	public Progress[] getProgress() {// nope
		return null;
	}

	/** Convenience function */
	public static void removeAnnotations(String[] conn,
			String... annotationsToDelete) throws UIMAException, IOException {
		CollectionReader cr = createReader(
				MongoCollectionRemover.class,  PARAM_DB_CONNECTION,
				conn, PARAM_DELETE_ANNOTATIONS, annotationsToDelete);
		AnalysisEngine noAE = AnalysisEngineFactory
				.createEngine(StatsAnnotatorPlus.class);
		SimplePipeline.runPipeline(cr, noAE);
		LOG.debug("done removing annotations");
	}
}
