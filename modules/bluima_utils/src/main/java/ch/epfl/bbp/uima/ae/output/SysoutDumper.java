package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

/**
 * Dumps to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class SysoutDumper extends JCasAnnotator_ImplBase {

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        String pmId = getHeaderDocId(jCas);
        System.out.println(pmId + " " + jCas.getDocumentText());
    }

}