package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_OUTPUT_FILE;
import static com.google.common.collect.Lists.newLinkedList;
import static com.google.common.collect.Maps.newHashMap;
import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;

import ch.epfl.bbp.io.TextFileWriter;
import ch.epfl.bbp.uima.types.Keep;

/**
 * Output {@link Keep}s into the LDA-C format:<br>
 * <code>    [M] [term_1]:[count] [term_2]:[count] ...  [term_N]:[count]</code><br>
 * where [M] is the number of unique terms in the document, and the [count]
 * associated with each term is how many times that term appeared in the
 * document. Also writes the corresponding vocabulary file, one {@link Keep} per
 * line.<br>
 * The writer can be used separately:
 * 
 * <pre>
 * writer = new LdaCWriter.Writer(&quot; &quot;, corpusFile, vocabFile);
 * writer.addDocument(wordList);
 * writer.close();
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class LdaCWriter extends JCasAnnotator_ImplBase {

	@ConfigurationParameter(name = PARAM_OUTPUT_FILE, defaultValue = "lda-c.txt", mandatory = false)
	private String outputFile;

	public static final String PARAM_VOCABULARY_OUTPUT_FILE = "vocabularyOutputFile";
	@ConfigurationParameter(name = PARAM_VOCABULARY_OUTPUT_FILE, defaultValue = "lda-c.vocab", mandatory = false)
	private String vocabularyOutputFile;

	public static final String PARAM_IDS_OUTPUT_FILE = "idsOutputFile";
	@ConfigurationParameter(name = PARAM_IDS_OUTPUT_FILE, defaultValue = "lda-c.ids", mandatory = false)
	private String idsOutputFile;

	public static final String PARAM_DCA_FORMAT = "dcaFormat";
	@ConfigurationParameter(name = PARAM_DCA_FORMAT, defaultValue = "false",//
	description = "wheter to output in DCA format (without ':')")
	private boolean dcaFormat;

	private Writer writer;
	private TextFileWriter idsWriter;

	@Override
	public void initialize(UimaContext context)
			throws ResourceInitializationException {
		super.initialize(context);
		try {
			String separator = dcaFormat ? " " : ":";
			writer = new Writer(separator, outputFile, vocabularyOutputFile);
			idsWriter = new TextFileWriter(new File(idsOutputFile));
		} catch (IOException e) {
			throw new ResourceInitializationException(e);
		}
	}

	@Override
	public void process(JCas jCas) throws AnalysisEngineProcessException {

		try {
			List<String> words = newLinkedList();
			for (Keep k : select(jCas, Keep.class)) {
				words.add(k.getNormalizedText());
			}
			if (!words.isEmpty()) {
				writer.addDocument(words);
				idsWriter.addLine(getHeaderDocId(jCas));
			}

		} catch (Exception e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

	@Override
	public void collectionProcessComplete()
			throws AnalysisEngineProcessException {
		super.collectionProcessComplete();
		try {
			writer.close();
			idsWriter.close();
		} catch (IOException e) {
			throw new AnalysisEngineProcessException(e);
		}
	}

	public static class Writer {

		/** Either space (DCA format) or colum (LDA-C format) */
		private String separator;
		private Map<String, Integer> vocabulary = newLinkedHashMap(); // ordered!
		private PrintWriter corpusWriter;
		private String vocabularyOutputFile;

		/**
		 * @param separator
		 *            ':' for LDA-C, ' ' for DCA
		 * @param outputFile
		 * @param vocabularyOutputFile
		 */
		public Writer(String separator, String outputFile,
				String vocabularyOutputFile) throws IOException {
			this.separator = separator;
			this.vocabularyOutputFile = vocabularyOutputFile;
			corpusWriter = new PrintWriter(new BufferedWriter(new FileWriter(
					new File(outputFile))));
		}

		public void addDocument(List<String> words) {
			// key: vocabulary id; val: count
			Map<Integer, Integer> documentMap = newHashMap();

			for (String word : words) {

				Integer tokenId = vocabulary.get(word);
				if (tokenId == null) {// add new to vocab
					tokenId = vocabulary.size();
					vocabulary.put(word, tokenId);
				}

				Integer cnt = documentMap.get(tokenId);
				if (cnt == null) {// add new to docMap
					documentMap.put(tokenId, 1);
				} else {
					documentMap.put(tokenId, cnt + 1);
				}
			}
			corpusWriter.print(documentMap.size());
			for (Entry<Integer, Integer> entry : documentMap.entrySet()) {
				corpusWriter.print(" " + entry.getKey() + separator
						+ entry.getValue());
			}
			corpusWriter.println();
		}

		public void close() throws IOException {
			corpusWriter.close();
			// write vocab
			PrintWriter vocabWriter = new PrintWriter(new BufferedWriter(
					new FileWriter(new File(vocabularyOutputFile))));
			for (String vocabEntry : vocabulary.keySet()) {
				vocabWriter.println(vocabEntry);
			}
			vocabWriter.close();
		}
	}
}