package ch.epfl.bbp.uima.db.cr;

import static ch.epfl.bbp.uima.BlueUima.PARAM_DB_CONNECTION;
import static ch.epfl.bbp.uima.ae.StatsAnnotatorPlus.PARAM_PRINT_EVERY;
import static ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR.PARAM_EXPAND_ABBREVIATIONS;
import static ch.epfl.bbp.uima.testutils.UimaTests.assertResultsContains;
import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngine;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.util.CasCreationUtils.createCas;

import java.util.Collection;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.junit.Ignore;
import org.junit.Test;

import ch.epfl.bbp.uima.ae.StatsAnnotatorPlus;
import ch.epfl.bbp.uima.cr.PubmedWholeDatabaseCR;
import de.julielab.jules.types.pubmed.Header;

@Ignore
public class PubmedWholeDatabaseCRTest {

    @Test
    public void test() throws Exception {

        CollectionReader cr = createReader(PubmedWholeDatabaseCR.class,
                JULIE_TSD);

        CAS cas = createCas(cr.getProcessingResourceMetaData());
        cr.getNext(cas);

        for (int i = 0; i < 10000; i++) {
            cr.hasNext();
            cas = createCas(cr.getProcessingResourceMetaData());
            cr.getNext(cas);
            System.err.println(cas.getDocumentText());
        }

        // FIXME hangs up cr.close();

        Collection<Header> h = select(cas.getJCas(), Header.class);
        assertResultsContains(h, "DocId", "1");
    }

    @Test
    public void test2() throws Exception {

        String[] conn = { "127.0.0.1", "bb_pubmed", "root", "" };

        runPipeline(
                createReader(PubmedWholeDatabaseCR.class, JULIE_TSD,
                        PARAM_DB_CONNECTION, conn, PARAM_EXPAND_ABBREVIATIONS,
                        true),
                createEngine(StatsAnnotatorPlus.class, PARAM_PRINT_EVERY, 1000));
    }

}
