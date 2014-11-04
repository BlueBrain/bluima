package ch.epfl.bbp.uima.ae;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderIntDocId;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.epfl.bbp.uima.types.DocumentSpecies;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;

public class SpeciesStats extends JCasAnnotator_ImplBase {
    private static Logger LOG = LoggerFactory.getLogger(SpeciesStats.class);

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        int pmId = getHeaderIntDocId(jCas);

        DocumentSpecies docSpecies = selectSingle(jCas, DocumentSpecies.class);

        String msg = pmId + "\t" + docSpecies.getFamilyName();
        for (LinnaeusSpecies species : select(jCas, LinnaeusSpecies.class)) {
            msg += "\t" + species.getMostProbableSpeciesId() + "\t"
                    + species.getCoveredText().replaceAll("\t", " ");
        }

        LOG.debug(msg);
    }

}
