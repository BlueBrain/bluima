package ch.epfl.bbp.uima.topicmodels.annotators
import scala.collection.JavaConversions._
import scala.collection.immutable.HashSet
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.UimaContext
import org.apache.uima.fit.util.JCasUtil
import ch.epfl.bbp.uima.types.FrequentToken
import ch.epfl.bbp.uima.types.Hapax
import de.julielab.jules.types.Token

/**
 * Marks tokens as frequent token or hapax.
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.Token), outputs = Array(ch.epfl.bbp.uima.types.FrequentToken, ch.epfl.bbp.uima.types.Hapax))
class TokenFrequencyFilterAnnotator extends JCasAnnotator_ImplBase {
  private val log = AnnotatorUtils.getLogger
  private var caseSensitive = false

  private var normal: Set[String] = null
  private var frequent: Set[String] = null

  override def initialize(context: UimaContext) {
    val path = AnnotatorUtils.getStringParamFromContext(context, TokenFrequencyFilterAnnotator.TokenFrequencyFile, "", true)
    val min = AnnotatorUtils.getIntParamFromContext(context, TokenFrequencyFilterAnnotator.MinimumFrequency, 0, true)
    val max = AnnotatorUtils.getIntParamFromContext(context, TokenFrequencyFilterAnnotator.MaximumFrequency, Int.MaxValue, true)

    caseSensitive = AnnotatorUtils.getBooleanParamFromContext(context, TokenFrequencyFilterAnnotator.CaseSensitive, caseSensitive, false)

    if (min >= max) {
      log.warn("Parameters MinimumFrequency and MaximumFrequency in TokenFrequencyFilterAnnotator not consistent")
    }

    val minLines = AnnotatorUtils.prepareFile(path).getLines.map(l => TokenFrequencyFilterAnnotator.extract(l)).filter(a => a(1).toInt >= min).toArray

    normal = HashSet() ++ minLines.filter(a => a(1).toInt <= max).map(a => a(0))
    frequent = HashSet() ++ minLines.filter(a => a(1).toInt > max).map(a => a(0))

    log.info("Vocabulary size " + normal.size + " min freq/max freq " + min + "/" + max)
  }

  override def process(doc: JCas) {
    val tokens = JCasUtil.select(doc, classOf[Token])

    for (t <- tokens) {

      val token = t.getCoveredText
      val tokenCmp = if (!caseSensitive) token.toLowerCase() else token

      if (!normal.contains(tokenCmp)) {
        if (frequent.contains(tokenCmp)) {
          (new FrequentToken(doc, t.getBegin, t.getEnd)).addToIndexes
        } else {
          (new Hapax(doc, t.getBegin, t.getEnd)).addToIndexes
        }
      }

    }
  }
}

object TokenFrequencyFilterAnnotator {
  val TokenFrequencyFile = "tokenFrequencyFile" // (String) Path to file containing tokens and their frequency in the corpus 
  val MinimumFrequency = "minimumFrequency" // (Integer) minimum frequency of token to be retained
  val MaximumFrequency = "maximumFrequency" // (Integer) maximum frequency of token to be retained
  val CaseSensitive = "caseSensitive" // (Boolean) (default false) If true, tokens are not normalized to lowercase before string comparisions.

  /** Splits at tab */
  def extract(s: String): Array[String] = {
    val split = s.split("\t")
    val l = split(0) //FIXME provide POS
    // if surrounded by ":   Array(split(0).dropRight(1).drop(1), split(1))
    //    Array(split(0), split(1))
    Array(l, split(1))
  }
}