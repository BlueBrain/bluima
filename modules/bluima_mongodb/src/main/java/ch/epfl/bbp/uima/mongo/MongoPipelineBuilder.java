package ch.epfl.bbp.uima.mongo;

import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.mongo.MongoUpdateWriter.PARAM_UPDATE_ANNOTATIONS;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import java.io.IOException;

import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.xml.sax.SAXException;

import ch.epfl.bbp.uima.uimafit.CpeBuilder;

/**
 * Wraps a Mongo CR and MongoWriter, so that one can add AEs in between (for
 * incremental processing).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class MongoPipelineBuilder extends CpeBuilder {

    private String[] connection;
    private String[] updateAnnotations;

    /**
     * If JDK < 7, this shows a warning, just ignore with
     * 
     * @SuppressWarnings("unchecked")
     */
    public MongoPipelineBuilder(int nrThreads, String[] connection,
            Class<? extends Annotation>... updateAnnotations)
            throws IOException, SAXException, CpeDescriptorException,
            ResourceInitializationException {
        this(nrThreads, connection, getUpdateAnnotations(updateAnnotations));
    }

    private static String[] getUpdateAnnotations(
            Class<? extends Annotation>... updateAnnotations) {
        String[] ret = new String[updateAnnotations.length];
        for (int i = 0; i < updateAnnotations.length; i++) {
            ret[i] = updateAnnotations[i].getName();
        }
        return ret;
    }

    public MongoPipelineBuilder(int nrThreads, String[] connection,
            String... updateAnnotations) throws IOException, SAXException,
            CpeDescriptorException, ResourceInitializationException {
        super(createReaderDescription(MongoCollectionReader.class,
                PARAM_DB_CONNECTION, connection));
        this.setMaxProcessingUnitThreatCount(nrThreads);
        this.connection = connection;
        this.updateAnnotations = updateAnnotations;
    }

    @Override
    public Boolean process() throws ResourceInitializationException,
            CpeDescriptorException {
        try {
            add(MongoUpdateWriter.class, PARAM_DB_CONNECTION, connection,
                    PARAM_UPDATE_ANNOTATIONS, updateAnnotations);
        } catch (Exception e) {
            throw new ResourceInitializationException(e);
        }
        return super.process();
    }
}
