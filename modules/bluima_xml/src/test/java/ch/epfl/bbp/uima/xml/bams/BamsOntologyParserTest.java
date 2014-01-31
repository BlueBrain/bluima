package ch.epfl.bbp.uima.xml.bams;

import static ch.epfl.bbp.uima.xml.bams.Connection.Strength.moderate_strong;
import static org.junit.Assert.assertEquals;
import org.junit.Ignore;

import org.junit.Test;

import ch.epfl.bbp.uima.xml.bams.Connection.Strength;
import ch.epfl.bbp.uima.xml.bams.Reference.Type;

@Ignore
public class BamsOntologyParserTest {

    @Test
    public void test() throws Exception {
        BamsOntologyParser.parse();

    }

    @Test
    public void testParseStrength() throws Exception {
        assertEquals(moderate_strong, Strength.fromString("moderate/strong"));
        assertEquals(Strength.very_strong, Strength.fromString("very strong"));
    }

    @Test
    public void testParseType() throws Exception {
        assertEquals(Type.article,
                Type.fromNs("http://brancusi1.usc.edu/RDF/article"));
    }

}
