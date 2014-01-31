package ch.epfl.bbp.uima.topicmodels.mallet
import java.io.File

import java.io.PrintStream
import java.io.PrintWriter
import cc.mallet.pipe.TokenSequence2FeatureSequence
import cc.mallet.topics.ParallelTopicModel
import cc.mallet.types.Instance
import cc.mallet.types.InstanceList
import cc.mallet.types.IDSorter

/**
 * Sketch of a wrapper for Mallet.
 * 
 * Used in some early phase of the project. Quite incomplete and untested
 */
class MalletTopicModelWrapper(initNrTopics: Int) {

  private val alpha = 1.0
  private val beta = 0.01
  private var nrTopics = initNrTopics
  private var model = new ParallelTopicModel(nrTopics, alpha, beta)
  private val trainingInstances = new InstanceList(new TokenSequence2FeatureSequence())
  private val validationInstances = new InstanceList(new TokenSequence2FeatureSequence())

  private var wordProbPerTopic: List[Map[String, Double]] = null

  def this(m: ParallelTopicModel) {
    this(20) // TODO take right parameter
    model = m
    wordProbPerTopic = initWordProb
  }

  def addDoc(instance: Instance) {
    trainingInstances.addThruPipe(instance)
  }

  def addValidationDoc(instance: Instance) {
    validationInstances.addThruPipe(instance)
  }

  def setNrOfTopics(topics: Int) = {
    nrTopics = topics
    model = new ParallelTopicModel(topics, alpha, beta)
  }

  def train(iterations: Int) {
    model.setNumThreads(6)
    model.setNumIterations(iterations)
    model.addInstances(trainingInstances)
    model.estimate()
  }

  def printSomeStats(file: File) {
    val out = new PrintStream(file)

    out.println("model log likelihood: " + model.modelLogLikelihood())
    model.printTopWords(out, 15, false)
    out.close()
  }

  def calculatePerplexityOnValidationCorpus(f: File) = {
    val estimator = model.getProbEstimator()

    System.out.println("estimating likelihoods")
    val out = new PrintStream(f)
    val totalLogLikelihood = estimator.evaluateLeftToRight(trainingInstances, 20, true, out)
    System.out.println("estimating likelihoods end")

    val perplexity = Math.pow(2.0, -(totalLogLikelihood / trainingInstances.size()))
    out.println("total log likelihood\t" + totalLogLikelihood)
    out.println("perplexity\t" + perplexity)
    out.close()

    perplexity
  }

  def getWeight(word: String) = {
    val x = model.getProbEstimator()
  }

  def topicsDistribution(instance: Instance) = {
    val inferencer = model.getInferencer()
    val il = new InstanceList(trainingInstances.getPipe())

    il.addThruPipe(instance)

    val x = inferencer.getSampledDistribution(il.get(0), 10, 1, 5)

    val a = model.getAlphabet()
    val w = model.getSortedWords()

    x
  }

  private def initWordProb: List[Map[String, Double]] = {
    val words = model.getSortedWords()
    val alpha = model.getAlphabet()

    def createMap(t: Int): Map[String, Double] = {
      val it = words.get(t).iterator()
      var map = Map.empty[String, Double]
      while (it.hasNext()) {
        val w = it.next()
        map = map + (alpha.lookupObject(w.getID()).toString() -> w.getWeight())
      }

      map
    }

    List.range(1, model.getNumTopics()).map(t => createMap(t))
  }

  def probInTopics(lemma: String): List[Double] = {
    wordProbPerTopic.map(m => m.getOrElse(lemma, 0.0))
  }

  def writeModelToFile(f: File) {
    model.write(f)
  }
}

object MalletTopicModelWrapper {
  def readModelFromFile(f: File) = {
    new MalletTopicModelWrapper(ParallelTopicModel.read(f))
  }
}
