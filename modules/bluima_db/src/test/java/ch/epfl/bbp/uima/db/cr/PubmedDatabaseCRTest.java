package ch.epfl.bbp.uima.db.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.BlueUima.PARAM_BETWEEN;
import static ch.epfl.bbp.uima.BlueUima.PARAM_SKIP_EMPTY_DOCS;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.FSArray;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.cr.PubmedDatabaseCR;
import de.julielab.jules.types.AuthorInfo;
import de.julielab.jules.types.Header;

@Ignore
// TODO requires db access
public class PubmedDatabaseCRTest {

    @Test
    public void testLimit10() throws Exception {

        CollectionReader cr = createReader(PubmedDatabaseCR.class,
                BlueUima.PARAM_BETWEEN, new int[] { 0, 9 },
                BlueUima.PARAM_SKIP_EMPTY_DOCS, false);

        ArrayList<JCas> jCases = asList(cr);
        assertEquals(9, jCases.size());

        for (JCas jCas : jCases) {
            int docId = getHeaderIntDocId(jCas);
            System.err.println(docId);
        }
    }

    @Test
    public void testOffset10() throws Exception {

        List<JCas> jCases = asList(createReader(PubmedDatabaseCR.class,
                PARAM_BETWEEN, new int[] { 5, 14 }, PARAM_SKIP_EMPTY_DOCS,
                false));

        assertEquals(10, jCases.size());
    }

    @Test
    public void testAuthors() throws Exception {

        // http://www.ncbi.nlm.nih.gov/pubmed/?term=1&report=xml&format=text
        CollectionReader cr = createReader(PubmedDatabaseCR.class,
                BlueUima.PARAM_BETWEEN, new int[] { 0, 1 },
                BlueUima.PARAM_SKIP_EMPTY_DOCS, false);

        String[] lastNames = { "Makar", "McMartin", "Palese", "Tephly" };
        String[] foreNames = { "A B", "K E", "M", "T R" };
        // AB___A B___Makar__-__KE___K
        // E___McMartin__-__M___M___Palese__-__TR___T R___Tephly
        for (JCas jCas : asList(cr)) {
            Header header = JCasUtil.selectSingle(jCas, Header.class);

            FSArray authors = header.getAuthors();
            for (int i = 0; i < authors.size(); i++) {
                AuthorInfo a = (AuthorInfo) authors.get(i);
                assertEquals(foreNames[i], a.getForeName());
                assertEquals(lastNames[i], a.getLastName());
            }

            assertEquals("1976-01-16", header.getCopyright());
        }
    }
}
