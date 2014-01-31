package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static ch.epfl.bbp.uima.BlueUima.PARAM_FEATURE_NAME;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static org.apache.uima.resource.ResourceInitializationException.NO_RESOURCE_FOR_PARAMETERS;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.Feature;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.resource.ResourceInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;

/**
 * A consumer that writes out specified Annotations and features to a specified
 * text file
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AnnotationTypeWriter extends JCasAnnotator_ImplBase {
	private static Logger LOG = LoggerFactory
			.getLogger(AnnotationTypeWriter.class);

	@ConfigurationParameter(name = PARAM_OUTPUT_FILE, defaultValue = "out.txt", //
	description = "outputfile, or System for sysout")
	private String outputFile;

	@ConfigurationParameter(name = PARAM_ANNOTATION_CLASS, //
	defaultValue = "org.apache.uima.jcas.tcas.Annotation", description = "the full name of the annotation class")
	private String annotationClass;

	@ConfigurationParameter(name = PARAM_FEATURE_NAME, description = "the name of the feature to extract, or none for the text only")
	private String featureName;

	public static final String PARAM_FILTER_FEATURES_WITH_VALUE = "filterFeaturesWithValue";

	@ConfigurationParameter(name = PARAM_FILTER_FEATURES_WITH_VALUE,//
	description = "filter the features that have this value")
	private String filterFeaturesWithValue;

	private static final String PARAM_NEW_LINE = "param_new_line";
	@ConfigurationParameter(name = PARAM_NEW_LINE, description = "whether to add a new line after each token", defaultValue = "false")
	private boolean newLine;

	private PrintWriter writer;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {
			if (outputFile.equals("System"))
				writer = new PrintWriter(System.out, true);
			else
				writer = new PrintWriter(new BufferedWriter(new FileWriter(
						new File(outputFile))));

		} catch (Exception e) {
			throw new ResourceInitializationException(
					NO_RESOURCE_FOR_PARAMETERS, new Object[] { outputFile }, e);
		}
	}

	@Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {
		try {
			for (Annotation annotation : jcas.getAnnotationIndex()) {
				if (annotation.getClass().getName().equals(annotationClass)) {
					LOG.trace("printing " + annotation.getCoveredText());
					if (featureName != null) {
						Feature feature = annotation.getType()
								.getFeatureByBaseName(featureName);
						String featureStr = annotation
								.getFeatureValueAsString(feature);

						if (!(filterFeaturesWithValue != null && featureStr
								.equals(filterFeaturesWithValue))) {
							writer.append(annotation.getCoveredText() + "<"
									+ featureStr + ">");
							if (newLine) {
								writer.println();
							} else {
								writer.append(" ");
							}
						}
					} else {
						writer.append(annotation.getCoveredText());
						if (newLine) {
							writer.println();
						} else {
							writer.append(" ");
						}
					}
				}
			}
			writer.println();
			writer.flush();

		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		IOUtils.closeQuietly(writer);
	}

	public static AnalysisEngineDescription getAED(
			Class<? extends Annotation> annot, String featureName)
			throws ResourceInitializationException {
		return createEngineDescription(AnnotationTypeWriter.class,
				PARAM_OUTPUT_FILE, "System", PARAM_ANNOTATION_CLASS,
				annot.getName(), PARAM_FEATURE_NAME, featureName,
				PARAM_NEW_LINE, true);
	}
}
