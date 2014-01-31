package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ALL_MAPPINGS;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ALL_MAPPINGS_KEYS;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.BEGIN;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.END;
import static ch.epfl.bbp.uima.mongo.MongoFieldMapping.ID;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

import de.julielab.jules.types.Header;

/**
 * Writes annotations to MongoDB
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoUpdateWriter extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(MongoUpdateWriter.class);

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, mandatory = true, //
    description = "host, dbname, collectionname, user, pw")
    private String[] db_connection;

    public static final String PARAM_UPDATE_ALL_ANNOTATIONS = "update_all_annotations";
    @ConfigurationParameter(name = PARAM_UPDATE_ALL_ANNOTATIONS, defaultValue = "false", //
    description = "whether to update all annotations found in the cas."
            + " Otherwise, specify which annotation to update in 'update_annotations'")
    private boolean updateAllAnnotations;

    public static final String PARAM_UPDATE_ANNOTATIONS = "update_annotations";
    @ConfigurationParameter(name = PARAM_UPDATE_ANNOTATIONS, //
    description = "the specific annotations to update.")
    private String[] updateAnnotations;
    private Set<String> updateAnnotationsSet;

    private DBCollection coll;

    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        // check updateAnnotations parameters
        if (updateAllAnnotations) {
            checkArgument(updateAnnotations == null,
                    "You can't update all annotations, and provide specific annotations in "
                            + PARAM_UPDATE_ANNOTATIONS);

        } else {
            updateAnnotationsSet = new HashSet<String>();
            checkArgument(updateAnnotations.length > 0,
                    "please provide at least some update annotations");
            for (String ua : updateAnnotations) {
                try {
                    Class<?> uaClass = Class.forName(ua);
                    checkArgument(Annotation.class.isAssignableFrom(uaClass));
                } catch (ClassNotFoundException e) {
                    throw new ResourceInitializationException(
                            NO_RESOURCE_FOR_PARAMETERS,
                            new Object[] { " --> invalid annotation for " + ua });
                }
                updateAnnotationsSet.add(ua);
            }

            for (String ua : updateAnnotations) {
                if (!MongoFieldMapping.ALL_MAPPINGS_KEYS.contains(ua)) {
                    throw new ResourceInitializationException(
                            NO_RESOURCE_FOR_PARAMETERS, new Object[] {
                                    " --> no MongoFieldMapping for annotation "
                                            + ua, null });
                }
            }
        }

        try {
            MongoConnection conn = new MongoConnection(db_connection);
            coll = conn.coll;
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        try {
            // LOG.debug("updating docId {}", getHeaderIntDocId(jCas));

            Map<String, BasicDBList> dbLists = new HashMap<String, BasicDBList>();

            FSIterator<Annotation> it = jCas.getAnnotationIndex().iterator();
            while (it.hasNext()) {
                Annotation a = it.next();
                String typeName = a.getType().getName();

                if (updateAllAnnotations) {
                    if (ALL_MAPPINGS_KEYS.contains(typeName)) {
                        processAnnotaion(a, dbLists, typeName);
                    } else {
                        LOG.warn(
                                "no mapping for {}, could not write annotation",
                                typeName);
                    }
                } else { // only specific annotations
                    if (ALL_MAPPINGS_KEYS.contains(typeName)
                            && updateAnnotationsSet.contains(typeName)) {
                        processAnnotaion(a, dbLists, typeName);
                    }
                }
            }

            // insert all dbLists
            BasicDBObject updateQuery = new BasicDBObject(ID,
                    getHeaderIntDocId(jCas) + "");
            BasicDBObject updateCommands = new BasicDBObject();
            updateCommands.put("$set", dbLists);
            coll.update(updateQuery, updateCommands, true, false);

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

    private void processAnnotaion(Annotation a,
            Map<String, BasicDBList> dbLists, String typeName) {
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

            if (fieldMapping.fieldMappings.containsKey(f.getShortName())) {
                String dbKey = fieldMapping.fieldMappings.get(f.getShortName());

                MongoFieldMapping.writeFieldToDb(f.getRange().getShortName(),
                        o, a, dbKey, f);
            }
        }
    }
}