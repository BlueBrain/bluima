package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.CasUtil.indexCovering;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.cas.DoubleArray;
import org.apache.uima.fit.component.JCasAnnotator_ImplBase;
import org.apache.uima.fit.descriptor.OperationalProperties;

import com.google.common.collect.Maps;

import ch.epfl.bbp.Histogram;
import ch.epfl.bbp.MapUtils;
import ch.epfl.bbp.uima.types.BrainRegionDictTerm;
import ch.epfl.bbp.uima.types.Topic;
import de.julielab.jules.types.Token;

/**
 * see 20130729_eval_topic_dist_on_br_and_prots
 * 
 * @author renaud.richardet@epfl.ch
 */
@OperationalProperties(multipleDeploymentAllowed = false)
public class EvalTopicDistOnBrAndProts extends JCasAnnotator_ImplBase {

    private Histogram<Integer> brI = new Histogram<Integer>();
    private Histogram<Integer> brO = new Histogram<Integer>();

    @Override
    public void process(JCas jCas) throws AnalysisEngineProcessException {

        final Map<AnnotationFS, Collection<AnnotationFS>> coveringBrainRegions = indexCovering(
                jCas.getCas(), //
                getType(jCas, Token.class),
                getType(jCas, BrainRegionDictTerm.class));

        // topics from DCA. not all tokens have topics (e.g. tokens
        // representing stopwords, or hapax)
        // key: each token; value: a list of Topics covering that token
        final Map<AnnotationFS, Collection<AnnotationFS>> coveringTopics = indexCovering(
                jCas.getCas(), //
                getType(jCas, Token.class), getType(jCas, Topic.class));

        for (Token t : select(jCas, Token.class)) {

            // get topic dist
            List<Integer> topics = newArrayList();
            Map<Integer, Double> topics2 = Maps.newHashMap();
            List<Integer> topics2L = newArrayList();
            try {
                Topic top = (Topic) coveringTopics.get(t).iterator().next();
                DoubleArray scores = top.getScores();
                for (int topic_id = 0; topic_id < scores.size(); topic_id++) {
                    double score = scores.get(topic_id);
                     //System.out.println(topic_id+"\t"+score);
                     topics2.put(topic_id,score);
                    if (score >= 0.05d) {
                        topics.add(topic_id);
                    }
                }
                
                topics2=MapUtils.sortByValue(topics2, true);
                int k=0;
                for (Integer integer : topics2.keySet()) {
                    if (k++<5)
                        topics2L.add(integer);
                }
            } catch (Exception e) {// nope
            }

            if (coveringBrainRegions.containsKey(t)) {
                for (Integer topic : topics2L)
                    brI.add(topic);

            } else {
                for (Integer topic : topics2L)
                    brO.add(topic);
            }
        }
    }

    @Override
    public void collectionProcessComplete()
            throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
        System.out.println(">>Inside:\n" + brI);
        System.out.println(">>Outside:\n" + brO);
    }
}
