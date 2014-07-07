package ch.epfl.bbp.uima.topicmodels.inferencer
import cc.mallet.topics.TopicInferencer
import ch.epfl.bbp.uima.topicmodels.mallet.MalletUtils
import ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator

/**
 * Wrapper for Mallet's TopicInferencer.
 */
class MalletBasedInferencer(var counts: Array[Array[Int]], alpha: Array[Double], beta: Double, dict: Map[String, Int]) {

   val alphabet = MalletUtils.getAlphabet(dict,  Nil)
   val inferencer = new TopicInferencer(MalletUtils.getTypeTopicCounts(counts),
      MalletUtils.tokensPerTopic(counts),
      alphabet,
      alpha,
      beta,
      beta * alphabet.size)
   
   counts = null // frees memory
   GarbageCollectorAnnotator.runGC
   
  def inference(doc: List[String], totalCycles: Int, burnin: Int): Array[Double] = {
     
//    val alphabet = MalletUtils.getAlphabet(dict, doc :: Nil)
//
//    val inferencer = new TopicInferencer(MalletUtils.getTypeTopicCounts(counts),
//      MalletUtils.tokensPerTopic(counts),
//      alphabet,
//      alpha,
//      beta,
//      beta * alphabet.size)

     /**iter, burnin, thin: 
      * These parameters control how many Gibbs sampling draws are made. 
      * The first burnin iterations are discarded and then every thin iteration 
      * is returned for iter iterations.*/
    inferencer.getSampledDistribution(MalletUtils.createInstance(doc, alphabet, dict), totalCycles, 100, burnin)
    // FIXME thin is hardcoded!
  }
}
