package ch.epfl.bbp.uima.uimafit;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.DotSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.NaiveSentenceSplitterAnnotator;
import ch.epfl.bbp.uima.ae.output.DocumentTextWriter;
import ch.epfl.bbp.uima.cr.TextArrayReader;
import de.julielab.jules.types.Sentence;

public class CpeBuilderTest {

    // @Test FIXME fails on maven cl
    public void testCpeBuilder() throws Exception {

        PipelineBuilder cpeBuilder = new CpeBuilder(createReaderDescription(
                TextArrayReader.class, JULIE_TSD, PARAM_INPUT,
                new String[] { "hello world. how goes?" }))
                .setMaxProcessingUnitThreatCount(1);

        cpeBuilder.add(NaiveSentenceSplitterAnnotator.class);
        cpeBuilder.add(DocumentTextWriter.getAED("target"));

        cpeBuilder.process();
    }

    @Test
    public void testSimplePipelineBuilder() throws Exception {

        PipelineBuilder cpeBuilder = new JcasPipelineBuilder(
                getTestCas("hello world. how goes?"));

        cpeBuilder.add(DotSentenceSplitterAnnotator.class);

        JCas jCas = (JCas) cpeBuilder.process();
        assertEquals("hello world. how goes?", jCas.getDocumentText());
        assertEquals(2, select(jCas, Sentence.class).size());
    }
}
