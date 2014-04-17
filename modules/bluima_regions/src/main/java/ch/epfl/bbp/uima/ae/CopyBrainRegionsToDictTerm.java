package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;

/**
 * Workaround to copy {@link BrainRegion} to {@link BrainRegionDictTerm} with
 * text
 * 
 * @author renaud.richardet@epfl.ch
 */
public class CopyBrainRegionsToDictTerm extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        for (BrainRegion br : select(jCas, BrainRegion.class)) {

            BrainRegionDictTerm brdt = new BrainRegionDictTerm(jCas,
                    br.getBegin(), br.getEnd());
            brdt.setEntityId(br.getCoveredText().replaceAll("[\r\t\n]", ""));
            brdt.addToIndexes();
        }
    }
}