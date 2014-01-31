package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.uima.typesystem.Prin;

/**
 * Dumps it all to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Dumper extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String pmId = getHeaderDocId(jCas);
        System.out.println(pmId + " -----------------------------");
        Prin.t(jCas);
    }

}