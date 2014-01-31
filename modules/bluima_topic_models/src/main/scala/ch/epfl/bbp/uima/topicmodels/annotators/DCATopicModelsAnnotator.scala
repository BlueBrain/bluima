package ch.epfl.bbp.uima.topicmodels.annotators
import scala.Array.canBuildFrom
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.cas.DoubleArray
import org.apache.uima.jcas.JCas
import org.apache.uima.UimaContext
import org.apache.uima.fit.util.JCasUtil
import ch.epfl.bbp.uima.topicmodels.inferencer.DCAInference
import ch.epfl.bbp.uima.types.FeatureTokens
import ch.epfl.bbp.uima.types.Topic
import ch.epfl.bbp.uima.BlueCasUtil
import ch.epfl.bbp.uima.types.TopicDistribution
import scala.collection.JavaConversions._

/**
 * Annotates a CAS with LDA topics. Inference of topics based on a previously trained DCA model
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.FeatureTokens), 
//    outputs = Array(ch.epfl.bbp.uima.types.Topic, ch.epfl.bbp.uima.types.TopicDistribution))
class DCATopicModelsAnnotator extends JCasAnnotator_ImplBase {
  protected val log = AnnotatorUtils.getLogger
  protected var inferencer: DCAInference = null
  protected var cycles: Int = 100
  protected var burnin: Int = 10

  override def initialize(context: UimaContext) {
    val dir = AnnotatorUtils.getStringParamFromContext(context, DCATopicModelsAnnotator.DCADirectoryPath, "", true)
    val stem = AnnotatorUtils.getStringParamFromContext(context, DCATopicModelsAnnotator.DCAStem, "", true)
    val useMeanTheta = AnnotatorUtils.getBooleanParamFromContext(context, DCATopicModelsAnnotator.UseMeanTheta, false, false)

    inferencer = new DCAInference(dir, stem, false) // estimation of corpus size should be good enough, so not passing anything as a 4th parameter

    cycles = AnnotatorUtils.getIntParamFromContext(context, DCATopicModelsAnnotator.InferenceIterations, cycles, false)
    burnin = AnnotatorUtils.getIntParamFromContext(context, DCATopicModelsAnnotator.InferenceBurnin, burnin, false)

    log.info("Initialized DCA topic models annotator. Using  {} burnin cycles and {} total cycles.", burnin, cycles)
  }

  override def process(doc: JCas) {
    val idRaw = BlueCasUtil.getHeaderDocId(doc)
    val id = if (idRaw != null) idRaw else "unknown"

    val ft = JCasUtil.selectSingle(doc, classOf[FeatureTokens])
    if (ft == null) {
      log.error("Didn't find any feature tokens in document {}!. This annotator has to be preceeded by a FeatureTokensExtractionAnnotator!", id)
    }

    val features = ft.getTokens.toArray.toList

    log.debug("DCAAnnot: Feature tokens in document {}: {}", id, features.size)

    // estimate topic distribution over document
    val topicDist = inferencer.getTopicDistribution(features, cycles, burnin)

    // add it as an annotation to the documents
    val d = new TopicDistribution(doc)
    d.setProbability(new DoubleArray(doc, topicDist.size))
    topicDist.zipWithIndex.foreach(p => d.setProbability(p._2, p._1))
    d.addToIndexes

    // annotate tokens with distribution
    val wordsAssoc = features.map(w => inferencer.getTopicsDistributionForWord(w, topicDist))
    val featurePos = ft.getBeginnings.toArray zip ft.getEndings.toArray
    (featurePos zip wordsAssoc).foreach(f => createTopicAnnotation(doc, f._1, f._2)) // add annotation for each token
  }

  /**
   * Creates a topic annotation for some tokens at position pos and add it to the indexes.
   */
  private def createTopicAnnotation(doc: JCas, pos: (Int, Int), scores: Array[Double]) {
    val topic = new Topic(doc, pos._1, pos._2)
    topic.setScores(new DoubleArray(doc, scores.size))
    scores.zipWithIndex.foreach(p => topic.setScores(p._2, p._1))
    topic.setMostLikelyTopic(scores.zipWithIndex.max._2) // argmax over topic scores
    topic.addToIndexes
  }
}

object DCATopicModelsAnnotator {
  val DCADirectoryPath = "dcaDirectoryPath"
  val DCAStem = "dcaStem"
  val UseMeanTheta = "useMeanTheta"
  val InferenceIterations = "inferenceIterations"
  val InferenceBurnin = "inferenceBurnin"

  def topNTopics(topics: DoubleArray, topN: Int, minProb: Double): Array[Int] = {
    topics.toArray().zipWithIndex // add index, which is the topic id
      .filter(_._1 > minProb) // filter by minimum prob
      .sortBy(-_._1) // sort highest prob first
      .slice(0, topN).map(_._2) // keep topN; return topicId
  }
} 