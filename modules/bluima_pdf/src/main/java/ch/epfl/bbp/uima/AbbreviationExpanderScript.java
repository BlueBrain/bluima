package ch.epfl.bbp.uima;

import static ch.epfl.bbp.uima.BlueUima.PARAM_INPUT_FILE;
import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.pipeline.SimplePipeline.runPipeline;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.ae.AbbreviationsAnnotator;
import ch.epfl.bbp.uima.cr.SingleFileReader;
import ch.epfl.bbp.uima.pdf.cr.PdfCollectionAnnotator;
import ch.epfl.bbp.uima.typesystem.To;
import de.julielab.jules.types.Abbreviation;

public class AbbreviationExpanderScript extends JCasAnnotator_ImplBase {
    Logger LOG = LoggerFactory.getLogger(AbbreviationExpanderScript.class);

    public static void main(String[] args) throws Exception {

        runPipeline(
                createReaderDescription(SingleFileReader.class,
                        PARAM_INPUT_FILE,
                        "src/test/resources/yuri/2019.full.pdf"), //
                createEngineDescription(PdfCollectionAnnotator.class),//
                createEngineDescription(AbbreviationsAnnotator.class,
                        "retrain", true),//
                createEngineDescription(AbbreviationExpanderScript.class)//
        );

    }

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        Collection<Abbreviation> abrevs = select(jCas, Abbreviation.class);
        for (Abbreviation abrev : abrevs) {
            LOG.debug(To.string(abrev));
        }
    }

}
