package ch.epfl.bbp.uima.xml;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.junit.Test;

import ch.epfl.bbp.uima.XmlHelper;
import ch.epfl.bbp.uima.utils.ConceptFileWriter.Concept;

public class ConceptMapperParserTest {

    @Test
    public void test() throws Exception {

        File age = new File(XmlHelper.XML_TEST_RESOURCES
                + "ConceptMapperParser/age.xml");

        Map<String, Concept> onto = ConceptMapperParser.parse(age);
        assertEquals(46, onto.size());
        Concept infant = onto.get("age:INFANT");

        assertEquals(4, infant.getVariants().size());
    }

}
