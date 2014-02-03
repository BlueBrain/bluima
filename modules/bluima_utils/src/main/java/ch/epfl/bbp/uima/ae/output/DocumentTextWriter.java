package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_DIR;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.fit.factory.AnalysisEngineFactory;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.BlueCasUtil;

/**
 * Dumps the document text to a text file
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class DocumentTextWriter extends JCasAnnotator_ImplBase {

	@ConfigurationParameter(name = PARAM_OUTPUT_DIR, defaultValue = "target/docs")
	private String outputDir;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {
			FileUtils.forceMkdir(new File(outputDir));
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		String docId = BlueCasUtil.getHeaderDocId(jCas);

		if (jCas.getDocumentText() != null
				&& jCas.getDocumentText().length() > 0) {
			try {
				TextFileWriter writer = new TextFileWriter(outputDir
						+ File.separatorChar + docId + ".txt");
				writer.addLine(jCas.getDocumentText());
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static AnalysisEngineDescription getAED(String outDir)
			throws ResourceInitializationException {
		return AnalysisEngineFactory.createEngineDescription(
				DocumentTextWriter.class, PARAM_OUTPUT_DIR, outDir);
	}
}
