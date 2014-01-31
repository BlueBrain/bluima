package ch.epfl.bbp.uima.cr;

import static ch.epfl.bbp.uima.typesystem.TypeSystem.JULIE_TSD;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReader;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.util.CasCreationUtils.createCas;
import static org.junit.Assert.assertEquals;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.factory.AnalysisEngineFactory;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.junit.Ignore;
import org.junit.Test;

import de.julielab.jules.types.BioEntityMention;

/**
 * @author renaud.richardet@epfl.ch
 */
public class GeniaCorpusCollectionReaderTest extends JCasAnnotator_ImplBase {

    @Test
    @Ignore
    // too slow
    public void test() throws Exception {
        CollectionReader cr = createReader(GeniaCorpusCollectionReader.class,
                JULIE_TSD);
        SimplePipeline
                .runPipeline(
                        cr,
                        AnalysisEngineFactory
                                .createEngineDescription(GeniaCorpusCollectionReaderTest.class));

    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (BioEntityMention mention : select(jCas, BioEntityMention.class)) {

            if (mention.getSpecificType() != null
                    && mention.getSpecificType().startsWith("G#protein"))
                System.out.println(mention.getCoveredText());
        }
    }

    @Test
    public void testFaster() throws Exception {
        CollectionReader cr = createReader(GeniaCorpusCollectionReader.class,
                JULIE_TSD);
        cr.hasNext();
        CAS cas = createCas(cr.getProcessingResourceMetaData());
        cr.getNext(cas);
        assertEquals("Activation of the CD", cas.getJCas().getDocumentText()
                .substring(0, 20));
        cr.close();
    }

}
