package ch.epfl.bbp.uima.topicmodels.annotators.featureextraction
import scala.collection.JavaConversions._
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.fit.util.JCasUtil
import ch.epfl.bbp.uima.types.FeatureTokens
import de.julielab.jules.types.Token
import org.apache.uima.jcas.cas.StringArray
import org.apache.uima.UimaContext
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils
import org.apache.uima.jcas.cas.IntegerArray

// TODO remove
/**
 * Extract features from the tokens
 *
 * Features extracted from a token are mostly just its string representation or. Some more complex treatments
 * could be implemented, e.g. for measures.
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.Token), outputs = Array(ch.epfl.bbp.uima.types.FeatureTokens))
class FeatureTokensExtractionAnnotator extends JCasAnnotator_ImplBase {
  protected var lowercase = true

  override def initialize(context: UimaContext) {
    lowercase = AnnotatorUtils.getBooleanParamFromContext(context, FeatureTokensExtractionAnnotator.Lowercase, lowercase, false)
  }

  override def process(doc: JCas) {
    val tokens = JCasUtil.select(doc, classOf[Token])

    val features = tokens.map(t => (processToken(t, doc), t.getBegin, t.getEnd))
      .filter(s => s._1.size > 0)
      .toArray

    addFeatureTokenAnnotation(doc, features)
  }

  protected def processToken(t: Token, doc: JCas): String = {
    val s = t.getCoveredText
    if (lowercase) s.toLowerCase
    else s
  }

  def addFeatureTokenAnnotation(doc: JCas, features: Array[(String, Int, Int)]) {
    val ft = new FeatureTokens(doc)
    
    ft.setTokens(new StringArray(doc, features.size))
    features.zipWithIndex.foreach(p => ft.setTokens(p._2, p._1._1))

    ft.setBeginnings(new IntegerArray(doc, features.size))
    features.zipWithIndex.foreach(p => ft.setBeginnings(p._2, p._1._2))
    
    ft.setEndings(new IntegerArray(doc, features.size))
    features.zipWithIndex.foreach(p => ft.setEndings(p._2, p._1._3))
    
    ft.addToIndexes
  }
}
object FeatureTokensExtractionAnnotator {
  val Lowercase = "lowercase" // (Boolean) (true by default) Indicates if token strings should be converted to lower-case 
}