package ch.epfl.bbp.uima.mongo;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

/**
 * A wrapper for a connection to Mongo.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoConnection {

	final public String host, dbName, collectionName, user, pw;
	final public Mongo m;
	final public DB db;
	final public DBCollection coll;

	/**
	 * @param db_connection
	 *            an array {host, dbName, collectionName, user, pw}. Leave user
	 *            and pw empty ("") to skip authentication
	 */
	public MongoConnection(String[] db_connection) throws UnknownHostException,
			MongoException {
		this(db_connection, true);
	}

	/**
	 * @param db_connection
	 *            an array {host, dbName, collectionName, user, pw}. Leave user
	 *            and pw empty ("") to skip authentication
	 */
	public MongoConnection(String[] db_connection, boolean safe)
			throws UnknownHostException, MongoException {

		checkArgument(db_connection.length == 5,
				"Should be: host, dbname, collectionname, user, pw but lengh = "
						+ db_connection.length);

		host = db_connection[0];
		dbName = db_connection[1];
		collectionName = db_connection[2];
		user = db_connection[3];
		pw = db_connection[4];

		checkNotNull(host, "host is NULL");
		checkNotNull(dbName, "dbName is NULL");
		checkNotNull(collectionName, "collectionName is NULL");
		checkNotNull(user, "user is NULL");
		checkNotNull(pw, "pw is NULL");

		m = new Mongo(host, 27017);
		if (safe)
			m.setWriteConcern(WriteConcern.SAFE);
		m.getDatabaseNames();// to test connection
		db = m.getDB(dbName);
		if (user.length() > 0) {
			if (!db.authenticate(user, pw.toCharArray())) {
				throw new MongoException(-1, "cannot login with user " + user);
			}
		}
		coll = db.getCollection(collectionName);
	}

	@Override
	public String toString() {
		return "MongoConnection: " + host + ":" + dbName + "::"
				+ collectionName;
	}
}
