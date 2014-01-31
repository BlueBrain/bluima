package ch.epfl.bbp.uima.integration_tests;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.ae.ZipXWriter.PARAM_OUTPUT_DIRECTORY_NAME;
import static ch.epfl.bbp.uima.pear.ComponentManager.getAE;
import static ch.epfl.bbp.uima.pear.ComponentManager.getCR;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static org.apache.uima.util.CasCreationUtils.createCas;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.ae.ZipXWriter;
import ch.epfl.bbp.uima.pear.Component;
import ch.epfl.bbp.uima.pear.ComponentManager;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Concentration;
import ch.epfl.bbp.uima.typesystem.Prin;
import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.POSTag;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * @author renaud.richardet@epfl.ch
 */
public class AllComponentsIntegrationTest {

	protected static Logger LOG = LoggerFactory
			.getLogger(AllComponentsIntegrationTest.class);

	List<String> skipList = Arrays.asList(new String[] {//
					"Chunker",
					"DateNameFinder",
					"LocationNameFinder",
					"MoneyNameFinder",
					"OpenNlpTextAnalyzer",
					"OrganizationNameFinder",
					"PercentageNameFinder",
					"PersonNameFinder",
					"PosTagger",
					"SentenceDetector",
					"TimeNameFinder",
					"Tokenizer",
					"TypeSystem",//
					"LingpipeBiocreativeNER",
					"LingpipeExactDictionaryNER",
					"LingpipePOSTagger", //
					"AcronymAnnotator",//
					"GeneEntityAnnotator",
					"MSTParserDescriptor",
					"SentenceAnnotator",
					"TokenAnnotator",
					"TokenAnnotationChecker",
					"GarbageCollectorAnnotator",//
					"BlueBioLemmatizer",
					"BannerTokenizerAnnotator",
					"ZipDelegatingReader",
					"DocumentTextWriter",//
					"PruneConcentrationsAnnotator", "FileReader",

					"VerbAnnotator", "BartWriter",
					"RT1CellTypeProteinConcentrationExtractor",
					"BlueDictionaryAnnotator", "RegExAnnotator",
					"VerbFilter2Annotator", "BioLexiconVerbsConceptAnnotator",
					"CellTypesConceptAnnotator",
					"EnsureDocTextNotNullAnnotator", "EnsureDocHasHeader","EnsureTokensHaveLemmaAndPOS",
					"EnsureDocHasOneSentence",
					"StatsAnnotator", "AbstractFileReader",
					"StatsAnnotatorPlus" });

	@Test
	public void testAllComponentsHaveTests() throws Exception {

		List<String> methodNames = new ArrayList<String>();
		Method[] methods = AllComponentsIntegrationTest.class.getMethods();
		for (Method method : methods) {
			methodNames.add(method.getName());
		}

		Collection<Component> components = ComponentManager.getComponents();
		for (Component component : components) {

			String name = component.getDescriptorName();

			if (skipList.contains(name)) {
				LOG.warn("Skipping IT for {}, shame on you!", name);
			} else {

				if (!methodNames.contains("test" + name)) {
					throw new AssertionError("no tests for " + name);
				}
			}
		}
	}

	@Test
	public void testBiocreative2GeneCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testRegexTokenizerAnnotator() throws Exception {

		AnalysisEngine sentenceAE = getAE("NaiveSentenceSplitterAnnotator");
		AnalysisEngine tokenizerAE = getAE("RegexTokenizerAnnotator");

		JCas jcas = UimaTests.getTestCas("bla,bla calbindin-D28k blaBla");
		SimplePipeline.runPipeline(jcas, sentenceAE, tokenizerAE);

		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		Prin.t("tokens", tokens);
		assertEquals(5, tokens.size());
	}

	@Test
	public void testBioAdiAbreviationAnnotator() {
		// TODO Auto-generated method stub
	}

	public void testLinnaeusAnnotator() {
		// TODO Auto-generated method stub
	}

	@Test
	public void testBiocreative2GeneCR() {
		// TODO Auto-generated method stub

	}

