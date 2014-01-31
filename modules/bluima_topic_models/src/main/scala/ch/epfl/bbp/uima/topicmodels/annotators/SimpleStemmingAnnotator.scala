package ch.epfl.bbp.uima.topicmodels.annotators
import scala.collection.JavaConversions.collectionAsScalaIterable

import org.apache.uima.jcas.JCas
import org.apache.uima.fit.component.JCasAnnotator_ImplBase
import org.apache.uima.fit.util.JCasUtil

import ch.epfl.bbp.uima.ae.EnsureTokensHaveLemmaAndPOS
import ch.epfl.bbp.uima.utils.SnowballStemmer
import de.julielab.jules.types.Token

/**
 * Stems all the tokens using the Snowball stemmer
 *
 * The stem is written to the lemma field of the token.
 */
class SimpleStemmingAnnotator extends JCasAnnotator_ImplBase {
  private val stemmer = new SnowballStemmer()

  override def process(doc: JCas) {
    val tokens = JCasUtil.select(doc, classOf[Token])
    tokens.foreach(t => {
      EnsureTokensHaveLemmaAndPOS.setLemma(t, doc, stemmer.stem(t.getCoveredText()))
    })
  }
}
