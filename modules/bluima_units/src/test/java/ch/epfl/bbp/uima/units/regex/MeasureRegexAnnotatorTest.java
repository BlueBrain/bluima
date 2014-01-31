package ch.epfl.bbp.uima.units.regex;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static ch.epfl.bbp.uima.ae.MeasureRegexAnnotators.BLUE_UIMA_MEASURES;
import static ch.epfl.bbp.uima.ae.MeasureRegexAnnotators.addMeasureAnnotators;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.junit.Assert.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.apache.uima.fit.util.CasUtil;
import org.apache.uima.fit.util.JCasUtil;

import ch.epfl.bbp.uima.ae.MeasureRegexAnnotators;
import ch.epfl.bbp.uima.ae.OpenNlpHelper;
import ch.epfl.bbp.uima.annotationviewer.BlueAnnotationViewerAnnotator;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionReader;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.BodyCitation;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import ch.epfl.bbp.uima.uimafit.SimplePipelineBuilder;
import ch.epfl.bbp.uima.xml.TestResourceParser;
import ch.epfl.bbp.uima.xml.testresources.Output;
import ch.epfl.bbp.uima.xml.testresources.Result;
import ch.epfl.bbp.uima.xml.testresources.UnitTests;
import de.julielab.jules.types.Token;

/**
 * Compares the text extracted by the measure regexes with a gold standard
 * (specified as a generic input-output testcase xml)
 * 
 * @author renaud.richardet@epfl.ch
 * @author joelle.portmann@epfl.ch
 */
public class MeasureRegexAnnotatorTest {
    private static Logger LOG = getLogger(MeasureRegexAnnotatorTest.class);

    @Test
    public void testSimple() throws Exception {

        JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
        MeasureRegexAnnotators.addMeasureAnnotators(pipeline);

        JCas jCas = UimaTests.getTokenizedTestCas("48 Hertz");
//        JCas jCas = UimaTests.getTokenizedTestCas("43 - 55 milliseconds");
        pipeline.process(jCas);
        LOG.debug(To.string(jCas));
    }

    @Test
    public void testSimple2() throws Exception {

        JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
        MeasureRegexAnnotators.addMeasureAnnotators(pipeline);

        JCas jCas = UimaTests
                .getTokenizedTestCas("aa bb 70 kg ddd 24 mm ddd 2002.");
        pipeline.process(jCas);
        LOG.debug(To.string(jCas));
    }

    @Test
    @Ignore
    public void testPdfs() throws Exception {

        String pdfs = "/Users/richarde/data/_papers_etc/pmc_pdfs_sample/";

        PipelineBuilder pipeline = new SimplePipelineBuilder(createReaderDescription(
                PdfCollectionReader.class, JULIE_TSD, PARAM_INPUT_DIRECTORY,
                pdfs));

        addMeasureAnnotators(pipeline);
        pipeline.add(BlueAnnotationViewerAnnotator.class);

        pipeline.process();
    }

    @Test
    public void testWithXmlTestsuite() throws Exception {

        JcasPipelineBuilder pipeline = new JcasPipelineBuilder();
        pipeline.add(OpenNlpHelper.getSentenceSplitter());
        pipeline.add(OpenNlpHelper.getTokenizer());
        MeasureRegexAnnotators.addMeasureAnnotators(pipeline);

        UnitTests tests = new TestResourceParser()
                .parse(new FileInputStream(BLUE_UIMA_MEASURES
                        + "src/test/resources/regex_concepts/measures_test.xml"));

        for (Result result : tests.getResult()) {
            testSingleResult(pipeline, result, Measure.class.getName());
        }
    }

    static void testSingleResult(JcasPipelineBuilder pipeline, Result result,
            String annotation) throws UIMAException, IOException,
            CpeDescriptorException {

        // create a CAS with the input value and run a pipeline on it
        String inputText = result.getInput().getValue();
        JCas jCas = UimaTests
                .getTestCas("And this is with great pleasure that we will show "
                        + inputText + " to the world.");
        pipeline.process(jCas);

        String ts = "";
        for (Token t : select(jCas, Token.class)) {
            ts += t.getCoveredText() + "__";
        }
        LOG.debug("tokenized cas: {}", ts);

        // retrieve the extracted annotation from the pipeline
        Collection<AnnotationFS> extractedConc = CasUtil.select(jCas.getCas(),
                CasUtil.getType(jCas.getCas(), annotation));
        //System.err.println(extractedConc.size());

        StringBuilder sb = new StringBuilder();
        for (AnnotationFS a : extractedConc) {
            sb.append("\n" + To.string(a) + "");
        }
        LOG.debug(extractedConc.size() + " match(es) for input text '{}', {}",
                inputText, sb.toString());
        if (extractedConc.size() > 1)
            throw new AssertionError("more than one " + annotation
                    + " matched, " + To.string(extractedConc));

        // get the expected Measure from the test file
        List<Output> expectedConc = result.getOutput();
        boolean hasExpectedConc = expectedConc.size() > 0
                && expectedConc.get(0).getId() != null;

        // 1st case: there is neither an extracted nor an expected match for the
        // given input (both lists are empty) ---> OK
        if (!hasExpectedConc && extractedConc.isEmpty()) {
            LOG.debug(annotation
                    + " extraction OK for '{}', no output/match expected",
                    inputText);
            return;

            // 2nd case: a match was expected but we missed it! ---> ERROR (a
            // slip)
        } else if (hasExpectedConc && extractedConc.isEmpty()) {
            throw new AssertionError("no " + annotation + " extracted from: '"
                    + inputText + "', but we expected:\n"
                    + To.string(expectedConc));

            // 3rd case: we accidentally extracted a Measure which was not
            // supposed to be one! ---> ERROR (a false positive)
        } else if (!hasExpectedConc && extractedConc.size() > 0) {
            throw new AssertionError("no output/match expected: "
                    + To.string(expectedConc) + ", but found: "
                    + To.string(extractedConc));

            // last case: we have both, an expected output AND an extracted
            // output ---> CHECK identity
        } else {
            AnnotationFS a = extractedConc.iterator().next();
            // only the first-extracted Measure is considered! (no loop)

            // compares the discovered Measure to the expected output
            // BE CAREFUL: only the EXPECTED output features will be tested (the
            // non-specified features remain unchecked!!)
            // uses "reflexion" on the UIMA API
            for (Output o : expectedConc) {

                String id = o.getId();
                if (id != null) {

                    Feature feature = a.getType().getFeatureByBaseName(id);
                    String rangeType = feature.getRange().getName();

                    if (rangeType.equals("uima.cas.String")) {

                        String extractedStringValue = a.getStringValue(feature);
                        assertEquals(id + " should match ", o.getValue(),
                                extractedStringValue);

                    } else if (rangeType.equals("uima.cas.Float")) {

                        Float extractedFloatValue = a.getFloatValue(feature);
                        assertEquals(id + " should match ",
                                new Float(o.getValue()), extractedFloatValue);

                    } else if (rangeType.equals("uima.cas.Integer")) {

                        Integer extractedFloatValue = (int) a
                                .getLongValue(feature);
                        assertEquals(id + " should match ",
                                new Integer(o.getValue()), extractedFloatValue);

                    } else {
                        throw new AssertionError("type " + rangeType
                                + " not supported yet. Go fix it :-)");
                    }
                }
            }
        }
    }
}
