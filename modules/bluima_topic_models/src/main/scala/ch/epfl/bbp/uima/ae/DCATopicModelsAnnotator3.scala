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
import de.julielab.jules.types.Token

/**
 * Annotates a CAS with most probable document topics.
 * Inference of topics based on a previously trained DCA model.
 */
class DCATopicModelsAnnotator3 extends DCATopicModelsAnnotator {

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

    // // annotate tokens with distribution
    // JCasUtil.select(doc, classOf[Token]).foreach { t =>
    //   val tokenTopicDist = inferencer.getTopicsDistributionForWord(t.getCoveredText(), topicDist)
    //   val mostLikelyId = tokenTopicDist.zipWithIndex.max._2 // argmax over topics
    //   t.setTopicIds(mostLikelyId.toString)
    // }
  }
}
