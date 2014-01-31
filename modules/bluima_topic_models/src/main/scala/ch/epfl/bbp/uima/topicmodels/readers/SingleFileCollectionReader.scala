package ch.epfl.bbp.uima.topicmodels.readers
import java.io.PrintStream
import org.apache.uima.jcas.JCas
import org.apache.uima.UimaContext
import org.apache.uima.fit.component.JCasCollectionReader_ImplBase
import java.io.File
import java.io.InputStreamReader
import scala.io.BufferedSource
import de.julielab.jules.types.pubmed.Header
import org.apache.uima.util.Progress

/**
 * UIMA collection reader to read documents stored in a file, one document per line.
 */
class SingleFileCollectionReader extends JCasCollectionReader_ImplBase {

  private var docCnt = 0
  private var maxFilesToRead = Int.MaxValue
  private var proportionToRead = 1.0
  private var src: Iterator[String] = null
  private val rnd = new scala.util.Random(42)
  
  /** called once, when the Reader is initiated */
  override def initialize(context: UimaContext) = {
    val filepath = context.getConfigParameterValue(SingleFileCollectionReader.FilePath).toString()
    src = scala.io.Source.fromFile(new File(filepath)).getLines()

    val maxFiles = context.getConfigParameterValue(SingleFileCollectionReader.MaxDocsToRead)
    if (maxFiles != null)
      maxFilesToRead = maxFiles.toString().toInt

    val prop = context.getConfigParameterValue(SingleFileCollectionReader.ProportionToRead)
    if (prop != null)
      proportionToRead = prop.toString().toDouble
  }

  override def hasNext(): Boolean = src.hasNext && docCnt < maxFilesToRead

  override def getNext(jCas: JCas) {
    var docText = src.next()

    while ((1 - proportionToRead) > rnd.nextDouble && src.hasNext) // skip a few, if requested
      docText = src.next()

    // create jcas
    jCas.setDocumentText(docText)

    val header = new Header(jCas)
    header.setDocId(docCnt.toString())
    header.addToIndexes

    docCnt += 1

    if (docCnt % 10000 == 0)
      println("Read " + docCnt + " documents")
  }

  override def getProgress(): Array[Progress] = null // not important here
}

object SingleFileCollectionReader {
  val FilePath = "filePath" // (String) Path of input file
  val MaxDocsToRead = "maxDocsToRead" // (Integer) reads the first N documents from a file.
  val ProportionToRead = "proportion" // (Double) alternative: reads all the files but takes file only with a certain probability
}