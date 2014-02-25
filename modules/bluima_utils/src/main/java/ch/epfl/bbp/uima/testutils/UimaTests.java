package ch.epfl.bbp.uima.testutils;

import static ch.epfl.bbp.uima.utils.Preconditions.checkEquals;
import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.util.JCasUtil.exists;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.uima.UIMAException;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.TOP;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.uima.ae.DotSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.WhitespaceTokenizerAnnotator;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.typesystem.TypeSystem;

/**
 * Helper / Convenience methods for Uima tests
 * 
 * @author renaud.richardet@epfl.ch
 * 
 */
public class UimaTests {

	// http://www.ncbi.nlm.nih.gov/pubmed/15659591
	public static final String TEST_SENTENCE = "Using this calibration procedure, we find that mature granule cells (doublecortin-) contain approximately 40 microm, and newborn granule cells (doublecortin+) contain 0-20 microm calbindin-D28k.";
	// http://www.ncbi.nlm.nih.gov/pubmed/1957687
	public static final String TEST_ABSTRACT = "An automated clean up and concentration method by column switching is described for the assay of mitoxantrone, an antitumour drug in plasma. The system uses a YWG-CN short precolumn for on-line clean up and concentration and a Shimpack CLC-ODS analytical column for separation. Water is used as pretreat mobile phase and 48% methanol in ammonium acetate (0.2 mol/L pH 1.9) is used as analytical mobile phase. The plasma sample is treated with methanol and ultrasonated for 10 min. The supernatant of treated sample is directly injected to precolumn. The recovery of clean up is 85%. The wavelength used for detection is 658 nm and the concentration of detection limit is 6 ng/ml in plasma. The procedure is simple and the method is sensitive.";
	// http://www.ncbi.nlm.nih.gov/pubmed/10072497
	public static final String TEST_DEPENDENCY = "Altogether, our results suggest that NAC might impair the generation of primary immune responses in humans through its inhibitory action on DC.";

	public static JCas getTestCas() throws UIMAException {
		JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
		jCas.setDocumentText(TEST_SENTENCE);
		return jCas;
	}

	public static JCas getTestCas(String text) throws UIMAException {
		JCas jCas = JCasFactory.createJCas(TypeSystem.JULIE_TSD);
		jCas.setDocumentText(text);
		return jCas;
	}

	public static <T extends Annotation> T createAnnot(JCas jCas, Class<T> c,
			int beg, int end) throws Exception {
		checkArgument(beg <= end);
		// Class<?> c = Class.forName("ch.epfl.bbp.uima.types." + name);
		Constructor<?> ctor = c.getConstructor(JCas.class, Integer.TYPE,
				Integer.TYPE);
		@SuppressWarnings("unchecked")
		T a = (T) ctor.newInstance(jCas, beg, end);
		a.addToIndexes();
		return a;
	}

	public static <T extends Annotation> T createAnnot(JCas jCas, Class<T> c,
			int beg, int end, String assertCoveredText) throws Exception {
		T annot = createAnnot(jCas, c, beg, end);
		checkEquals(annot.getCoveredText(), assertCoveredText);
		return annot;
	}

	public static void assertResultsContains(
			Collection<? extends TOP> annotations, String fieldName,
			Object expected) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {

		boolean found = false;

		if (annotations.size() == 0) {
			throw new AssertionError("no annotations found");
		}

		for (TOP annotation : annotations) {

			Method method = annotation.getClass().getMethod(
					"get" + StringUtils.capitalize(fieldName));

			if (expected instanceof Integer) {
				try {
					int expectedInt = (Integer) expected;
					int actual = Integer.parseInt((String) method
							.invoke(annotation));
					if (actual == expectedInt) {
						found = true;
					}
				} catch (Exception e) {// nope
				}
			} else if (expected instanceof String) {
				String expectedStr = (String) expected;
				String actual = (String) method.invoke(annotation);
				if (actual.equals(expectedStr)) {
					found = true;
				}
			} // TODO for other fields types

		}

		if (!found) {
			throw new AssertionError("cannot find field '" + fieldName
					+ "' on collection \n" + To.string(annotations));
		}
	}

	public static void assertResultsContains(
			Collection<? extends Annotation> annotations, int begin, int end) {
		if (annotations.size() == 0)
			throw new AssertionError("no annotations found");
		for (Annotation a : annotations)
			if (a.getBegin() == begin && a.getEnd() == end)
				return;
		// not found
		String msg = "cannot find begin-end '" + begin + "-" + end
				+ "' on collection (have: ";
		for (Annotation a : annotations)
			msg += a.getBegin() + "-" + a.getEnd() + " ";
		throw new AssertionError(msg + ")");
	}

	/**
	 * @param annotations
	 * @param texts
	 *            the EXACT corresponding text of ALL the annotations
	 */
	public static void assertResultsContainsText(
			Collection<? extends Annotation> annotations, String... texts) {
		if (annotations.size() == 0)
			throw new AssertionError("no annotations found");

		if (annotations.size() != texts.length)
			throw new AssertionError("annotation size not matching text size, "
					+ annotations.size() + " vs " + texts.length);

		int i = 0;
		for (Annotation a : annotations) {
			if (!a.getCoveredText().equals(texts[i])) {
				throw new AssertionError("Text '" + texts[i] + "' at position "
						+ (i) + " does not match '" + a.getCoveredText() + "'");
			}
			i++;
		}
	}

	/**
	 * @param annotations
	 * @param textToBeCovered
	 */
	public static void assertResultsCovers(
			Collection<? extends Annotation> annotations, String textToBeCovered) {
		boolean found = false;
		for (Annotation a : annotations) {
			if (a.getCoveredText().equals(textToBeCovered)) {
				found = true;
			}
		}

		if (!found) {
			throw new AssertionError("cannot find text '" + textToBeCovered
					+ "' on collection \n" + To.string(annotations));
		}
	}

	/**
	 * @param text
	 * @return a JCas that has been (naively) sentence splitted and tokenized
	 */
	public static JCas getTokenizedTestCas(String text) throws UIMAException,
			IOException {
		JCas testCas = getTestCas(text);
		AnalysisEngine ss = createEngine(DotSentenceSplitterAnnotator.class);
		AnalysisEngine wt = createEngine(WhitespaceTokenizerAnnotator.class);
		SimplePipeline.runPipeline(testCas, ss, wt);
		return testCas;
	}

	/**
	 * @return a JCas with default text that has been (naively) sentence
	 *         splitted and tokenized
	 */
	public static JCas getTokenizedTestCas() throws UIMAException, IOException {
		return getTokenizedTestCas(TEST_SENTENCE);
	}

	public static void assertContains(JCas jCas,
			Class<? extends Annotation> annot, int expectedCnt) {
		if (expectedCnt == 0) {
			checkArgument(
					!exists(jCas, annot),
					"assertContains %s annotations of type %s, but found some ",
					expectedCnt, annot.getSimpleName());
		} else {
			checkEquals(
					expectedCnt,
					select(jCas, annot).size(),
					"assertContains " + expectedCnt + " annotations of type "
							+ annot.getSimpleName() + ", but found "
							+ select(jCas, annot).size());
		}
	}
}
