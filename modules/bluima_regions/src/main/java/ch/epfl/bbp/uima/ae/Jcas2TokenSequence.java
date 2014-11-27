package ch.epfl.bbp.uima.ae;

import static com.google.common.collect.Lists.newArrayList;
import static org.apache.uima.fit.util.CasUtil.indexCovering;
import static org.apache.uima.fit.util.JCasUtil.getType;
import static org.apache.uima.fit.util.JCasUtil.select;
import static org.apache.uima.fit.util.JCasUtil.selectCovered;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.jcas.JCas;

import cc.mallet.pipe.Pipe;
import cc.mallet.types.Instance;
import cc.mallet.types.TokenSequence;
import ch.epfl.bbp.uima.types.BrainRegion;
import ch.epfl.bbp.uima.types.Gold;
import ch.epfl.bbp.uima.types.LinnaeusSpecies;
import ch.epfl.bbp.uima.types.Measure;
import ch.epfl.bbp.uima.types.Topic;
import de.julielab.jules.types.Sentence;
import de.julielab.jules.types.Token;

/**
 * Creates a {@link cc.mallet.types.TokenSequence} from a {@link JCas}, reusing
 * the UIMA {@link Sentence} and {@link Token}. Sets the (true) label based on
 * the presence of a {@link BrainRegion} covering the {@link Token}.
 * 
 * @author renaud.richardet@epfl.ch
 */
public class Jcas2TokenSequence extends Pipe {
    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_POS = "u_POS_";
    public static final String PROPERTY_LEMMA = "u_LEMMA_";
    public static final String PROPERTY_TEXT = "u_TEXT_";
    public static final String PROPERTY_TOPICS = "u_TOPICID_";
    public static final String PROPERTY_WORDVECTOR = "u_WORDVECT_";
    public static final String PROPERTY_SPECIES = "u_SPECIES_";
    public static final String PROPERTY_UNITS = "u_UNITS_";

    public static final String TARGET_I = "I";
    public static final String TARGET_O = "O";

    public static final boolean NEW_FEATURES = true;

