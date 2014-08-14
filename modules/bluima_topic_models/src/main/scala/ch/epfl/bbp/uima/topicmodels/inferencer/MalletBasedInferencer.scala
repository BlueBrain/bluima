package ch.epfl.bbp.uima.topicmodels.inferencer
import cc.mallet.topics.TopicInferencer
import cc.mallet.topics.MyTopicInferencer
import ch.epfl.bbp.uima.topicmodels.mallet.MalletUtils
import ch.epfl.bbp.uima.ae.GarbageCollectorAnnotator
import com.google.common.base.Preconditions

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

     Preconditions.checkArgument(totalCycles>burnin, "totalCycles must be > burnin", "")
     /**iter, burnin, thin: 
      * These parameters control how many Gibbs sampling draws are made. 
      * The first burnin iterations are discarded and then every thin iteration 
      * is returned for iter iterations.*/
    inferencer.getSampledDistribution(MalletUtils.createInstance(doc, alphabet, dict), totalCycles, 10, burnin)
    // FIXME thin is hardcoded!
  }
}
