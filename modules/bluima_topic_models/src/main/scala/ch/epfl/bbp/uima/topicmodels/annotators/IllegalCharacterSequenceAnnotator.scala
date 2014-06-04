package ch.epfl.bbp.uima.topicmodels.annotators
import org.apache.uima.UimaContext
import org.apache.uima.fit.util.JCasUtil
import org.apache.uima.jcas.JCas
import scala.collection.immutable.HashSet
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import de.julielab.jules.types.Token
import scala.collection.JavaConversions._
import ch.epfl.bbp.uima.types.Noise

/**
 * Marks tokens containing illegal character sequences with the Noise annotation
 * 
 * Note, that if the character sequences contain whitespaces such as " " or "\t" they are removed by the UIMA framework.
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.Token), outputs = Array(ch.epfl.bbp.uima.types.Noise))
class IllegalCharacterSequenceAnnotator extends JCasAnnotator_ImplBase {
  private var characterBlackList: Set[String] = null

  override def initialize(context: UimaContext) {
    super.initialize(context)

    val blacklist = context.getConfigParameterValue(IllegalCharacterSequenceAnnotator.CharacterBlackList)
    
    if(blacklist == null)
      AnnotatorUtils.getLogger.warn("No blacklist set in IllegalCharacterAnnotator!")
    val list = if (blacklist != null) blacklist.asInstanceOf[Array[String]] else Array.empty[String]
    
    val whitespace = AnnotatorUtils.getBooleanParamFromContext(context, IllegalCharacterSequenceAnnotator.IncludeWhiteSpace, false, false)  
    characterBlackList = (new HashSet() ++ list)  ++ (if(whitespace) " " :: "\t" :: Nil else Nil)
  }

  override def process(doc: JCas) {
    val tokens = JCasUtil.select(doc, classOf[Token])

    for (t <- tokens) {
      val tokenStr = t.getCoveredText.toLowerCase
      if (characterBlackList.exists(tokenStr.contains(_))) {
        (new Noise(doc, t.getBegin, t.getEnd())).addToIndexes
      }
    }
  }
}

object IllegalCharacterSequenceAnnotator {
  val CharacterBlackList = "characterblackList" // (String Array) List of character sequences not allowed to appear in any token.
  val IncludeWhiteSpace = "includewhitespace" // (Boolean) also marks tokens if they contain whitespaces such as " " or "\t"
}