    /**
     * IN: one {@link JCas} <br/>
     * data : 1 {@link JCas}<br>
     * targ : none<br>
     * name : pmId<br>
     * srce : {@link JCas}<br>
     * <br>
     * OUT: {@link Instance}s, each corresponding to a {@link Sentence}<br/>
     * data : a {@link TokenSequence}, one {@link cc.mallet.types.Token} per
     * UIMA {@link Token}<br>
     * targ : labels for training (if any), based on covering UIMA
     * {@link BrainRegion} annotations<br>
     * name : pmId<br>
     * srce : {@link Sentence}<br>
     * <br>
     * Note: this {@link Pipe} creates more {@link Instance} than it gets (one
     * output {@link Instance} per input {@link Sentence}).
     */
    @SuppressWarnings("unused")
    @Override
    public Iterator<Instance> newIteratorFrom(Iterator<Instance> source) {
        List<Instance> output = new LinkedList<Instance>();

        while (source.hasNext()) {
            Instance carrier = (Instance) source.next();
            JCas jCas = (JCas) carrier.getData();
            int pmId = (Integer) carrier.getName();

            // gold labels from training corpus (represent BRs)
            // key: token that are covered; val: list of BR covering that token
            final Set<AnnotationFS> coveringBrainRegions = indexCovering(
                    jCas.getCas(), //
                    getType(jCas, Token.class), getType(jCas, Gold.class))
                    .keySet();

            // key: token that are covered; val: list of BR covering that token
            final Map<AnnotationFS, Collection<AnnotationFS>> coveringMeasures = indexCovering(
                    jCas.getCas(), //
                    getType(jCas, Token.class), getType(jCas, Measure.class));

            // topics from DCA. not all tokens have topics (e.g. tokens
            // representing stopwords, or hapax)
            // key: each token; value: a list of Topics covering that token
            final Map<AnnotationFS, Collection<AnnotationFS>> coveringTopics = indexCovering(
                    jCas.getCas(), //
                    getType(jCas, Token.class), getType(jCas, Topic.class));

            final Collection<LinnaeusSpecies> species = select(jCas,
                    LinnaeusSpecies.class);

            int sentId = 0;
            for (Sentence s : select(jCas, Sentence.class)) {

                List<cc.mallet.types.Token> data = newArrayList();
                TokenSequence target = new TokenSequence();

                for (Token t : selectCovered(Token.class, s)) {

                    cc.mallet.types.Token malletToken = new cc.mallet.types.Token(
                            t.getCoveredText());
                    data.add(malletToken);

                    // POS, LEMMA
                    malletToken.setFeatureValue(PROPERTY_POS + t.getPos(), 1.0);
                    // /if (GridSearchConfiguration.getBoolean("Lemma")) {
                    if (t.getLemmaStr() != null && t.getLemmaStr().length() > 1)
                        malletToken.setFeatureValue(
                                PROPERTY_LEMMA + t.getLemmaStr(), 1.0);
                    // else
                    // malletToken.setFeatureValue(
                    // PROPERTY_TEXT + t.getCoveredText(), 1.0);

                    // // Word2class
                    // if (false) {
                    // int classz = Word2VecUtils.getClass(t.getCoveredText());
                    // malletToken.setFeatureValue(PROPERTY_WORDVECTOR//
                    // + classz, 1.0);
                    // }
                    //
                    // // Word2vec
                    // if (false) {
                    // float[] wordVector = Word2VecUtils.getWordVector(t
                    // .getCoveredText());
                    // if (wordVector != null) {
                    //
                    // for (int j = 0; j < wordVector.length; j++) {
                    // malletToken.setFeatureValue(PROPERTY_WORDVECTOR//
                    // + j, wordVector[j]);
                    // }
                    // } else {
                    // System.out.println("no wordvec for "
                    // + t.getCoveredText());
                    // }
                    // }

                    /*-
                    // TOPICS
                    if (coveringTopics.containsKey(t)) {
                        Topic top = (Topic) coveringTopics.get(t).iterator()
                                .next();

                        if ("a".equals("a")) {
                            malletToken.setFeatureValue(PROPERTY_TOPICS//
                                    + top.getMostLikelyTopic(), 1.0);

                        } else {
                            int topicScenario = StaticOption
                                    .getInt("topScenario");

                            if (topicScenario == 1) { // top1
                                malletToken.setFeatureValue(PROPERTY_TOPICS//
                                        + top.getMostLikelyTopic(), 1.0);

                            } else if (topicScenario == 2) { // staged

                                // format: u_TOPICID_{topicId}_{category}
                                DoubleArray scores = top.getScores();
                                for (int topic_id = 0; topic_id < scores.size(); topic_id++) {
                                    double score = scores.get(topic_id);
                                    // System.out.println(topic_id+"\t"+score);
                                    if (score >= 0.01d && score < 0.05d) {
                                        malletToken.setFeatureValue(
                                                PROPERTY_TOPICS + topic_id
                                                        + "_1", 1.0);
                                    } else if (score >= 0.05d && score < 0.1d) {
                                        malletToken.setFeatureValue(
                                                PROPERTY_TOPICS + topic_id
                                                        + "_2", 1.0);
                                    } else if (score >= 0.1d && score < 0.2d) {
                                        malletToken.setFeatureValue(
                                                PROPERTY_TOPICS + topic_id
                                                        + "_3", 1.0);
                                    } else if (score >= 0.2d) {
                                        malletToken.setFeatureValue(
                                                PROPERTY_TOPICS + topic_id
                                                        + "_4", 1.0);
                                    }
                                }

                            } else { // topN & minProb

                                int topNTopics = StaticOption
                                        .getInt("topNTopics");
                                double minProb = StaticOption
                                        .getDouble("minProb");
                                for (int topTopic : DCATopicModelsAnnotator
                                        .topNTopics(top.getScores(),
                                                topNTopics, minProb)) {
                                    malletToken.setFeatureValue(PROPERTY_TOPICS
                                            + topTopic, 1.0);
                                }
                            }
                        }
                    }*/

                    // SPECIES
                    if (NEW_FEATURES && species != null && !species.isEmpty()) {
                        for (LinnaeusSpecies specie : species) {
                            malletToken.setFeatureValue(PROPERTY_SPECIES
                                    + specie.getMostProbableSpeciesId(), 1.0);
                        }
                    }

                    // MEASURE
                    if (NEW_FEATURES && coveringMeasures.containsKey(t)) {
                        String unit = null;
                        for (AnnotationFS measure : coveringMeasures.get(t)) {
                            Measure m = (Measure) measure;
                            if (m.getUnit() != null && m.getUnit().length() > 0) {
                                unit = m.getUnit();
                                break;
                            }
                        }
                        if (unit != null) {
                            malletToken.setFeatureValue(PROPERTY_UNITS + unit,
                                    1.0);
                        }
                    }

                    // TARGET annots for brain regions
                    if (coveringBrainRegions.contains(t)) {
                        target.add(TARGET_I);
                    } else {
                        target.add(TARGET_O);
                    }
                }

                output.add(new Instance(new TokenSequence(data), target, pmId
                        + "__" + sentId, null));
                sentId++;
            }
        }
        return output.iterator();
    }
}
