package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Map;

import org.junit.Test;

import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

public class NeuronamesXmlParserTest {

    @Test
    public void test() throws Exception {
        Map<String, Concept> nn = NeuronamesXmlParser.parse();
        assertEquals(2861, nn.size());

        Concept brain = nn.get("brain");
        assertNotNull(brain);
        assertEquals(5, brain.getVariants().size());
    }
}
