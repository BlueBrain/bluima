package ch.epfl.bbp.uima.topicmodels.writers
import java.io.File
import java.io.PrintStream
import scala.annotation.tailrec
import scala.collection.JavaConversions._
import scala.util.Random
import org.apache.uima.jcas.JCas
import org.apache.uima.UimaContext
import org.slf4j.LoggerFactory
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.descriptor.OperationalProperties
import org.apache.uima.fit.util.JCasUtil
import de.julielab.jules.types.pubmed.Header
import de.julielab.jules.types.Token
import scala.collection.mutable.SynchronizedMap
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils
import ch.epfl.bbp.uima.types.FeatureTokens
import ch.epfl.bbp.uima.BlueCasUtil
import org.apache.uima.resource.ResourceInitializationException
import org.apache.commons.io.FileUtils

/**
 * Converts features to various formats for LDA softwares
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.FeatureTokens), outputs = Array())
@OperationalProperties(multipleDeploymentAllowed = false) // There should be only one instance of this class, since it collects some data over all the CAS inputed (see writeStatistics)
class JCasWriterConsumer extends JCasAnnotator_ImplBase {
  private val log = AnnotatorUtils.getLogger

  // Map containing feature nr and counts for each feature occurring
  private val vocabulary = new scala.collection.mutable.HashMap[String, (Int, Int)]
  private var featureIndex = -1

  private var directory = ""
  private var training: PrintStream = null
  private var test: PrintStream = null
  private var trainingDocIdsOut: PrintStream = null
  private var testDocIdsOut: PrintStream = null
  private var cvTestStreams: List[PrintStream] = null
  private var cvTrainingStreams: List[PrintStream] = null

  // for statistics
  private var totalDocCnt = 0
  private var trainingDocCnt = 0
  private var testDocCnt = 0
  private var featuresTraining = 0
  private var featuresTest = 0

  private var writers: java.util.List[CasWriter] = null
  private var crossValidationFolds = 0

  private val rnd = new Random()
  private var foldNumbers: List[Int] = Nil // used for cross validation
  private var testSetSize = 0.10

  override def initialize(context: UimaContext) = {
    directory = AnnotatorUtils.getStringParamFromContext(context, JCasWriterConsumer.OutputDirectory, "", true)

    if (directory.endsWith(File.separator))
      directory.substring(0, directory.length() - File.separator.length())
    new File(directory).mkdirs

    testSetSize = AnnotatorUtils.getDoubleParamFromContext(context, JCasWriterConsumer.TestSetFraction, testSetSize, false)

    val seed = AnnotatorUtils.getLongParamFromContext(context, JCasWriterConsumer.Seed, System.currentTimeMillis, false)
    rnd.setSeed(seed)

    val cv = AnnotatorUtils.getIntParamFromContext(context, JCasWriterConsumer.CrossvalidationFolds, 0, false)
    cvTrainingStreams = {
      if (cv > 1)
        List.range(0, crossValidationFolds).map(l => prepareStream(directory + "/training" + l + ".txt"))
      else Nil
    }
    cvTestStreams = {
      if (cv > 1)
        List.range(0, crossValidationFolds).map(l => prepareStream(directory + "/test" + l + ".txt"))
      else Nil
    }

    // ren: hacked to use with pipeline script; DCA writer
    val outDir = directory //AnnotatorUtils.getStringParamFromContext(context, "outDir", "", true)
    val folds = cv // AnnotatorUtils.getIntParamFromContext(context, "folds", -1, true)
    val writeTestFolds = AnnotatorUtils.getBooleanParamFromContext(context, "writeTestFolds", false, false)
    writers = (new DCAWriter(outDir + "/dca/dca.txtbag", outDir + "/dca/dca.tokens", outDir + "/dca", folds, writeTestFolds)) :: Nil
    // TODO writers = WriterConfig.getList(AnnotatorUtils.getStringParamFromContext(context, JCasWriterConsumer.Writers, "", true))

    // cleartext files for control and also for mallet
    training = prepareStream(directory + "/training.txt")
    test = prepareStream(directory + "/test.txt")
    trainingDocIdsOut = prepareStream(directory + "/training_doc_ids.txt")
    testDocIdsOut = prepareStream(directory + "/test_doc_ids.txt")
  }

  override def process(doc: JCas) {
    totalDocCnt += 1

    val features = JCasUtil.selectSingle(doc, classOf[FeatureTokens]).getTokens.toArray
    features.foreach(addNewFeature(_)) // adding features to vocabulary

    val random = rnd.nextDouble()
    val isTest = random <= testSetSize

    if (!features.isEmpty) {
      // adjust statistics
      if (isTest) testDocCnt += 1 else trainingDocCnt += 1
      if (isTest) featuresTest += features.size else featuresTraining += features.size

      // write to normal text file (e.g. for mallet)
      val out = if (isTest) test else training
      features.foreach(t => out.print(t + " "))
      out.println()

      // write docID
      val idOut = if (isTest) testDocIdsOut else trainingDocIdsOut
      val docId = BlueCasUtil.getHeaderDocId(doc)
      idOut.println(if (docId != null) docId else totalDocCnt)

      // convert features to number
      val numFeatures = features.map(s => vocabulary.getOrElse(s, (-1, 0))._1)
      val numFeaturesSorted = numFeatures.toList.sortWith(_ < _)
      val numFeaturesCnt = countOccurrences(numFeaturesSorted)

      writers.foreach(w =>
        if (isTest) w.writeTestInstance(numFeaturesCnt)
        else w.writeTrainingInstance(numFeaturesCnt))

      if (crossValidationFolds > 1) {
        if (foldNumbers.isEmpty)
          foldNumbers = Random.shuffle(List.range(0, crossValidationFolds))

        val rfold = foldNumbers.head
        foldNumbers = foldNumbers.tail

        features.foreach(t => cvTestStreams(rfold).print(t + " "))
        cvTestStreams(rfold).println

        val otherStreams = cvTrainingStreams.filter(_ != cvTrainingStreams.apply(rfold))
        otherStreams.foreach(s => {
          features.foreach(t => s.print(t + " "))
          s.println()
        })

        writers.foreach(w => w.writeCrossvalidationInstance(numFeaturesCnt, rfold))
      }

    } else {
      //log.warn("Document number {} is empty", totalDocCnt) //many documents are empty in pubmed abstracts
    }

    if (totalDocCnt % 10000 == 0) {
      log.debug("Voc size after {} documents {}", totalDocCnt, vocabulary.size)
      val runtime = Runtime.getRuntime()
      log.debug("total mem " + runtime.totalMemory / (1024 * 1024) + "M free memory : " + runtime.freeMemory / (1024 * 1024) + " M Max mem " + runtime.maxMemory / (1024 * 1024) + "M")
    }
  }

  override def collectionProcessComplete {
    super.collectionProcessComplete()

    log.info("Processing completed! Write final files")

    // writes vocabulary to file in a sorted list
    val out = prepareStream(directory + "/vocabulary.txt")
    val sortedVoc = vocabulary.toList.sortWith((a, b) => a._1 < b._1)
    sortedVoc.foreach(e => out.println(e._1 + " " + e._2._1 + " " + e._2._2))
    out.close()

    // do some statistics
    writeStatistics(directory)

    // also wrapping up things for writers
    val vocImmut = collection.immutable.Map(vocabulary.toList: _*)
    writers.foreach(w => w.complete(vocImmut))

    // closing files
    training.close()
    test.close()
    trainingDocIdsOut.close()
    testDocIdsOut.close
  }

  private def addNewFeature(s: String) {
    if (s != "") {
      val entry = vocabulary.getOrElse(s, (-1, 0))

      val feature = {
        if (entry._1 >= 0) entry._1
        else { featureIndex += 1; featureIndex } // unseen feature.
      }

      vocabulary.update(s, (feature, entry._2 + 1))
    }
  }

  /**
   * Creates from a sequence of features a bag of words representation.
   */
  private def countOccurrences(list: List[Int]): List[(Int, Int)] = {

    @tailrec
    def countOccurrences0(lst: List[Int], acc: List[(Int, Int)]): List[(Int, Int)] = lst match {
      case Nil => acc
      case head :: tail => acc match {
        case Nil => countOccurrences0(tail, (head, 1) :: Nil)
        case hAcc :: tAcc if (head == hAcc._1) =>
          countOccurrences0(tail, (hAcc._1, hAcc._2 + 1) :: tAcc)
        case hAcc :: tAcc if (head != hAcc._1) =>
          countOccurrences0(tail, (head, 1) :: hAcc :: tAcc)
      }
    }

    countOccurrences0(list, Nil).reverse
  }

  private def writeStatistics(path: String) {
    val out = prepareStream(path + "/statistics.txt")

    out.println("Processed documents\t" + totalDocCnt)
    out.println("Empty documents\t" + (totalDocCnt - (trainingDocCnt + testDocCnt)))
    out.println("Training set\t" + trainingDocCnt)
    out.println("Test set \t" + testDocCnt)
    out.println("Features in training set\t" + featuresTraining)
    out.println("Features in test set\t" + featuresTest)
    out.println("Average features per documents in training set\t" + (featuresTraining / trainingDocCnt.toDouble))
    out.println("Average features per documents in test set\t" + (featuresTest / testDocCnt.toDouble))

    val sortedVoc = vocabulary.toList.sortWith((a, b) => a._2._2 > b._2._2)

    // print most common features
    val featureNumber = 100
    val features = sortedVoc.take(featureNumber)
    features.foreach(t => out.println(t._1 + "\t" + t._2._2))
    out.close()

    val featureDist = prepareStream(path + "/featureCountDistribution.txt")
    countOccurrences(sortedVoc.map(t => t._2._2)).foreach(p => featureDist.println(p._1 + "\t" + p._2))
    featureDist.close()
  }

  private def prepareStream(path: String) = {
    val f = new File(path)
    if (!f.exists) {
      f.getParentFile.mkdirs
      f.createNewFile
    }

    if (!f.exists() || !f.canRead())
      new RuntimeException("Cannot open required file " + f.getAbsolutePath())

    log.info("Prepared file for writing at " + f.getAbsolutePath)
    new PrintStream(f)
  }
}

object JCasWriterConsumer {
  val OutputDirectory = "outputDirectory" // (String) output directory of auxiliary files generated
  val TestSetFraction = "testSetFraction" // (Double) Optional. Size of the test set (percentage)
  val Writers = "writers" // (String) key to get CasWriter instances from WriterConfig singleton
  val Seed = "seed" // (Integer) Optional. Seed for random generator. If none is given, time is taken
  val CrossvalidationFolds = "crossValidationFolds" // (Integer) number of CV folds. Default is 1. 
}