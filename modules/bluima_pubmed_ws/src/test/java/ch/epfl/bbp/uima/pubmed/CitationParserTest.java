package ch.epfl.bbp.uima.pubmed;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class CitationParserTest {

    @Test
    @Ignore// will use GroBid
    public void test() throws Exception {
        Citation c = new CitationParser()
                .parse("Thomson AM & Bannister A. Postsynaptic pyramidal target selection by descending layer III pyramidal axons: dual intracellular recordings and biocytin filling in slices of rat neocortex. Neuroscience 84, 669–683 (1998).");
        assertEquals(
                "Postsynaptic pyramidal target selection by descending layer III pyramidal axons: dual intracellular recordings and biocytin filling in slices of rat neocortex",
                c.getTitle());
        assertEquals(1998, c.getYear());
        assertEquals("[Thomson AM, A Bannister]", c.getAuthors().toString());

        assertEquals(
                "tttit[title] AND me[author] AND 1234[Date - Publication]",
                new Citation().addAuthor("me").setYear(1234).setTitle("tttit")
                        .toPubmedQueryString());
    }
}