	// FIXME @Test
	public void testConcentrationRegexAnnotator() throws Exception {

		JCas jcas = UimaTests.getTestCas("bla bla 40 mg/m3 bli bli");
		AnalysisEngine ae = getAE("ConcentrationRegexAnnotator");
		runPipeline(jcas, ae);
		Collection<Concentration> concentrations = select(jcas,
				Concentration.class);
		assertEquals(1, concentrations.size());
		Concentration concentration = concentrations.iterator().next();
		assertEquals("mg/m3", concentration.getUnit());
		// FIXME assertEquals("µg/m3", concentration.getUnit());
	}

	@Test
	public void testOpenNlpTokenAnnotator() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testWhiteTextCollectionReader() throws Exception {

		CollectionReader cr = getCR("WhiteTextCollectionReader");

		CAS cas = createCas(cr.getProcessingResourceMetaData());
		cr.getNext(cas);

		Collection<BrainRegion> br = JCasUtil.select(cas.getJCas(),
				BrainRegion.class);
		assertEquals(8, br.size());

		BrainRegion brainRegion = br.iterator().next();
		assertEquals("superior colliculus", brainRegion.getCoveredText());
	}

	@Test
	public void testNaiveSentenceSplitterAnnotator() throws Exception {
		JCas jcas = getTestCas("Le petit chat mange. Il mange bien. Oh comme c'est bon!");

		AnalysisEngine ss = getAE("NaiveSentenceSplitterAnnotator");

		runPipeline(jcas, ss);

		Collection<Sentence> sentences = JCasUtil.select(jcas, Sentence.class);
		assertEquals(1, sentences.size());
	}

	@Test
	public void testDotSentenceSplitterAnnotator() throws Exception {

		JCas jcas = getTestCas("Le petit chat mange. Il mange bien. Oh comme c'est bon!");

		AnalysisEngine ss = getAE("DotSentenceSplitterAnnotator");

		runPipeline(jcas, ss);

		Collection<Sentence> sentences = JCasUtil.select(jcas, Sentence.class);
		assertEquals(3, sentences.size());
	}

	@Test
	public void testWhitespaceTokenizerAnnotator() throws Exception {

		JCas jcas = getTestCas("bla bla calbindin-D28k bla bla");

		AnalysisEngine ss = getAE("NaiveSentenceSplitterAnnotator");
		AnalysisEngine wt = getAE("WhitespaceTokenizerAnnotator");

		runPipeline(jcas, ss, wt);

		Collection<Token> tokens = JCasUtil.select(jcas, Token.class);
		LOG.debug(To.string("tokens", tokens));
		assertEquals(5, tokens.size());
	}

	@Test
	public void testTextLineReader() throws Exception {
		CollectionReader cr = getCR("TextLineReader",
				BlueUima.PARAM_INPUT_FILE, "TextLineReader/testDoc.txt");
		CAS cas = createCas(cr.getProcessingResourceMetaData());
		cr.getNext(cas);
		assertEquals("first line", cas.getDocumentText());
	}

	@Test
	public void testOpenNlpChunkAnnotator() {
		// TODO
	}

	@Test
	public void testOpenNlpParseAnnotator() {
		// TODO
	}

	@Test
	public void testOpenNlpPosTagAnnotator() throws Exception {
		JCas jcas = getTestCas();

		AnalysisEngine ss = getAE("NaiveSentenceSplitterAnnotator");
		AnalysisEngine wt = getAE("WhitespaceTokenizerAnnotator");
		AnalysisEngine pos = getAE("OpenNlpPosTagAnnotator");

		runPipeline(jcas, ss, wt, pos);

		Collection<POSTag> tokens = JCasUtil.select(jcas, POSTag.class);
		LOG.debug(To.string("POSTag", tokens));
		assertEquals(24, tokens.size());

		// String[] Pos = { "DT", "NN", "NN", "PRP", "VBP", "IN", "JJ", "NN",
		// "NNS", "(", "VBP", "RB", "CD", "NNS", "CC", "JJ", "NN", "NNS",
		// "(", "VBP", "CD", "NNS", "NN" };
		// Iterator<POSTag> iterator = tokens.iterator();
		// while (iterator.hasNext()) {
		// POSTag posTag = iterator.next();
		// }
	}

