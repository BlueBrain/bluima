package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.MissingUtils.printf;
import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static ch.epfl.bbp.uima.BlueCasUtil.iterator;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Test;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Cooccurrence;
import de.julielab.jules.types.Sentence;

public class WhiteTextConnectionsCollectionReaderTest {

    @Test
    public void test() throws Exception {

        Iterator<JCas> cr = iterator(createReader(WhiteTextConnectionsCollectionReader.class));

        JCas jCas = cr.next();
        System.out.println(jCas.getDocumentText());

        Sentence sentence = select(jCas, Sentence.class).iterator().next();

        List<BrainRegion> brs = selectCovered(BrainRegion.class, sentence);
        assertEquals(4, brs.size());
        Collection<Cooccurrence> cc = selectCovered(Cooccurrence.class,
                sentence);
        assertEquals(0, cc.size());

        jCas = cr.next();
        assertEquals("2341628", getHeaderDocId(jCas));
        sentence = select(jCas, Sentence.class).iterator().next();
        brs = selectCovered(BrainRegion.class, sentence);
        assertEquals(2, brs.size());
        cc = selectCovered(Cooccurrence.class, sentence);
        assertEquals(1, cc.size());
    }

    public static void main(String[] args) throws Exception {
        Iterator<JCas> cr = iterator(createReader(WhiteTextConnectionsCollectionReader.class));
        int docs = 0, sentenceCnt = 0, brCnt = 0, coocCnt = 0;
        while (cr.hasNext()) {
            JCas jCas = cr.next();
            docs++;

            for (Sentence sentence : select(jCas, Sentence.class)) {
                sentenceCnt++;
                List<BrainRegion> brs = selectCovered(BrainRegion.class,
                        sentence);
                brCnt += brs.size();
                Collection<Cooccurrence> cc = selectCovered(Cooccurrence.class,
                        sentence);
                coocCnt += cc.size();
            }
            System.out.println(jCas.getDocumentText());
        }
        printf("{} docs, {} sentences, {} br, {} coocs", docs, sentenceCnt,
                brCnt, coocCnt);
    }
}
