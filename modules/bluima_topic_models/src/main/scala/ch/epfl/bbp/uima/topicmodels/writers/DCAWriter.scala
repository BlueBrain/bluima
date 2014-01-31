package ch.epfl.bbp.uima.topicmodels.writers
import java.io.BufferedWriter
import java.io.File
import java.io.PrintStream
import java.io.FileWriter
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileOutputStream
import java.io.FileInputStream

/**
 * Writes files in DCA format.
 * 
 * @param filePath Path of DCA directory
 * @param tokenFile Path to file where token file (index to string) should be stored
 * @param tmpFilesDir Path to directory where potentially large temporary files can be kept
 * @param cvFolds Number of folds in cross-validation. If it is smaller or equal to 1, no CV is performed.
 * @param writeCvTestFold If true (default) writes CV test folds in DCA format (and appends it to training file)
 */
class DCAWriter(filePath: String, tokenFile: String, tmpFilesDir: String, cvFolds: Int = 0, writeCvTestFold: Boolean = true)
  extends CasWriter(tmpFilesDir + DCAWriter.TrainFile, tmpFilesDir + DCAWriter.TestFile, cvFolds, writeCvTestFold) {

  override protected def writeInstance(features: List[(Int, Int)], stream: PrintStream) = {
    // writing in bagged format
    stream.print(features.size + " " + features.head._1 + " " + features.head._2)
    features.tail.foreach(p => stream.print(" " + p._1 + " " + p._2))
    stream.println()
  }

  override def complete(vocabulary: Map[String, (Int, Int)]) {
    val vocSize = vocabulary.size

    mergeFile(tmpFilesDir + DCAWriter.TrainFile, tmpFilesDir + DCAWriter.TestFile, filePath, vocSize, instancesCount - testInstances, false)

    if (cvFolds > 1) {
      List.range(0, cvFolds)
        .foreach(l => mergeFile(
          tmpFilesDir + DCAWriter.TrainFile + l,
          tmpFilesDir + DCAWriter.TestFile + l,
          filePath + l,
          vocSize,
          instancesCount - cvTestSizes(l),
          writeCvTestFold))
    }

    // writes vocabulary to dca token file
    val dcaOut = prepareFile(tokenFile)
    vocabulary.toList.sortWith((a, b) => a._2._1 < b._2._1).foreach(e => dcaOut.println(e._1))
    dcaOut.close()
  }

  /**
   * Prepends header (number of documents and distinct tokens in corpus) to training files.
   * If includeTest is true, the test set gets appended to the DCA files as well.
   */
  private def mergeFile(trainingPath: String, testPath: String, outputPath: String, vocSize: Int, instances: Int, includeTest: Boolean) {
    val outStream = new PrintStream(new File(outputPath))
    outStream.println(instances)
    outStream.println(vocSize)
    outStream.close()

    // merges the two files to one
    // WARNING: For very large files (>2G) this mechanism doesn't work, due to problems with channel size. 
    // Did the merging manually on command line for these cases.
    val trainingFile = new File(trainingPath)
    val testFile = new File(testPath)
    val out = new FileOutputStream(outputPath, true).getChannel()
    val train = new FileInputStream(trainingFile).getChannel()
    val test = new FileInputStream(testFile).getChannel()

    out.transferFrom(train, 0, train.size())

    if (includeTest)
      out.transferFrom(test, 0, test.size())

    // cleaning up
    out.close()
    train.close()
    test.close()
    trainingFile.delete()
    testFile.delete()
  }
}

object DCAWriter {
  private val TrainFile = "/dcaTrain.txt"
  private val TestFile = "/dcaTest.txt"
}