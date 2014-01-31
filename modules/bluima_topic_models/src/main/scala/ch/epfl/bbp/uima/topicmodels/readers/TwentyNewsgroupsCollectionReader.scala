package ch.epfl.bbp.uima.topicmodels.readers

import org.apache.uima.fit.component.JCasCollectionReader_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.util.Progress
import org.apache.uima.UimaContext
import de.julielab.jules.types.pubmed.Header
import java.io.File
import org.apache.uima.fit.descriptor.OperationalProperties
import ch.epfl.bbp.uima.utils.Preconditions

/**
 * Collection reader for the 20 Newsgroups corpus.
 *
 * It is assumed that the documents of each newsgroups are stored seperately in a folder for each newsgroups
 * and that the filenames represent the document id.
 */
@OperationalProperties(multipleDeploymentAllowed = false)
class TwentyNewsgroupsCollectionReader() extends JCasCollectionReader_ImplBase {
  private var groups: Array[File] = null
  private var maxFilesToRead = Int.MaxValue

  private var currentFileList: List[File] = Nil
  private var currentGroup = 0

  private var docCnt = 0

  /** called once, when the Reader is initiated */
  override def initialize(context: UimaContext) = {
    groups = getGroups(context.getConfigParameterValue(TwentyNewsgroupsCollectionReader.CorpusDirPath).toString())
    currentFileList = groups.apply(0).listFiles.toList

    val maxFiles = context.getConfigParameterValue(TwentyNewsgroupsCollectionReader.MaxDocsToRead)
    if (maxFiles != null)
      maxFilesToRead = maxFiles.toString().toInt
  }

  override def hasNext(): Boolean = !currentFileList.isEmpty && docCnt < maxFilesToRead

  override def getNext(jCas: JCas) {
    // open next file
    val docLines = scala.io.Source.fromFile(currentFileList.head, "ISO-8859-1").getLines()

    // extract body
    val headerR = """^[\w-]+: .*""".r
    val subjectR = """^Subject: (.*)$""".r
    val docText = docLines.zipWithIndex.map {
      case (x, i) =>
        x match {
          // keep the subject
          case subjectR(s) => s
          // remove header if within first 25 lines
          case headerR() if i < 25 => ""
          // the rest is considered the body of text
          case _ => x
        }
    }.mkString(" ")

    // create jcas
    jCas.setDocumentText(docText.toLowerCase())
    val header = new Header(jCas)
    header.setDocId(currentFileList.head.getName)
    header.addToIndexes

    docCnt += 1

    // prepare next file
    currentFileList = currentFileList match {
      case head :: Nil => {
        // read next group/directory
        currentGroup += 1
        if (currentGroup < groups.length) {
          println("reading group #" + currentGroup)
          groups.apply(currentGroup).listFiles.toList
        } else
          Nil // all directories have been read, we're done
      }
      case head :: tail => tail
      case Nil => Nil
    }
  }

  override def getProgress(): Array[Progress] = null // nope

  private def getGroups(path: String) = {
    val rootDir = new File(path)
    if (!rootDir.exists())
      throw new IllegalArgumentException("no dir at " + rootDir);
    rootDir.listFiles.filter(_.isDirectory)
  }

  def getCurrentGroup = currentGroup
}

object TwentyNewsgroupsCollectionReader {
  val CorpusDirPath = "corpusDirPath" // (String) Path to root directory of path.
  val MaxDocsToRead = "maxDocsToRead" // (Integer) reads the first N documents from the corpus. For testing purposes
}