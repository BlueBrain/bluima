package ch.epfl.bbp.uima.topicmodels.annotators.featureextraction
import scala.collection.JavaConversions._
import scala.collection.immutable.HashSet
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase
import org.apache.uima.jcas.JCas
import org.apache.uima.fit.util.JCasUtil
import ch.epfl.bbp.uima.types.FrequentToken
import ch.epfl.bbp.uima.types.Hapax
import ch.epfl.bbp.uima.types.Noise
import ch.epfl.bbp.uima.types.Punctuation
import ch.epfl.bbp.uima.types.Stopword
import de.julielab.jules.types.Token
import ch.epfl.bbp.uima.types.Measure
import ch.epfl.bbp.uima.topicmodels.annotators.AnnotatorUtils

/**
 * Extract tokens from document which are features, i.e tokens which are annotated as stopwords or some other undesirable category
 *
 */
class FeatureTokensFilterAnnotator extends JCasAnnotator_ImplBase {
  override def process(doc: JCas) {
    val undesirableAnnotations = annotationsToRemove(doc).flatMap(ty => JCasUtil.select(doc, ty.getClass()))
    val badPositions = HashSet.empty ++ undesirableAnnotations.map(t => (t.getBegin, t.getEnd))

    val toRemove = JCasUtil.select(doc, classOf[Token])
      .filter(t => badPositions.contains((t.getBegin, t.getEnd)))
      
    toRemove.foreach(_.removeFromIndexes)
  }

  /**
   * Returns a list of annotation "types" to be removed
   * 
   * The fact to represent the types of annotations as dummy instances of the respective type is more of a hack
   */
  protected def annotationsToRemove(doc: JCas): List[org.apache.uima.jcas.tcas.Annotation] = {
    (new Stopword(doc)) ::
      (new FrequentToken(doc)) ::
      (new Measure(doc)) :: // FIXME: for simplicity, we kick out measures
      (new Noise(doc)) ::
      (new Hapax(doc)) :: // FIXME: Need to be careful if relevant token (e.g. protein) is marked as hapax.
      (new Punctuation(doc)) :: Nil
  }
}