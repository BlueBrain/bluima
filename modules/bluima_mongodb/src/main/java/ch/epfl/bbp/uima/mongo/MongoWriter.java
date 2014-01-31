package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.*;
import static java.lang.Integer.parseInt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.Type;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.StringUtils;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.typesystem.TypeSystem;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import de.julielab.jules.types.Header;

/**
 * Writes annotations to MongoDB
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoWriter extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(MongoWriter.class);

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, mandatory = true, //
    description = "host, dbname, collectionname, user, pw")
    private String[] db_connection;

    public static final String PARAM_CONNECTION_SAFE_MODE = "safeMode";
    @ConfigurationParameter(name = PARAM_CONNECTION_SAFE_MODE, defaultValue = "true", //
    description = "Mongo's WriteConcern SAFE(true) or NORMAL(false)")
    private boolean safeMode;

    private DBCollection coll;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);
        try {
            MongoConnection conn = new MongoConnection(db_connection, safeMode);
            coll = conn.coll;
            // index pmid (an integer, unlike _id),useful for queries
            coll.ensureIndex(PM_ID);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            BasicDBObject doc = new BasicDBObject();
            doc.put(TEXT, jCas.getDocumentText());

            Map<String, BasicDBList> dbLists = new HashMap<String, BasicDBList>();

            FSIterator<Annotation> it = jCas.getAnnotationIndex().iterator();
            while (it.hasNext()) {
                Annotation a = it.next();
                String typeName = a.getType().getName();

                if (typeName.equals("uima.tcas.DocumentAnnotation")) {// nope
                } else if (typeName.equals(TypeSystem.HEADER)
                        || typeName.equals(TypeSystem.PUBMED_HEADER)) {
                    Header h = (Header) a;
                    doc.put(ID, h.getDocId()); // LATER set prefix
                    doc.put(PM_ID, parseInt(h.getDocId()));
                    doc.put(TITLE, h.getTitle());

                } else if (ALL_MAPPINGS_KEYS.contains(typeName)) {
                    MongoFieldMapping fieldMapping = ALL_MAPPINGS.get(typeName);

                    BasicDBList dbList; // getOrElseCreate
                    if (dbLists.containsKey(fieldMapping.shortName)) {
                        dbList = dbLists.get(fieldMapping.shortName);
                    } else {
                        dbList = new BasicDBList();
                        dbLists.put(fieldMapping.shortName, dbList);
                    }

                    BasicDBObject o = new BasicDBObject();
                    dbList.add(o);
                    o.put(BEGIN, a.getBegin());
                    o.put(END, a.getEnd());

                    Type t = a.getType();
                    for (Feature f : t.getFeatures()) {

                        if (fieldMapping.fieldMappings.containsKey(f
                                .getShortName())) {
                            String dbKey = fieldMapping.fieldMappings.get(f
                                    .getShortName());

                            MongoFieldMapping.writeFieldToDb(f.getRange()
                                    .getShortName(), o, a, dbKey, f);

                        }
                    }
                } else {
                    LOG.warn("no mapping for {}", typeName);
                }
            }

            // insert all dbLists
            for (String dbListKey : dbLists.keySet()) {
                BasicDBList dbList = dbLists.get(dbListKey);
                doc.put(dbListKey, dbList);
            }

            coll.insert(doc);

        } catch (Throwable t) {
            // e.g. with "DBObject of size  is over Max BSON size"
            String sourceFile = "unknown";
            try {
                Header header = JCasUtil.selectSingle(jCas, Header.class);
                sourceFile = header.getSource();
            } catch (Throwable t2) {// nope
            }
            LOG.error("inserting doc " + sourceFile + StringUtils.print(t), t);
        }
    }
}