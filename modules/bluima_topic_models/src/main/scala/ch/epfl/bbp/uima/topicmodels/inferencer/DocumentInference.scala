package ch.epfl.bbp.uima.topicmodels.inferencer

trait DocumentInference {
  def getTopicDistribution(doc : List[String], totalCycles : Int, burnin : Int) : Array[Double]
  def getTopicsDistributionForWord(w : String, topicDist : Array[Double]) : Array[Double]
}