package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ALL_MAPPINGS;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ALL_MAPPINGS_KEYS;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.BEGIN;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.END;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ID;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.TEXT;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.TITLE;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueUima;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.Bytes;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import de.julielab.jules.types.pubmed.Header;

/**
 * Reads CASes from Mongo
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoCollectionReader extends JCasCollectionReader_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(MongoCollectionReader.class);

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, mandatory = true, //
    description = "host, dbname, collectionname, user, pw")
    protected String[] db_connection;
    protected DBCursor cur;

    @ConfigurationParameter(name = BlueUima.PARAM_QUERY, //
    description = "a mongo query, e.g. {my_db_field:{$exists:true}} or {ftr.ns:1} or {pmid: 17} "
            + "or {pmid:{$in:[12,17]}} or {pmid:{ $gt: 8, $lt: 11 }} ")
    private String query = null;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            MongoConnection conn = new MongoConnection(db_connection);
            initQuery(conn);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    protected void initQuery(MongoConnection conn) throws IOException {
        if (query != null)
            cur = conn.coll.find((DBObject) JSON.parse(query));
        else
            cur = conn.coll.find();
        cur.addOption(Bytes.QUERYOPTION_NOTIMEOUT).batchSize(1000);
    }

    @Override
    public boolean hasNext() throws IOException, CollectionException {
        return cur.hasNext();
    }

    @Override
    public void getNext(JCas jCas) throws IOException, CollectionException {

        // text & id
        DBObject doc = cur.next();
        Object text = doc.get(TEXT);
        if (text != null)
            jCas.setDocumentText(doc.get(TEXT).toString());
        else
            jCas.setDocumentText("");
        Header h = new Header(jCas);
        h.setDocId(doc.get(ID).toString());
        if (doc.containsField(TITLE) && doc.get(TITLE) != null)
            h.setTitle(doc.get(TITLE).toString());
        else
            h.setTitle("");
        h.addToIndexes();

        // all other annotations, from mappings
        for (String dbListsName : doc.keySet()) {

            for (String annotClass : ALL_MAPPINGS_KEYS) {
                MongoFieldMapping fm = ALL_MAPPINGS.get(annotClass);

                if (fm.shortName.equals(dbListsName)) {

                    BasicDBList dbList = (BasicDBList) doc.get(dbListsName);
                    for (Object o : dbList) {
                        BasicDBObject dbO = (BasicDBObject) o;

                        try {
                            Annotation a = getAnnotationByClassName(jCas,
                                    annotClass);
                            a.setBegin(dbO.getInt(BEGIN));// LATER maybe opt.
                            a.setEnd(dbO.getInt(END));

                            Type t = a.getType();
                            for (Feature f : t.getFeatures()) {
                                // System.err.println("f.short "
                                // + f.getShortName());

                                if (fm.fieldMappings.containsKey(f
                                        .getShortName())) {

                                    String fieldKey = fm.fieldMappings.get(f
                                            .getShortName());
                                    String range = f.getRange().getShortName();

                                    MongoFieldMapping.readFieldFromDb(fieldKey,
                                            range, a, f, dbO, jCas);
                                }
                            }
                            a.addToIndexes();

                        } catch (Exception e) {
                            LOG.error("while processing docId " + doc.get(ID),
                                    e);
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static Map<String, Constructor> CACHE = new HashMap<String, Constructor>();

    /**
     * Returns an annotation object (de.julielab.jules.types.annotation) of the
     * type specified by fullEntityClassName. This is done by means of dynamic
     * class loading and reflection.
     * 
     * @param aJCas
     *            the jcas to which to link this annotation object
     * @param fullAnnotationClassName
     *            the full class name of the new annotation object
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Annotation getAnnotationByClassName(JCas aJCas,
            String fullAnnotationClassName) throws ClassNotFoundException,
            SecurityException, NoSuchMethodException, IllegalArgumentException,
            InstantiationException, IllegalAccessException,
            InvocationTargetException {

        // was slow on jvisualvm profiler
        if (!CACHE.containsKey(fullAnnotationClassName)) {
            Class[] parameterTypes = new Class[] { JCas.class };
            Class myNewClass = Class.forName(fullAnnotationClassName);
            Constructor myConstructor = myNewClass
                    .getConstructor(parameterTypes);
            CACHE.put(fullAnnotationClassName, myConstructor);
        }
        Constructor constructor = CACHE.get(fullAnnotationClassName);
        Annotation anno = (Annotation) constructor.newInstance(aJCas);
        return anno;
    }

    @Override
    public Progress[] getProgress() {// nope
        return null;
    }
}
