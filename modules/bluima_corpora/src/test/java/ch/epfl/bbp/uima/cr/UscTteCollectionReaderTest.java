package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.StringUtils.snippetize;
import static ch.epfl.bbp.uima.BlueCasUtil.asList;
import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_DIRECTORY;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.List;

import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.BlueCasUtil;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.typesystem.To;

public class UscTteCollectionReaderTest {
    Logger LOG = LoggerFactory.getLogger(UscTteCollectionReaderTest.class);

    @Test
    @Ignore
    public void test() throws Exception {

        for (JCas jCas : BlueCasUtil.asList(createReader(
                UscTteCollectionReader.class, PARAM_INPUT_DIRECTORY, "dummy"))) {

            LOG.debug("text: {}", snippetize(jCas.getDocumentText(), 200));

            List<BrainRegion> brs = asList(select(jCas, BrainRegion.class));
            for (BrainRegion br : brs)
                LOG.debug("{}-{} " + To.string(br), br.getBegin(), br.getEnd());

            // assertEquals(61, brs.size());

            // BrainRegion br = brs.iterator().next();
            // assertEquals("the cerebellum", br.getCoveredText());
        }
    }
}
