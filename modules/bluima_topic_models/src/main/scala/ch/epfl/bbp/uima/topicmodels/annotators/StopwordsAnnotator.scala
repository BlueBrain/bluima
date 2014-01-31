package ch.epfl.bbp.uima.topicmodels.annotators
import scala.collection.JavaConversions._

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.UimaContext
import org.apache.uima.fit.util.JCasUtil

import ch.epfl.bbp.uima.types.Stopword
import de.julielab.jules.types.Token

/**
 * FIXME remove
 * Marks tokens as stopword, if they appear in the stopwords list.
 */
class StopwordsAnnotator extends JCasAnnotator_ImplBase {
  private val log = AnnotatorUtils.getLogger
  private var caseSensitive = false

  private var stopwords: Set[String] = null

  override def initialize(context: UimaContext) {
    super.initialize(context)

    val stopwordsFilePath = AnnotatorUtils.getStringParamFromContext(context, StopwordsAnnotator.StopwordsFilePath, null, true)
    caseSensitive = AnnotatorUtils.getBooleanParamFromContext(context, StopwordsAnnotator.CaseSensitive, caseSensitive, true)

    stopwords = StopwordsAnnotator.readStopwordsList(stopwordsFilePath, caseSensitive)
    log.info("Read stopwords file from {}. Read {} stopwords. Case sensitive: " + caseSensitive, stopwordsFilePath, stopwords.size.toString)
  }

  override def process(doc: JCas) {
    val tokens = JCasUtil.select(doc, classOf[Token])

    for (t <- tokens) {
      val token =  t.getCoveredText
      val tokenCmp = if (!caseSensitive) token.toLowerCase() else token

      if (stopwords.contains(tokenCmp)) {
        val annot = new Stopword(doc, t.getBegin, t.getEnd())
        annot.addToIndexes
      }
    }
  }

}

object StopwordsAnnotator {
  val StopwordsFilePath = "stopwordsFilePath" // (String) path to the stopword list
  val CaseSensitive = "caseSensitive" // (Boolean) (default false) If true, tokens are not normalized to lowercase before string comparisions.

  def readStopwordsList(stopwordsFilePath: String, caseSensitive: Boolean) = {
    val words = AnnotatorUtils.prepareFile(stopwordsFilePath)
    val stopwords = scala.collection.mutable.Set.empty[String]

    words.getLines()
      .map(s => if (!caseSensitive) s.toLowerCase() else s)
      .foreach(stopwords.add(_))

    stopwords.toSet
  }
}