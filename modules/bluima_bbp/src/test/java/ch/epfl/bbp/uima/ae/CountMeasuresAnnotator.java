package ch.epfl.bbp.uima.ae;

import static org.apache.uima.fit.util.JCasUtil.select;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.uima.types.Measure;

/**
 * 
 * @see Evaluate2_Numbers
 * @author renaud.richardet@epfl.ch
 */
public class CountMeasuresAnnotator extends JCasAnnotator_ImplBase {

    Histogram<String> hist = new Histogram<String>();

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {
        for (Measure m : select(jCas, Measure.class)) {
            hist.add(m.getUnit());
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        System.out.println("Measures: \n" + hist.toString());
    }
}