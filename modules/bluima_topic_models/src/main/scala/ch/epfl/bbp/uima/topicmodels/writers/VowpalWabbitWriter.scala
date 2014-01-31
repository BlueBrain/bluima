package ch.epfl.bbp.uima.topicmodels.writers
import java.io.PrintStream

/**
 * Writes document features for Vowpal Wabbit
 */
class VowpalWabbitWriter(trainingFilePath: String, testFilePath: String, cvFolds: Int = 0, writeCvTestFold: Boolean = true)
  extends CasWriter(trainingFilePath: String, testFilePath: String, cvFolds, writeCvTestFold) {

  override protected def writeInstance(features: List[(Int, Int)], stream: PrintStream) = {
    stream.print("| " + " " + features.head._1 + ":" + features.head._2)
    features.tail.foreach(p => stream.print(" " + p._1 + ":" + p._2))
    stream.println()
  }
}