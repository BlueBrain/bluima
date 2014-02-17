package ch.epfl.bbp.uima.validation;

import static ch.epfl.bbp.uima.BlueUima.PARAM_CORPUS_NAME;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.HEADER;

import java.io.File;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.descriptor.OperationalProperties;
import org.apache.uima.fit.descriptor.TypeCapability;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.serialization.BinaryCasWriter;

/**
 * Used after the {@link CollectionReader} of an annotated corpus to persist it
 * for subsequent use (cross validation).
 * 
 * See {@link CrossvalidationReader}
 * 
 * @author renaud.richardet@epfl.ch
 */
@TypeCapability(outputs = { HEADER })
// because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class CrossvalidationWriter extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory
			.getLogger(CrossvalidationWriter.class);

	@ConfigurationParameter(name = PARAM_CORPUS_NAME)
	private String corpusName;

	private File store;
	private int docId;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);

		store = new File("target/crossvalidation_" + corpusName);
		docId = 0;

		if (store.exists()) {
			throw new RuntimeException("corpus exists at " + store.getPath());
		} else {
			LOG.warn("creating Cross-Validation data in {}", store.getPath());
			store.mkdirs();
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		File file = new File(store, docId + ".gz");
		try {
			BinaryCasWriter.serialize(jCas, file);
		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
		docId++;
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		LOG.warn("Persisting corpus done, added {} documents in {}", docId,
				store.getPath());
	}
}
