package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.util.CasCreationUtils;
import org.junit.Test;

import ch.epfl.bbp.uima.types.CellTypeProteinConcentration;
import ch.epfl.bbp.uima.typesystem.Prin;

/**
 * @author renaud.richardet@epfl.ch
 */
public class XmlTestcaseCollectionReaderTest {

    @Test
    public void test() throws Exception {

        CollectionReader cr = createReader(XmlTestcaseCollectionReader.class,
                PARAM_INPUT_FILE, "testcases/example.xml");

        CAS cas = CasCreationUtils
                .createCas(cr.getProcessingResourceMetaData());
        cr.getNext(cas);
        cr.close();

        Collection<CellTypeProteinConcentration> prots = JCasUtil.select(
                cas.getJCas(), CellTypeProteinConcentration.class);
        assertTrue(prots.size() > 1);
        Prin.t(prots);
        // TODO assert on object
    }
}
