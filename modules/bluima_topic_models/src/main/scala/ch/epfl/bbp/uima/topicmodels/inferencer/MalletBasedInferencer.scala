package ch.epfl.bbp.uima.topicmodels.inferencer
import cc.mallet.topics.TopicInferencer
import ch.epfl.bbp.uima.topicmodels.mallet.MalletUtils

/**
 * Wrapper for Mallet's TopicInferencer.
 */
class MalletBasedInferencer(counts: Array[Array[Int]], alpha: Array[Double], beta: Double, dict: Map[String, Int]) {

   val alphabet = MalletUtils.getAlphabet(dict,  Nil)
   val inferencer = new TopicInferencer(MalletUtils.getTypeTopicCounts(counts),
      MalletUtils.tokensPerTopic(counts),
      alphabet,
      alpha,
      beta,
      beta * alphabet.size)
   
  def inference(doc: List[String], totalCycles: Int, burnin: Int): Array[Double] = {
     
//    val alphabet = MalletUtils.getAlphabet(dict, doc :: Nil)
//
//    val inferencer = new TopicInferencer(MalletUtils.getTypeTopicCounts(counts),
//      MalletUtils.tokensPerTopic(counts),
//      alphabet,
//      alpha,
//      beta,
//      beta * alphabet.size)

    inferencer.getSampledDistribution(MalletUtils.createInstance(doc, alphabet, dict), totalCycles, 10, burnin)
  }
}
