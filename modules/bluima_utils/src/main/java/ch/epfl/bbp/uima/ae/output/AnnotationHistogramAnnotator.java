package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

import ch.epfl.bbp.Histogram;

/**
 * Prints a histogram of the counts of each {@link Annotation}. E.g.
 * 
 * <pre>
 * ch.epfl.bbp.uima.types.BrainRegionDictTerm: 123
 * ch.epfl.bbp.uima.types.Sex: 4312
 * ch.epfl.bbp.uima.types.Rocknroll: 412
 * </pre>
 * 
 * @author renaud.richardet@epfl.ch
 */
public class AnnotationHistogramAnnotator extends JCasAnnotator_ImplBase {

    @ConfigurationParameter(name = "printForEveryCas", defaultValue = "false")
    private boolean printForEveryCas;

    private Histogram<String> histogram = new Histogram<String>();

    public void process(JCas jCas) throws AnalysisEngineProcessException {
        for (Annotation a : jCas.getAnnotationIndex())
            histogram.add(a.getClass().getName());

        if (printForEveryCas) {
            Histogram<String> thisCasHistogram = new Histogram<String>();
            for (Annotation a : jCas.getAnnotationIndex())
                thisCasHistogram.add(a.getClass().getName());
            System.out.println("histogram for " + getHeaderDocId(jCas) + "\n"
                    + histogram.toString() + "------------------------");
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        System.out.println("histogram\n" + histogram.toString()
                + "------------------------\n");
    }
}
