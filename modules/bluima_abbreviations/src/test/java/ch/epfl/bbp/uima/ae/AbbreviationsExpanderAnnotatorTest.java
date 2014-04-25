package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.AbbreviationsAnnotatorTest.SAMPLE_TXT;
import static ch.epfl.bbp.uima.testutils.UimaTests.getTestCas;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.regex.Pattern.compile;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.Protein;
import ch.epfl.bbp.uima.typesystem.To;
import ch.epfl.bbp.uima.uimafit.JcasPipelineBuilder;
import ch.epfl.bbp.uima.uimafit.PipelineBuilder;
import de.julielab.jules.types.Abbreviation;

public class AbbreviationsExpanderAnnotatorTest extends JCasAnnotator_ImplBase {
    protected static final Logger LOG = LoggerFactory
            .getLogger(AbbreviationsExpanderAnnotatorTest.class);

    @Test
    public void testAbrevs() throws Exception {

        JCas jCas = getTestCas(SAMPLE_TXT);

        PipelineBuilder p = new JcasPipelineBuilder(jCas);
        p.add(AbbreviationsExpanderAnnotatorTest.class);
        p.add(AbbreviationsAnnotator.class);
        p.add(AbbreviationsExpanderAnnotator.class);
        p.process();

        Collection<Abbreviation> abrevs = select(jCas, Abbreviation.class);
        for (Abbreviation abrev : abrevs) {
            LOG.debug(To.string(abrev));
        }
        assertEquals(15, abrevs.size());

        List<Protein> prots = newArrayList(select(jCas, Protein.class));
        for (Protein prot : prots)
            LOG.debug(To.string(prot));
        assertEquals(6, prots.size());

        // set by ForTestingAnnotator
        // Protein[glutamic acid decarboxylase]val:dummyNameToTestClone;id:null
        // Protein[calbindin]val:dummyNameToTestClone;id:null
        // Protein[dysplasia]val:dummyNameToTestClone;id:null

        // set by AbrevexpanderAnnotator
        // Protein[GAD]val:dummyNameToTestClone;id:null
        // Protein[CB]val:dummyNameToTestClone;id:null
        // Protein[CB]val:dummyNameToTestClone;id:null
    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        // glutamic acid decarboxylase: example of multi-word
        // calbindin: never gets used in the text again
        // dysplasia: used in the text
        Pattern p = compile("glutamic acid decarboxylase|calbindin|dysplasia");
        Matcher m = p.matcher(jCas.getDocumentText());
        while (m.find()) {
            Protein dummy = new Protein(jCas, m.start(), m.end());
            dummy.setName("dummyNameToTestClone");
            dummy.addToIndexes();
        }
    }
}
