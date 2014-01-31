package ch.epfl.bbp.uima.topicmodels.writers
import org.apache.uima.fit.util.JCasUtil
import org.apache.uima.jcas.JCas
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import de.julielab.jules.types.Token
import de.julielab.jules.types.Header
import scala.collection.JavaConversions._
import org.apache.uima.UimaContext
import java.io.File
import java.io.PrintStream
import org.apache.uima.fit.descriptor.OperationalProperties
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils

/**
 * Counts the total number of occurrences of tokens.
 *
 * The counts are maintained in a map. If this map grows large, it is written to a file and
 * a new map is created.
 *
 * The part files have the following format: For each token there is a line, containing the token and its
 * observed count separated by a tab. The lines are sorted in descending order of the tokens.
 * This allows an efficient merging of the files into one.
 *
 * Parameters: * the output file path (the part number is appended to the filename)
 * 			   * [optional] the maximum number of elements in the map. Default: 1.5 Mio
 *
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.Token), outputs = Array())
@OperationalProperties(multipleDeploymentAllowed = false)
class TokenFrequencyCounterWriter extends JCasAnnotator_ImplBase {
  private val log = AnnotatorUtils.getLogger
  private var file: File = null
  private var maxMapSize = 1500000

  private var tokens = new scala.collection.mutable.HashMap[String, Int]
  private var partCnt = 0
  private var cnt = 0

  override def initialize(context: UimaContext) = {
    val path = AnnotatorUtils.getStringParamFromContext(context, TokenFrequencyCounterWriter.FilePath, "", true)
    maxMapSize = AnnotatorUtils.getIntParamFromContext(context, TokenFrequencyCounterWriter.MaxMapSize, maxMapSize, false)

    file = new File(path)
    file.createNewFile()
    if (!file.canWrite())
      throw new RuntimeException("Problem creating file to store token frequency at " + path)

    log.info("Writing token counts to {} ", file.getAbsolutePath())
  }

  override def process(doc: JCas) {
    val casTokens = JCasUtil.select(doc, classOf[Token])
    val tokensStr = casTokens.map(t =>
      if (t.getLemmaStr() != null) t.getLemmaStr()
      else t.getCoveredText())

    tokensStr.foreach{t => tokens.update(t, tokens.getOrElse(t, 0) + 1)}

    cnt += 1
    if (cnt % 10000 == 0) {
      val runtime = Runtime.getRuntime()
      log.info("Processed " + cnt + " documents.  Hashmap size " + tokens.size + ".")
      log.info("Total mememory " + runtime.totalMemory / (1024 * 1024) + "M free memory : " + runtime.freeMemory / (1024 * 1024) + " M Max mem " + runtime.maxMemory / (1024 * 1024) + "M")
    }

    if (tokens.size > maxMapSize) {
      writeToFile()
      tokens = new scala.collection.mutable.HashMap[String, Int]
    }
  }

  override def collectionProcessComplete {
    super.collectionProcessComplete()
    log.info("Processing completed. Processed " + cnt + " documents")
    writeToFile
  }

  private def writeToFile() {
    val lst = tokens.toList.sortWith((a, b) => a._1 > b._1)
    val out = new PrintStream(new File(file.getAbsolutePath + partCnt))
    lst.foreach(l => out.println( l._1 + "\t" + l._2))
    out.close()

    partCnt += 1
  }
}

object TokenFrequencyCounterWriter {
  val FilePath = "filepath"
  val MaxMapSize = "maxMapSize"
}