	@Test
	public void testOpenNlpSentenceAnnotator() {
		// TODO Auto-generated method stub
	}

	@Test
	public void testTokenAnnotator() {
		// TODO Auto-generated method stub
	}

	// @Test
	// public void testRoomNumberAnnotator() throws Exception {
	//
	// JCas jcas = getTestCas("	12:00PM-1:00PM in HAW GN-K35"
	// + "	3:00PM-4:30PM in HAW GN-K35"
	// + "	9:00AM-5:00PM in HAW GN-K35");
	//
	// AnalysisEngine ae = getAE("RoomNumberAnnotator");
	// runPipeline(jcas, ae);
	//
	// Collection<RoomNumber> tokens = JCasUtil.select(jcas, RoomNumber.class);
	// LOG.debug(To.string("RoomNumber", tokens));
	// assertEquals(3, tokens.size());
	// }

	@Test
	public void testCasDetaggerAnnotator() throws Exception {

		JCas jcas = getTestCas();
		Token token = new Token(jcas);
		token.addToIndexes();
		Sentence sent = new Sentence(jcas);
		sent.addToIndexes();

		Collection<Annotation> tokens = JCasUtil.select(jcas, Annotation.class);
		assertEquals(3, tokens.size());

		AnalysisEngine ae = getAE("CasDetaggerAnnotator", "className",
				Token.class.getName());
		runPipeline(jcas, ae);
		tokens = JCasUtil.select(jcas, Annotation.class);
		assertEquals("Sentence still here", 2, tokens.size());

		ae = getAE("CasDetaggerAnnotator", "className", "all");
		runPipeline(jcas, ae);
		tokens = JCasUtil.select(jcas, Annotation.class);
		assertEquals("No annotation should remain, except doc", 1,
				tokens.size());
	}

	@Test
	public void testBlueAnnotationViewerAnnotator() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testAnnotationTypeWriter() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testJythonAnnotator() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testXmlTestcaseCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testPubmedCentralCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testTextArrayReader() throws Exception {
		testZipXmiCollectionReader();
	}

	@Test
	public void testZipXWriter() throws Exception {
		testZipXmiCollectionReader();
	}

	@Test
	public void testZipXmiCollectionReader() throws Exception {

		CollectionReader cr = getCR("TextArrayReader", PARAM_INPUT,
				new String[] { "this is a test" });

		AnalysisEngine serializer = createEngine(ZipXWriter.class,
				PARAM_OUTPUT_DIRECTORY_NAME, "target");
		SimplePipeline.runPipeline(cr, serializer);

		cr = getCR("ZipXmiCollectionReader", PARAM_INPUT_DIRECTORY, "target");
		CAS cas = createCas(cr.getProcessingResourceMetaData());
		cr.getNext(cas);
		assertEquals("this is a test", cas.getDocumentText());
	}

	@Test
	public void testPubmedArchiveCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testGeniaCorpusCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testBioNLPGeniaEventsCollectionReader() {
		// TODO Auto-generated method stub

	}

	@Test
	public void testAbnerAnnotator() {
	}

	@Test
	public void testDragonLemmatiserAnnotator() throws Exception {
		JCas jcas = UimaTests.getTokenizedTestCas();
		AnalysisEngine ae = getAE("DragonLemmatiserAnnotator");
		ae.process(jcas);

		for (Token t : JCasUtil.select(jcas, Token.class)) {
			System.out.println(t.getCoveredText() + "\t"
					+ t.getLemma().getValue());
		}
	}

	@Test
	public void testBannerAnnotator() throws Exception {

		JCas jcas = getTestCas();

		AnalysisEngine ae = getAE("BannerAnnotator");
		runPipeline(jcas, ae);

		for (Annotation a : jcas.getAnnotationIndex()) {
			System.out.println(a.getCoveredText() + a);
		}
	}
}
