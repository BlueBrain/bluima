package ch.epfl.bbp.nlp.ie.proteinconc.ae;

import static ch.epfl.bbp.nlp.ie.proteinconc.ExtractProteinConcentrations.EXTRACT_ROOT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.ae.MeasureRegexAnnotators.getMeasuresAED;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getChunker;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getPosTagger;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getSentenceSplitter;
import static ch.epfl.bbp.uima.ae.OpenNlpHelper.getTokenizer;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;
import ch.epfl.bbp.uima.uimafit.SimplePipelineBuilder;
import de.julielab.jules.types.Chunk;

public class ChunkAdapterTest {

	@Test
	public void test() throws Exception {

		JCas cas = getTestCas("The brain tissues (hippocampus and IPL) from control and MCI were homogenized in ice-cold isolation buffer containing [0.32 M sucrose, 10 mM Tris–HCl buffer (pH 7.5), 1.5 mM MgCl2, 1 mM EGTA, 1 mM EDTA, 1 mM PMSF, leupeptin (2 µg/ml) and aprotinin (2 µg/ml)] by douching 30 times in a glass tissue homogenizer (Wheaton, Millville, USA) according to the method of Sultana.");

		runPipeline(cas, getSentenceSplitter(), getTokenizer(), getPosTagger(),
				getChunker(), getMeasuresAED(),
				createEngineDescription(BannerMAnnotator.class),
				createEngineDescription(ChunkAdapter.class));

		boolean leupeptinPattern = false;
		boolean aproptininPattern = false;
		for (Chunk c : select(cas, Chunk.class)) {
			String coveredText = c.getCoveredText();
			assertFalse("a chunk contains a comma", coveredText.contains(","));
			leupeptinPattern = leupeptinPattern
					|| (coveredText.contains("leupeptin") && coveredText
							.contains("2 µg/ml"));
			aproptininPattern = aproptininPattern
					|| (coveredText.contains("aprotinin") && coveredText
							.contains("2 µg/ml"));
		}
		assertTrue("leupeptin and concentration not in the same chunk",
				leupeptinPattern);
		assertTrue("aproptinin and concentration not in the same chunk",
				aproptininPattern);
	}

	/**
	 * Verifies the correction of a bug which allowed chunk to be adapted such
	 * that they are not bounded by the Sentence annotation.
	 */
	@Test
	public void endOfSentenceTest() throws Exception {
		SimplePipelineBuilder builder = new SimplePipelineBuilder(
				createReaderDescription(PdfCollectionReader.class,//
						PARAM_INPUT_DIRECTORY, EXTRACT_ROOT
								+ "src/test/resources/test_pdfs/chunk_adapter"));

		builder.add(getSentenceSplitter());
		builder.add(getTokenizer());
		builder.add(getPosTagger());
		builder.add(getChunker());
		builder.add(createEngineDescription(ChunkAdapter.class));
		builder.add(ChunkController.class);
		builder.process();
	}

	public static class ChunkController extends JCasAnnotator_ImplBase {

		@Override
		public void process(JCas cas) throws AnalysisEngineProcessException {
			for (Chunk chunk : select(cas, Chunk.class)) {
				String coveredText = (chunk.getEnd() > 0) ? chunk
						.getCoveredText() : "";
				if (coveredText.contains("Rad")
						&& coveredText.contains("0.1 M")) {
					fail();
				}
			}
		}
	}
}
