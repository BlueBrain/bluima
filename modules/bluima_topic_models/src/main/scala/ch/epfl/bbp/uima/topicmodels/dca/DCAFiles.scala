package ch.epfl.bbp.uima.topicmodels.dca
import java.io.File
import java.util.regex.PatternSyntaxException

import scala.Array.canBuildFrom
import scala.collection.immutable.HashMap
import scala.io.Source

import org.slf4j.LoggerFactory

import ch.epfl.bbp.uima.topicmodels.mallet.MalletUtils

/**
 * A collection of functions to read files in a DCA directory. A working DCA model requires the following files:<br>
 * .par .meantheta .theta .topics .alpha .tokens<br>. The others can be removed/ignored
 *
 * @author Marc.Zimmermann@epfl.ch
 */
object DCAFiles {
  private val log = LoggerFactory.getLogger("DCAFiles")

  val AvgMatrixEnding = ".meantheta"
  val MatrixEnding = ".theta"
  val DEFAULT_THETAPRIOR = 0.1 // corresponds to beta

  val topicCommand = "mpupd -T -t 1,1 [STEM] | cut -d\"(\" -f2 | cut -d\")\" -f1 > [STEM].topics"

  def getAlpha(dirPath: String, stem: String) = {
    val src = prepareFile(dirPath, stem, ".alpha")

    val it = src.getLines().dropWhile(s => !s.startsWith("alpha="))
    // alpha is possibly split over several lines
    val firstAlphaLine = it.next.drop("alpha=".length) // remove "alpha="
      .split(',').map(_.toDouble)
    val rest = it.map(s => s.dropWhile(_ == ' ').split(',').drop(1).map(_.toDouble)).toArray
    val f = rest.flatten
    (firstAlphaLine ++ f)
  }

  def getThetaPrior(dirPath: String, stem: String) = {
    val src = prepareFile(dirPath, stem, ".par")
    val thetapriorLine = src.getLines().find(s => s.startsWith("thetaprior"))
    val beta = thetapriorLine.map(s => s.split('=').apply(1).trim.toDouble)

    beta.getOrElse(DCAFiles.DEFAULT_THETAPRIOR)
  }

  /**
   * Gets the probability matrix of p(w|z). The rows correspond to terms, the columns to topics.
   *
   * If averaged is true, read matrix generated with the -A option ([stem].meantheta]). If it doesnt exist, fall back to [stem].theta
   */
  def getTermTopicProbabilityMatrix(dirPath: String, stem: String, averaged: Boolean, vocabSize: Int): Array[Array[Float]] = {
    val ending = getAppropriateEnding(dirPath, stem, averaged)

    val src = prepareFile(dirPath, stem, ending)

    //OutOfMemory src.getLines().map(l => l.split(' ').map(_.toDouble)).toArray
    val ret = new Array[Array[Float]](vocabSize)
    src.getLines.zipWithIndex.foreach {
      case (line, i) =>
        ret(i) = line.split(' ').map(_.toFloat)
    }
    ret
    
  }

  /**
   * Gets the co-occurence matrix of p(w, z) scaled such that the sum of all entries correspond to the training corpus size.
   * The rows correspond to terms, the columns to topics.
   *
   * If averaged is true, read matrix generated with the -A option ([stem].meantheta]). If it doesn't exist, fall back to [stem].theta
   *
   * This function needs p(z) for the corpus. To get this, the command in the DCAFiles.topicCommand variable has to be executed
   */
  def getTermTopicCountsMatrix(dirPath: String, stem: String, trainingCorpusSize: Int, averaged: Boolean, vocabSize:Int): Array[Array[Int]] = {
    val thetaArr = getTermTopicProbabilityMatrix(dirPath, stem, averaged, vocabSize)
    theta2Counts(thetaArr, getTopicCounts(dirPath, stem, getAppropriateEnding(dirPath, stem, averaged)), trainingCorpusSize)
  }

  def getTokenDict(dirPath: String, stem: String): Map[String, Int] = {
    val src = prepareFile(dirPath, stem, ".tokens")
    HashMap.empty[String, Int] ++ src.getLines().toList.zipWithIndex //.map(x => (x._1, x._2+1))
  }

  def estimateTrainingCorpusSize(dirPath: String, stem: String): Int = {

    // if used didn't specify corpus size, then estimate it from the .par file
    val par = prepareFile(dirPath, stem, ".par").getLines().toList

    val documents = par.find(s => s.startsWith("documents"))
    val testDocs = par.find(s => s.startsWith("testdocs"))
    val mean_features = par.find(s => s.startsWith("mean_featurecount"))

    if (!documents.isDefined || !mean_features.isDefined)
      new RuntimeException("Could not find 'documents' or 'mean_featurecount' in " + stem + ".par")

    val trainingDocs = documents.get.drop("documents=".length).toInt - testDocs.map(s => s.drop("testdocs=".length).toInt).getOrElse(0)
    (trainingDocs * mean_features.get.drop("mean_featurecount=".length).toDouble).toInt

  }

  /**
   * Gets the p(z) of the training corpus. This is read from the "STEM.topics" file, which has to be generated beforehand
   * by the command in DCAFiles.topicCommand
   */
  private def getTopicCounts(dirPath: String, stem: String, matrixEnding: String): Array[Double] = {
    //mpupd -T -t 1,1 dca | sed 's/Component [0-9]* (\([0-9.]*\)):/\1/g' > dca.topics// generated with mpupd -m 0 dca > dca.topics)
    val src = prepareFile(dirPath, stem, ".topics")

    // do sanity check on file chaning date: should be newer than other files (people forget things...)
    val matrixFile = new File(dirPath + "/" + stem + matrixEnding)
    val topicFile = new File(dirPath + "/" + stem + ".topics")
    if (matrixFile.lastModified() > topicFile.lastModified()) {
      log.warn("Warning: Topic file is older than matrix file. Please execute the following command in the DCA directory: ")
      log.warn(topicCommand.replace("[STEM]", stem))
    }

    src.getLines().map(l => l.toDouble).toArray
  }

  private def prepareFile(dirPath: String, stem: String, ending: String): Source = {
    log.info(dirPath + "/" + stem + ending)
    Source.fromFile(new File(dirPath + "/" + stem + ending))
  }

  private def getAppropriateEnding(dirPath: String, stem: String, averaged: Boolean) = {
    if (averaged) {
      val path = dirPath + "/" + stem + AvgMatrixEnding
      if (new File(path).exists) {
        AvgMatrixEnding
      } else {
        log.warn("File {} doesn't exists. Falling back to {}", path, stem + MatrixEnding)
        MatrixEnding
      }
    } else
      MatrixEnding
  }

  /**
   * Converts p(w,z) matrix to a counts matrix where all the entries sum up to corpusSize
   */
  private def theta2Counts(theta: Array[Array[Float]], topic_dist: Array[Double], corpusSize: Int): Array[Array[Int]] = {

    // inplace multiplication
    for (l <- 0 to theta.length - 1) {
      for (t <- 0 to topic_dist.length - 1) {
        theta(l)(t) *= topic_dist(t).toFloat
      }
    }
    val coocurrenceMatrix = theta //.map(l => l.zip(topic_dist).map(p => (p._1 * p._2)))
    MalletUtils.convertProbabilityMatrixToCountsMatrix(coocurrenceMatrix, corpusSize)
  }
}