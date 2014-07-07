package ch.epfl.bbp.uima.topicmodels.estimator
import java.io.File
import java.io.PrintStream
import scala.collection.JavaConversions._
import cc.mallet.topics.MarginalProbEstimator
import cc.mallet.types.FeatureSequence
import cc.mallet.types.Instance
import cc.mallet.types.InstanceList
import cc.mallet.types.LabelAlphabet
import ch.epfl.bbp.uima.topicmodels.mallet.MalletUtils

/**
 * Front end for Mallets MarginalProbEstimator
 */
class MalletBasedEstimator(counts: Array[Array[Int]], alpha: Array[Double], beta: Double, docs: List[List[String]], dict: Map[String, Int]) {

  assert(counts(0).length == alpha.length)

  val newAlphabet = MalletUtils.getAlphabet(dict, docs)
  val outOfVocTokens = newAlphabet.size() - dict.size()

  val nrTopics = alpha.length

  def estimateLikelihood(cycles: Int) = {
    val resampling = true

    println("Using alpha[0]=" + alpha(0) + " beta=" + beta)
    val typeTopicCounts = MalletUtils.getTypeTopicCounts(counts)

    val adjustedMatrix = typeTopicCounts ++ Array.fill[Array[Int]](outOfVocTokens)(Array.fill[Int](0)(0)) // append some empty arrays
    
    val estimator = new MarginalProbEstimator(nrTopics, alpha, alpha.sum,
      beta,
      adjustedMatrix,
      MalletUtils.tokensPerTopic(counts))

    val inst = MalletUtils.createInstances(docs, newAlphabet, dict)

     estimator.evaluateLeftToRight(inst, cycles, resampling, System.out)
  }
}

