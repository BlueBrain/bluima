package ch.epfl.bbp.uima.ae.output;

import static java.lang.System.out;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.jcas.JCas;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;

import ch.epfl.bbp.Histogram;

/**
 * Statistics (histogram) of document text length. Bins are rounded to 100
 * 
 * @author renaud.richardet@epfl.ch *
 */
// because of instance fields:
@OperationalProperties(multipleDeploymentAllowed = false)
public class StatsTextAnnotator extends JCasAnnotator_ImplBase {

    private int emptyDocs = 0;
    private Histogram<Integer> histogram = new Histogram<Integer>();

    public void process(JCas jCas) throws AnalysisEngineProcessException {

        String text = jCas.getDocumentText();
        if (text == null || text.length() == 0) {
            emptyDocs++;
        } else {
            int l = (text.length() / 100) * 100;
            histogram.add(l);
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        out.println("StatsTextAnnotator\nempty\t" + emptyDocs + "\n"
                + histogram.toString() + "\n");
    }
}
