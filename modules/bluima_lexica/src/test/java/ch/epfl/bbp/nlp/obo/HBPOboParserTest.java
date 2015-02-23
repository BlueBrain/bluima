package ch.epfl.bbp.nlp.obo;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;

import ch.epfl.bbp.nlp.obo.Biostar45366.Term;
import ch.epfl.bbp.uima.obo.OBOOntology;
import ch.epfl.bbp.uima.obo.OntologyTerm;

public class HBPOboParserTest {
    private static final Logger LOG = getLogger(HBPOboParserTest.class);

    @Test
    @Ignore
    public void testName() throws Exception {

        OBOOntology oboOntology = new OBOOntology();
        oboOntology.read(new File(
                "src/main/resources/obo/hbp_neurotransmitter_ontology.obo"));

        for (Entry<String, OntologyTerm> a : oboOntology.getTerms().entrySet()) {
            System.out.println(a);
        }

    }
}
