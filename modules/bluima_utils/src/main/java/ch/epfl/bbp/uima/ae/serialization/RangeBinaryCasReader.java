package ch.epfl.bbp.uima.ae.serialization;

import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.ae.serialization.BinaryCasWriter.EXTENTION_GZ;
import static ch.epfl.bbp.uima.utils.Preconditions.checkFileExists;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.File;
import java.io.IOException;

import org.apache.uima.UimaContext;
import org.apache.uima.collection.CollectionException;
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Progress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.utils.StructuredDirectory;

/**
 * Reads CASes from a serialized, compressed binary format, in a
 * {@link StructuredDirectory}, that has been created with
 * {@link BinaryCasWriter}.<br>
 * Document range defined with upper and lower pmid bounds (PARAM_BETWEEN).
 * 
 * @author renaud.richardet@epfl.ch
 */
public class RangeBinaryCasReader extends JCasCollectionReader_ImplBase {
	protected static final Logger LOG = LoggerFactory
			.getLogger(RangeBinaryCasReader.class);

	@ConfigurationParameter(name = PARAM_INPUT_DIRECTORY, //
	description = "path to a StructuredDirectory containing serialized CASes")
	protected String inputDir;

	@ConfigurationParameter(name = PARAM_BETWEEN, description = "specifies a range "
			+ "of pubmed_id, e.g. {13,17} --> 13 <= pubmed_id <= 17")
	private int[] btw;
	int nextPmId, maxPmId;
	File next;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		// between
		checkArgument(btw.length == 2, "PARAM_BETWEEN should be length 2, "
				+ btw);
		checkArgument(btw[0] < btw[1],
				"PARAM_BETWEEN[0] should be < PARAM_BETWEEN[1], " + btw);
		nextPmId = btw[0];
		maxPmId = btw[1];
		// inputDir
		checkFileExists(inputDir);
	}

	@Override
	public boolean hasNext() throws IOException, CollectionException {

		for (int i = nextPmId; i < maxPmId; i++) {
			String path = StructuredDirectory.getFilePath(i, EXTENTION_GZ);
			File f = new File(inputDir + "/" + path);
			// System.out.println((f.exists() ? "+" : "-") + " " + i+ " "+path);
			if (f.exists()) {
				nextPmId = i + 1;
				next = f;
				return true;
			}
		}
		return false;
	}

	@Override
	public Progress[] getProgress() {// nope
		return null;
	}

	@Override
	public void getNext(JCas jCas) throws IOException, CollectionException {
		try {
			BinaryCasReader.deserialize(next, jCas);
		} catch (Exception e) {
			LOG.error(
					"could not read serialized cas at "
							+ next.getAbsolutePath(), e);
		}
	}

	
}
