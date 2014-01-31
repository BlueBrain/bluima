package ch.epfl.bbp.nlp.ie.proteinconc;

import static ch.epfl.bbp.uima.BlueUima.PARAM_MODEL_FILE;
import static ch.epfl.bbp.uima.LexicaHelper.getConceptMapper;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTokenizedTestCas;
import static org.apache.uima.fit.util.JCasUtil.select;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.BrainRegionsHelper;
import ch.epfl.bbp.uima.LexicaHelper;
import ch.epfl.bbp.uima.ae.BrainRegionAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;
import org.junit.Assert;
import org.junit.Test;

public class SetUpTest {

	private static final String MODEL_FILE = BrainRegionsHelper.BRAIN_REGIONS_HOME
			// + "resources/models/20130515_BrainRegion.model";
			+ "resources/models/20131007_BrainRegion.model";

	@Test
	public void testBrainRegionAnnotatorSetUp() {
		try {
			String sentence = "For reverse transcription, 17 μl of a solution containing 1.18 mM dNTP, 3.82 mM MgCl , 50 U of Moloney Murine Leukemia Virus 2 RNase H minus reverse transcriptase (Gibco-BRL), 4.4 μM random hexamer primer (Pharmacia), 20 U/ml of RNase inhibitor, 30 mM KCl, and 6 mM Tris-HCl pH 8.3, were added to the Eppendorf tube containing the neuron's contents.";
			JCas cas = getTokenizedTestCas(sentence);

			PipelineBuilder builder = new JcasPipelineBuilder(cas);
			builder.add(BrainRegionAnnotator.class, PARAM_MODEL_FILE,
					MODEL_FILE);
			builder.process();

			for (Annotation annotation : select(cas, Annotation.class)) {
				System.out.println(To.string(annotation));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("An error occurs during the annotator loading.");
		}

	}

	@Test
	public void testNeuronAnnotatorSetUp() {
		try {
			JCas cas = getTokenizedTestCas("narrow pyramidal neuron projecting to thalamus");

			PipelineBuilder builder = new JcasPipelineBuilder(cas);
			builder.add(getConceptMapper("blueonto1/neuron"));
			builder.process();

			for (Annotation annotation : select(cas, Annotation.class)) {
				System.out.println(To.string(annotation));
			}
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("An error occurs during the annotator loading.");
		}
	}
}
