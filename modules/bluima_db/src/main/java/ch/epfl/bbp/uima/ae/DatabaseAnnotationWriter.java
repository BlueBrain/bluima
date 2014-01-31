package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.cr.PubmedDatabaseCR.getDb;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.collect.Lists.newArrayList;
import static org.apache.commons.lang.WordUtils.capitalize;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.db.utils.Database;

/**
 * Writes the given {@link Annotation}s to a specified MySQL database.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class DatabaseAnnotationWriter extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory
            .getLogger(DatabaseAnnotationWriter.class);

    public static final String PARAM_CREATE_TABLE_STATEMENT = "createTableStatement";
    public static final String PARAM_INSERT_STATEMENT = "insertStatement";
    private int inserted = 0;

    @ConfigurationParameter(name = BlueUima.PARAM_DB_CONNECTION, mandatory = true,//
    description = "host, dbname, user, pw")
    private String[] db_connection;

    @ConfigurationParameter(name = PARAM_CREATE_TABLE_STATEMENT, //
    description = "SQL statement to create the table")
    private String createTableStatement;

    @ConfigurationParameter(name = PARAM_INSERT_STATEMENT, mandatory = true, //
    description = "SQL statement to insert annotations. The first argument will be (pm)id. Example: INSERT INTO `my_table` (`pmid`, `token`, `begin`, `end`) VALUES (?,?,?,?)")
    private String insertStatement;

    @ConfigurationParameter(name = BlueUima.PARAM_ANNOTATION_CLASS, mandatory = true, //
    description = "full qualified name of the annotation class. Example: de.julielab.jules.types.Token")
    private String annotation;

    @ConfigurationParameter(name = BlueUima.PARAM_ANNOTATION_FIELDS, mandatory = true, //
    description = "ordered array of annotation fields (omitting pmid). Example: { \"coveredText\", \"begin\", \"end\" }")
    private String[] annotationFields;
    private Database db;

    private PreparedStatement preparedStatement;
    private Class<? extends Annotation> annotationClass;
    private ArrayList<Method> annotationMethods;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(UimaContext context)
            throws ResourceInitializationException {
        super.initialize(context);

        inserted = 0;
        try {
            db = getDb(db_connection);
            if (createTableStatement != null
                    && createTableStatement.length() > 0) {
                db.execute(createTableStatement);
                LOG.info("created table with {}", createTableStatement);
            }
            preparedStatement = db.prepareStatement(insertStatement);

            annotationClass = (Class<? extends Annotation>) Class
                    .forName(annotation);

            annotationMethods = newArrayList();
            for (String annotationField : annotationFields) {
                boolean found = false;
                for (Method m : annotationClass.getMethods()) {
                    if (m.getName().equals("get" + capitalize(annotationField))) {
                        found = true;
                        annotationMethods.add(m);
                        break;
                    }
                }
                if (!found) {
                    throw new Exception("field " + annotationField
                            + " not found in class " + annotation);
                }
            }
            checkArgument(annotationMethods.size() == annotationFields.length);

        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        int pmId = getHeaderIntDocId(jCas);
        try {
            for (Annotation a : select(jCas, annotationClass)) {

                preparedStatement.setInt(1, pmId);

                for (int i = 0; i < annotationFields.length; i++) {
                    try {
                        Object value = annotationMethods.get(i).invoke(a);
                        preparedStatement.setObject(i + 2, value);
                    } catch (Exception e) {
                        throw new Exception(
                                "Could not assign field " + (i + 2), e);
                    }
                }
                preparedStatement.addBatch();

                if (++inserted % 1000 == 0)
                    preparedStatement.executeBatch();
            }

        } catch (Exception e) {
            LOG.error("could not insert " + pmId, e);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        try {
            preparedStatement.executeBatch();
            db.close();
            LOG.info("inserted {} entries into db", inserted);
        } catch (SQLException e) {
            throw new AnalysisEngineProcessException(e);
        }
    }
}
