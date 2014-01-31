package ch.epfl.bbp.uima.db.cr;

import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.junit.Assert.assertEquals;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.uima.collection.CollectionReader;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.BlueUima;
import ch.epfl.bbp.uima.cr.PubmedDatabaseCR;

@Ignore //TODO requires db access
public class PubmedDatabaseCRTest {

   
    @Test
    public void testLimit10() throws Exception {

        CollectionReader cr = createReader(PubmedDatabaseCR.class,
                JULIE_TSD, BlueUima.PARAM_BETWEEN, new int[] { 0, 9 },
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

        CollectionReader cr = createReader(PubmedDatabaseCR.class,
                JULIE_TSD, BlueUima.PARAM_BETWEEN, new int[] { 5, 15 },
                BlueUima.PARAM_SKIP_EMPTY_DOCS, false);

        Iterator<JCas> it = BlueCasUtil.iterator(cr);
        int cnt = 0;
        while (it.hasNext()) {
            JCas jCas = (JCas) it.next();
            int docId = getHeaderIntDocId(jCas);
            System.err.println(docId);
            cnt++;
        }
        assertEquals(10, cnt);
    }
}
