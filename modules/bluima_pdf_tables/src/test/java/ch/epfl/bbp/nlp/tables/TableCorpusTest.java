package ch.epfl.bbp.nlp.tables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class TableCorpusTest {

    // TODO do not hardcode
    final static String CORPUS_PATH = "/Volumes/HDD2/ren_data/data_hdd/corpora/table_corpus/";

    @Test
    public void testCorpus() throws Exception {

        List<Integer> pmIds = TableCorpus.getPmIds(CORPUS_PATH);
        assertEquals("doc cnt should match", 279 + 159, pmIds.size());

        Integer pmId1 = pmIds.get(0);
        List<GoldAnnotation> annotations = TableCorpus.getAnnotation(
                CORPUS_PATH, pmId1);
        assertTrue("the first ones have tables, and therefore annots",
                annotations.size() > 0);

        Integer pmId_no = pmIds.get(pmIds.size() - 1);
        annotations = TableCorpus.getAnnotation(CORPUS_PATH, pmId_no);
        assertTrue("the last ones have no tables, and therefore no annots",
                annotations.size() == 0);
    }
}
