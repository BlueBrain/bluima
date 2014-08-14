package ch.epfl.bbp.uima.topicmodels.inferencer
import ch.epfl.bbp.uima.topicmodels.dca.DCAFiles

/**
 * Provides topic inference for DCA models.
 * Based on Mallet topic inferencer.
 */
class DCAInference(dcaDirectoryPath: String, dcaStem: String, averaged: Boolean = true, trainingCorpusSize: Int = 0) extends DocumentInference {
  private val corpusSize = if (trainingCorpusSize <= 0) DCAFiles.estimateTrainingCorpusSize(dcaDirectoryPath, dcaStem)
  else trainingCorpusSize

  private val wordProbs:Array[Array[Double]] = null // FIXMEren DCAFiles.getTermTopicProbabilityMatrix(dcaDirectoryPath, dcaStem, averaged)
  private val alpha = DCAFiles.getAlpha(dcaDirectoryPath, dcaStem)
  private val beta = DCAFiles.getThetaPrior(dcaDirectoryPath, dcaStem)
  private val dict = DCAFiles.getTokenDict(dcaDirectoryPath, dcaStem)

  private val minferencer = new MalletBasedInferencer(DCAFiles.getTermTopicCountsMatrix(dcaDirectoryPath, dcaStem, corpusSize, averaged, dict.size), alpha, beta: Double, dict)

  /**
   * Estimates the topic probability distribution for a document.
   */
  override def getTopicDistribution(doc: List[String], totalCycles: Int, burnin: Int): Array[Double] = {
    minferencer.inference(doc, totalCycles, burnin)
  }

  override def getTopicsDistributionForWord(w: String, topicDist: Array[Double]): Array[Double] = {
    try { // FIXME Caused by: java.lang.ArrayIndexOutOfBoundsException: 89099
      dict.get(w) match {
        case Some(id) =>
          (wordProbs(id) zip topicDist)
            .map(x => x._1 * x._2)
        case None => Array.fill(topicDist.size)(0.0)
      }
    } catch {
      case _ => Array.fill(topicDist.size)(0.0)
    }
  }
}