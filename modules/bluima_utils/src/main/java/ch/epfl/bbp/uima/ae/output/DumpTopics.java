package ch.epfl.bbp.uima.ae.output;

import static ch.epfl.bbp.uima.BlueCasUtil.getHeaderDocId;
import static org.apache.uima.fit.util.JCasUtil.selectSingle;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.jcas.JCas;

import ch.epfl.bbp.uima.types.TopicDistribution;

/**
 * Dumps {@link TopicDistribution} to sysout
 * 
 * @author renaud.richardet@epfl.ch
 */
public class DumpTopics extends JCasAnnotator_ImplBase {

    final static NumberFormat nf = new DecimalFormat("0.#####");

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        double[] topicDistribution = selectSingle(jCas, TopicDistribution.class)
                .getProbability().toArray();

        System.out.print(getHeaderDocId(jCas) + "\t");
        for (double t : topicDistribution) {
            System.out.print(nf.format(t) + " ");
        }
        System.out.println();
    }
}