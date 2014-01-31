package ch.epfl.bbp.uima.ae

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
import ch.epfl.bbp.uima.types.Keep

/**
 * Extract features from the tokens
 *
 * Features extracted from a token are mostly just its string representation or. Some more complex treatments
 * could are implemented, e.g. for measures.
 */
//@TypeCapability(inputs = Array(ch.epfl.bbp.uima.types.Token), outputs = Array(ch.epfl.bbp.uima.types.FeatureTokens))
class FeatureTokensExtractionAnnotator2 extends JCasAnnotator_ImplBase {

  override def process(doc: JCas) {

    val features = JCasUtil.select(doc, classOf[Keep])
      .map(sp => (sp.getNormalizedText, sp.getBegin, sp.getEnd))
      .filter(s => s._1.size > 0)
      .toArray

    addFeatureTokenAnnotation(doc, features)
  }

  //TODO dedupe
  private def addFeatureTokenAnnotation(doc: JCas, features: Array[(String, Int, Int)]) {
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