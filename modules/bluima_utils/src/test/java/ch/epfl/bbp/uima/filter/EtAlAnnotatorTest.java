package ch.epfl.bbp.uima.filter;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_ANNOTATION_CLASS;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.metadata.CpeDescriptorException;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.KeepLargestAnnotationAnnotator;
import ch.epfl.bbp.uima.testutils.UimaTests;
import ch.epfl.bbp.uima.types.EtAlInlineReference;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;

public class EtAlAnnotatorTest {
    private static Logger LOG = LoggerFactory
            .getLogger(EtAlAnnotatorTest.class);

    JcasPipelineBuilder pipeline;

    @Test
    public void testWithXmlTestsuite() throws Exception {

        pipeline = new JcasPipelineBuilder();
        pipeline.add(EtAlAnnotator.class);
        pipeline.add(KeepLargestAnnotationAnnotator.class,
                PARAM_ANNOTATION_CLASS, EtAlInlineReference.class.getName());

        testWith("bla bla (Capitalname bla bli blu 1998a) bli bli");
        testWith("bla bla (asf asdf  sad asdf asfd  et al safd sadf fsad  2009) bli bli");
        testWith("bla bla Joachims (1999) bli bli");
        testWith("bla bla Satre et al. (2007) bli bli");
        testWith("bla bla Poirazi and Mel (2001) bli bli");
        testWith("bla bla ( et al ) bli bli");
        testWith("bla bla (Xxxx et al   ) bli bli");
        testWith("bla bla Zucker et al. (1993) bli bli");
        testWith("bla bla et al. bli bli");
    }

    private void testWith(String text) throws UIMAException,
            CpeDescriptorException, IOException {

        JCas jCas = UimaTests.getTestCas(text);
        pipeline.process(jCas);

        List<EtAlInlineReference> etAl = asList(select(jCas,
                EtAlInlineReference.class));
        LOG.debug("etAl" + To.string(etAl));
        assertEquals(1, etAl.size());
        assertEquals(8, etAl.get(0).getBegin());
        assertEquals(text.length() - 8, etAl.get(0).getEnd());
    }
}
