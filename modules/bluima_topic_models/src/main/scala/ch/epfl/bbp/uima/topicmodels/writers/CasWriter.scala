package ch.epfl.bbp.uima.topicmodels.writers
import java.io.File
import java.io.PrintStream
import scala.collection.mutable.ArrayBuffer
import org.slf4j.LoggerFactory
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils

/**
 * Abstract class for writing document instances in an appropriate format.
 *
 *  @param trainingFilePath Path of file to write training instances
 *  @param testFilePath Path of file to write test instances
 *  @param cvFolds Number of cross-validation folds (1 if no cross-validation)
 *  @param writeCvTestFold Boolean whether test files during cross-validation should be written in the respective format.
 */
abstract class CasWriter(trainingFilePath: String, testFilePath: String, cvFolds: Int, writeCvTestFold: Boolean) {
  protected val log = AnnotatorUtils.getLogger

  protected val trainOut = prepareFile(trainingFilePath)
  protected val testOut = prepareFile(testFilePath)

  protected val cvTrainStreams = if (cvFolds > 1) List.range(0, cvFolds).map(l => prepareFile(trainingFilePath + l))
  else Nil

  protected val cvTestStreams = if (cvFolds > 1) List.range(0, cvFolds).map(l => prepareFile(testFilePath + l))
  else Nil

  protected val cvTestSizes = Array.fill[Int](cvFolds)(0)
  protected var instancesCount = 0
  protected var testInstances = 0

  protected def prepareFile(path: String): PrintStream = {
    val f = new File(path)
    log.info("Opening file for writing " + f.getAbsolutePath())
    f.getParentFile().mkdirs()

    new PrintStream(f)
  }

  def writeCrossvalidationInstance(features: List[(Int, Int)], testFold: Int) {
    if (writeCvTestFold)
      writeInstance(features, cvTestStreams.apply(testFold))

    cvTestSizes.update(testFold, cvTestSizes(testFold) + 1)

    cvTrainStreams.filter(_ != cvTrainStreams.apply(testFold))
      .foreach(s => writeInstance(features, s))
  }

  def writeTrainingInstance(features: List[(Int, Int)]): Unit = {
    instancesCount += 1
    writeInstance(features, trainOut)
  }
  def writeTestInstance(features: List[(Int, Int)]): Unit = {
    instancesCount += 1
    testInstances += 1
    writeInstance(features, testOut)
  }

  // abstract methods
  /**
   * Writes some document instance to a stream.
   *
   *  @param features List of pairs the first denoting the feature number, the second the number of times it occurs in the document
   *  @param stream the output stream
   */
  protected def writeInstance(features: List[(Int, Int)], stream: PrintStream): Unit

  /**
   * Called a the end of the processing of all files in order to write additional files.
   */
  def complete(vocabulary: Map[String, (Int, Int)]) {

  }
}